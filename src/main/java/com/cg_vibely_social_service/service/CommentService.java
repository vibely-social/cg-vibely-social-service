package com.cg_vibely_social_service.service;

import com.cg_vibely_social_service.payload.response.CommentResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CommentService {

    void newComment(Long postId, String comment, MultipartFile source) throws IOException;

    List<CommentResponseDto> getComments(Long postId);
}
