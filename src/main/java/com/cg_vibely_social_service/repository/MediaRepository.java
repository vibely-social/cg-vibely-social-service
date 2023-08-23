package com.cg_vibely_social_service.repository;
import com.cg_vibely_social_service.entity.Media;
import com.cg_vibely_social_service.entity.Post;
import com.cg_vibely_social_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {
    void deleteById(Long MediaId);
    List<Media> findByUser(User user);
}
