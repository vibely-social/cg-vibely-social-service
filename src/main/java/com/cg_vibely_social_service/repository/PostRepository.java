package com.cg_vibely_social_service.repository;

import com.cg_vibely_social_service.entity.Post;
import com.cg_vibely_social_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    void deleteById(Long postID);

    List<Post> findByUser(User user);

}
