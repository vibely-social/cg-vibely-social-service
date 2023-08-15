package com.cg_vibely_social_service.repository;

import com.cg_vibely_social_service.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findAllByUserId(Long id);
    List<Friend> findAllByFriendId(Long id);
}
