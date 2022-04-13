package com.dani.member.model;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

/**
 * @author : DaEunKim
 * @version : 2022/04/13
 * @fileName : com.dani.member.model
 * @description :
 */
@Data
@RedisHash
public class Token implements Serializable {
	private static final long serialVersionUID = -7353484588260422449L;
	private String username;
	private String refreshToken;
}