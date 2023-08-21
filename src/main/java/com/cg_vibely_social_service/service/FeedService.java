package com.cg_vibely_social_service.service;

import com.cg_vibely_social_service.entity.Feed.Feed;
import com.cg_vibely_social_service.payload.response.FeedItemResponseDto;
import com.cg_vibely_social_service.payload.response.FriendResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FeedService {

    void newPost(String source, List<String> files) throws JsonProcessingException;

    void newPost(String source) throws JsonProcessingException;

    List<FeedItemResponseDto> getNewestPost();
}
