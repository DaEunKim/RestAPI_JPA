package com.dani.member.controller;

import com.dani.member.config.JwtProvider;
import com.dani.member.model.Member;
import com.dani.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author : DaEunKim
 * @version : 2022/04/07
 * @fileName : com.dani.member.controller
 * @description : 회원가입, 로그인, 로그아웃 api
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {
	@Autowired
	MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;

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
	 * @Description TODO 로그아웃
	 */

}
