package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.converter.IMapper;
import com.cg_vibely_social_service.entity.Feed.Feed;
import com.cg_vibely_social_service.entity.Feed.FeedItem;
import com.cg_vibely_social_service.payload.request.NewPostRequestDto;
import com.cg_vibely_social_service.repository.FeedRespository;
import com.cg_vibely_social_service.service.FeedService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {
    private final FeedRespository feedRespository;
    @Override
    public void newPost(String source, List<String> files) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        FeedItem feedItem =
                IMapper.INSTANCE.newPostConvert(objectMapper.readValue(source, NewPostRequestDto.class));
        feedItem.setGallery(files);
        feedItem.setCreatedDate(LocalDateTime.now().toString());
        Feed feed = new Feed();
        feed.setFeedItem(feedItem);
        feedRespository.save(feed);
    }

    @Override
    public void newPost(String source) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        LocalDateTime localDateTime = LocalDateTime.now();
        FeedItem feedItem =
                IMapper.INSTANCE.newPostConvert(objectMapper.readValue(source, NewPostRequestDto.class));
        feedItem.setCreatedDate(LocalDateTime.now().toString());
        Feed feed = new Feed();
        feed.setFeedItem(feedItem);
        feedRespository.save(feed);
    }
}
