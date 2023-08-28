package com.cg_vibely_social_service.service;

import com.cg_vibely_social_service.payload.response.LikeResponseDto;

public interface LikeService {
    LikeResponseDto likePost(Long postId);
}
