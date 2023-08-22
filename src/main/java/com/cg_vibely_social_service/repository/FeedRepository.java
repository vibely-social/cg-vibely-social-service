package com.cg_vibely_social_service.repository;


import com.cg_vibely_social_service.entity.Feed.Feed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {

    @Query(value = "SELECT * FROM FEED WHERE JSON_EXTRACT(feed_items, '$.privacy' ) = ?1 ", nativeQuery = true)
    List<Feed> findAllByFeed();

//    @Query(value = "SELECT p.*, JSON_UNQUOTE(JSON_EXTRACT(p.feed_items, '$.privacy')) AS privacy FROM FEED p WHERE JSON_UNQUOTE(JSON_EXTRACT(p.feed_items, '$.privacy')) = ?1", nativeQuery = true)
//    List<Feed> findByPrivacy(String privacy);
    @Query(value = "SELECT * FROM feed ORDER BY id DESC LIMIT ?,20;" , nativeQuery = true)
    List<Feed> findLatestFeeds(int limit);
}
