package com.cg_vibely_social_service.repository;

import com.cg_vibely_social_service.entity.FriendRequest;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.service.impl.UserImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    List<FriendRequest> findFriendRequestsByUser_id(Long userId);
    List<FriendRequest> findFriendRequestsByFriend_Id(Long friendId);
    FriendRequest findByUserAndFriend(User user, User friend);
    void deleteAllByUserAndFriendOrUserAndFriend(User user, User friend, User rv_friend, User rv_user);
}
