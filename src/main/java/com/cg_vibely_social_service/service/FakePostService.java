package com.cg_vibely_social_service.service;

import com.cg_vibely_social_service.entity.FakePost;
import org.springframework.stereotype.Service;

import java.util.List;

public interface FakePostService {
    List<FakePost> getFirst9ForMediaTabByUserId(Long userId);
}
