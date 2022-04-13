package com.dani.member.service;

import com.dani.member.config.JwtProvider;
import com.dani.member.model.Member;
import com.dani.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author : DaEunKim
 * @version : 2022/04/07
 * @fileName : com.dani.member.service
 * @description : 회원 service
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
	private final PasswordEncoder passwordEncoder;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	MemberRepository memberRepository;
	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	public Long signUp(Map<String, String> member){
		return memberRepository.save(Member.builder()
				.email(member.get("email"))
				.password(passwordEncoder.encode(member.get("password")))
				.roles(Collections.singletonList("ROLE_USER")) // 최초 가입시 USER 로 설정
				.build()).getId();
	}

	public String signIn(Map<String, String> member){
		Member getMemberInfo = memberRepository.findByEmail(member.get("email"))
				.orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
		if (!passwordEncoder.matches(member.get("password"), getMemberInfo.getPassword())) {
			throw new IllegalArgumentException("잘못된 비밀번호입니다.");
		}
		return jwtProvider.createToken(getMemberInfo.getUsername(), getMemberInfo.getRoles());
	}


	public String logout(Map<String, String> member){
		String msg = null;
		String accessToken = member.get("accessToken");
		Authentication username = jwtProvider.getAuthentication(accessToken);
		try {
			if (redisTemplate.opsForValue().get(username) != null) {
				//delete refresh token
				redisTemplate.delete((Collection<String>) username);
				msg = "로그아웃되었습니다. ";
			}
		} catch (IllegalArgumentException e) {
			msg = "사용자가 존재하지 않습니다. ";
		}

		//cache
		redisTemplate.opsForValue().set(accessToken, true);
		redisTemplate.expire(accessToken, 10*6*1000, TimeUnit.MILLISECONDS);
		return msg;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return memberRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
	}
}
