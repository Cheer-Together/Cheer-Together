package com.ssafy.cheertogether.member.repository;

import com.ssafy.cheertogether.member.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
}
