package com.cg_vibely_social_service.repository;
import com.cg_vibely_social_service.entity.Media;
import com.cg_vibely_social_service.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {
    void deleteById(Long MediaId);

    Page<Media> findAllByUserId(Long userId, PageRequest pageRequest);
}
