package com.dani.member.repository;

import com.dani.member.model.Goods;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author : DaEunKim
 * @version : 2022/04/13
 * @fileName : com.dani.member.repository
 * @description :
 */
public interface GoodsRepository extends JpaRepository<Goods, Long> {

}
