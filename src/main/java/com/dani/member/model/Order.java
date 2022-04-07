package com.dani.member.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

/**
 * @author : DaEunKim
 * @version : 2022/04/07
 * @fileName : com.dani.member.model
 * @description : 주문 모델
 */
@Entity
@Getter @Setter @ToString
@Table(name = "orderList")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "memberId")
	private Long memberId;

	@Column(name = "goodsId")
	private Long goodsId;

}
