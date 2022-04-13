package com.dani.member.controller;

import com.dani.member.model.Goods;
import com.dani.member.service.GoodsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : DaEunKim
 * @version : 2022/04/13
 * @fileName : com.dani.member.controller
 * @description : 상품 조회, 주문
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/goods")
public class GoodsController {
	@Autowired
	GoodsService goodsService;
	/**
	 * @author : DaEunKim
	 * @Description 특정 상품 조회
	 */
	@GetMapping(path = "/getGoods")
	public ResponseEntity<?> getGoods(@RequestBody Long id){
		Map<String, Object> resultMap = new HashMap<String, Object>();

		Goods items = goodsService.getOneGoods(id);
		if(items == null) {
			resultMap.put("resultCode", "401");
			resultMap.put("resultMsg", "상품 조회 실패");
		}else {
			resultMap.put("resultCode", "200");
			resultMap.put("items", items);
		}

		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
