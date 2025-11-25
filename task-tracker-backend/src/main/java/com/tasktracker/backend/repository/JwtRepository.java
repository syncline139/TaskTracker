package com.tasktracker.backend.repository;

import com.tasktracker.backend.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JwtRepository extends JpaRepository<RefreshToken, Long> {
    @Query("SELECT rt FROM RefreshToken rt JOIN rt.user u WHERE u.email =:email")
    Optional<RefreshToken> findRefreshTokenByEmail(@Param("email") String email);
}
