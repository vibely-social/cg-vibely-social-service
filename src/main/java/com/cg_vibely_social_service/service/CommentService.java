package com.cg_vibely_social_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CommentService {

    void newComment(Long postId, String comment, MultipartFile source) throws IOException;
}
