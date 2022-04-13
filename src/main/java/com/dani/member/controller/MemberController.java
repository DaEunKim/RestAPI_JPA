package com.dani.member.controller;

import com.dani.member.config.JwtProvider;
import com.dani.member.model.Member;
import com.dani.member.repository.MemberRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author : DaEunKim
 * @version : 2022/04/07
 * @fileName : com.dani.member.controller
 * @description : 회원가입, 로그인, 로그아웃 api
 */

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {
	@Autowired
	MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;

	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	/**
	 * @author : DaEunKim
	 * @Description 회원가입 /api/member/signup
	 */
	@PostMapping("/signup")
	public Long signup(@RequestBody Map<String, String> member) {
		return memberRepository.save(Member.builder()
				.email(member.get("email"))
				.password(passwordEncoder.encode(member.get("password")))
				.roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
				.build()).getId();
	}

	/**
	 * @author : DaEunKim
	 * @Description 로그인 /api/member/signin
	 */
	@PostMapping("/signin")
	public String signin(@RequestBody Map<String, String> member) {
		Member getMemberInfo = memberRepository.findByEmail(member.get("email"))
				.orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
		if (!passwordEncoder.matches(member.get("password"), getMemberInfo.getPassword())) {
			throw new IllegalArgumentException("잘못된 비밀번호입니다.");
		}
		return jwtProvider.createToken(getMemberInfo.getUsername(), getMemberInfo.getRoles());
	}

	/**
	 * @author : DaEunKim
	 * @Description 로그아웃
	 */
	@PostMapping(path="/logout")
	public ResponseEntity<?> logout(@RequestBody Map<String, String> m) {
		String accessToken = m.get("accessToken");
		Authentication username = jwtProvider.getAuthentication(accessToken);
		try {
			if (redisTemplate.opsForValue().get(username) != null) {
				//delete refresh token
				redisTemplate.delete((Collection<String>) username);
			}
		} catch (IllegalArgumentException e) {
			log.warn("사용자가 존재하지 않습니다. ");
		}

		//cache
		redisTemplate.opsForValue().set(accessToken, true);
		redisTemplate.expire(accessToken, 10*6*1000, TimeUnit.MILLISECONDS);

		return new ResponseEntity(HttpStatus.OK);
	}
}
