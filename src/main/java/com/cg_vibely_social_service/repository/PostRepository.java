package com.cg_vibely_social_service.repository;

import com.cg_vibely_social_service.entity.Post;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.response.PostResponseDto;
import jakarta.validation.constraints.Email;
import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    void deleteById(Long postID);

    List<Post> findByUser(User user);


}
