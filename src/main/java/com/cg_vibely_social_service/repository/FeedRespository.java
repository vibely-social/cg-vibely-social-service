package com.cg_vibely_social_service.repository;


import com.cg_vibely_social_service.entity.Feed.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRespository extends JpaRepository<Feed, Long> {
}
