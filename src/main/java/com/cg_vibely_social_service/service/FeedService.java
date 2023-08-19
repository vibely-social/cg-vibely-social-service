package com.cg_vibely_social_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface FeedService {
    void newPost(String source, List<String> files) throws JsonProcessingException;

    void newPost(String source) throws JsonProcessingException;
}
