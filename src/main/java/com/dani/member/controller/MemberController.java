package com.dani.member.controller;

import com.dani.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

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
	MemberService memberService;


	/**
	 * @author : DaEunKim
	 * @Description 회원가입 /api/member/signup
	 */
	@PostMapping("/signUp")
	public ResponseEntity<?> signUp(@RequestBody Map<String, String> member) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long result = memberService.signUp(member);
		if(result == null) {
			resultMap.put("resultCode", "401");
			resultMap.put("resultMsg", "회원 가입 실패 ");
		}else {
			resultMap.put("resultCode", "200");
			resultMap.put("resultMsg", "회원 가입 성공");
		}
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	/**
	 * @author : DaEunKim
	 * @Description 로그인 /api/member/signin
	 */
	@PostMapping("/signIn")
	public ResponseEntity<?> signIn(@RequestBody Map<String, String> member) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String result = memberService.signIn(member);
		if(result == null) {
			resultMap.put("resultCode", "401");
			resultMap.put("resultMsg", "로그인 실패 ");
		}else {
			resultMap.put("resultCode", "200");
			resultMap.put("resultMsg", "로그인 성공");
			resultMap.put("token", result);
		}
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	/**
	 * @author : DaEunKim
	 * @Description 로그아웃
	 */
	@PostMapping(path="/logout")
	public ResponseEntity<?> logout(@RequestBody Map<String, String> member) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String result = memberService.logout(member);
		resultMap.put("resultMsg", result);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
