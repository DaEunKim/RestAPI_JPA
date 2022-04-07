package com.dani.member.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.util.Arrays;

/**
 * @author : DaEunKim
 * @version : 2022/04/07
 * @fileName : com.dani.member.config
 * @description : security config
 */
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final JwtProvider jwtProvider;

	/**
	 * @author : DaEunKim
	 * @Description 암호화에 필요한 PasswordEncoder를 Bean에 등록
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	/**
	 * @author : DaEunKim
	 * @Description authenticationManager를 Bean에 등록
	 */
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제하겠습니다.
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 역시 사용하지 않습니다.
				.and()
				.authorizeRequests() // 요청에 대한 사용권한 체크
				.antMatchers("/h2-console/**", "/").permitAll()
				.anyRequest().permitAll()
				.and().headers().frameOptions().sameOrigin()
				.and().csrf().disable()
				.addFilterBefore(new JwtAuthenticationFilter(jwtProvider),
						UsernamePasswordAuthenticationFilter.class);

	}
}
