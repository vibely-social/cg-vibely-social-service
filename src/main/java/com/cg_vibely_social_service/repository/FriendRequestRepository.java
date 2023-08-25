package com.cg_vibely_social_service.repository;

import com.cg_vibely_social_service.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findFriendRequestsByUser_id(Long userId);

    List<FriendRequest> findFriendRequestsByFriend_Id(Long friendId);
}
