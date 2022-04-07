package com.dani.member.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author : DaEunKim
 * @version : 2022/04/07
 * @fileName : com.dani.member.model
 * @description :
 */
@Entity
@Getter @Setter @ToString
@Table(name = "member")
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	public Member(){

	}
	public Member(String email, String password){
		this.email = email;
		this.password = password;
	}



}
