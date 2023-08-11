package com.vibely_social.repository;


import com.vibely_social.entity.Post;
import com.vibely_social.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    void deleteById(Long postID);
    List<Post> findByUser(User user);

}
