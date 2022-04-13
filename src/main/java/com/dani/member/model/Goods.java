package com.dani.member.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

/**
 * @author : DaEunKim
 * @version : 2022/04/07
 * @fileName : com.dani.member.model
 * @description : 상품 모델
 */
@Entity
@Getter @Setter @ToString
@Table(name = "goods")
public class Goods {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String name; // 상품명

	@Column(name = "price")
	private String price; // 상품 가격

}
