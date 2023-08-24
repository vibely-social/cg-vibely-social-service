package com.cg_vibely_social_service.repository;

import com.cg_vibely_social_service.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findAllById(Long id);
    @Query(value = "SELECT DISTINCT u FROM User u " +
            "WHERE u.id != :userId " +
            "AND u.id NOT IN (SELECT f.friendId FROM Friend f WHERE f.userId = :userId) " +
            "AND u.id NOT IN (SELECT f.userId FROM Friend f WHERE f.friendId = :userId) " +
            "ORDER BY RAND()")
    List<User> find20UsersSuggestionByUserId(@Param("userId") Long userId, Pageable pageable);
}
