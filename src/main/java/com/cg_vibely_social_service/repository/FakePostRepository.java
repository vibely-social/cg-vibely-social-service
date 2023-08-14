package com.cg_vibely_social_service.repository;

import com.cg_vibely_social_service.entity.FakePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FakePostRepository extends JpaRepository<FakePost, Long> {
    List<FakePost> findFirst9ByUserId(Long userId);
}
