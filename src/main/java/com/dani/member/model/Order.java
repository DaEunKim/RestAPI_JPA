package com.dani.member.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<Goods> goods = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "id")
	private Member member;

	private Integer orderPrice; // 주문 가격
	private Integer orderCount; // 수량

	public static Order createOrder(Goods goods, int orderPrice, int orderCount){
		Order order = new Order();
		order.setGoods((List<Goods>) goods);
		order.setOrderPrice(orderPrice);
		order.setOrderCount(orderCount);
		return order;
	}

	/**
	 * @author : DaEunKim
	 * @Description 주문상품 전체 가격 조회
	 */
	public int getTotalPrice() {
		return getOrderPrice() * orderCount;
	}

}
