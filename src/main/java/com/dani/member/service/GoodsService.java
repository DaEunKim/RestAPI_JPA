package com.dani.member.service;

import com.dani.member.model.Goods;
import com.dani.member.repository.GoodsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : DaEunKim
 * @version : 2022/04/13
 * @fileName : com.dani.member.service
 * @description : 상품
 */
@Service
@RequiredArgsConstructor
public class GoodsService {
	@Autowired
	GoodsRepository goodsRepository;

	@Transactional //true이면 저장안된다.
	public void saveGoods(Goods goods) {
		goodsRepository.save(goods);
	}
	public List<Goods> getAllGoods() {
		return goodsRepository.findAll();
	}
	/**
	 * @author : DaEunKim
	 * @Description 특정 상품 조회
	 */
	public Goods getOneGoods(Long goodsId) {
		return goodsRepository.getById(goodsId);
	}
}
