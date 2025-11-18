package com.tasktracker.backend.repository;

import com.tasktracker.backend.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRepository extends JpaRepository<RefreshToken, Long> {
}
