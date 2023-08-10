package com.cg_vibely_social_service.repository;

import com.cg_vibely_social_service.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    void deleteById(Long postID);
}
