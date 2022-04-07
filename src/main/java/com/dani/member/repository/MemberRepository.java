package com.dani.member.repository;

import com.dani.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author : DaEunKim
 * @version : 2022/04/07
 * @fileName : com.dani.member.repository
 * @description :
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
	List<Member> findByEmailContaining(String email);

	Optional<Member> findByEmail(String email);
}
