package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.entity.FakePost;
import com.cg_vibely_social_service.repository.FakePostRepository;
import com.cg_vibely_social_service.service.FakePostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FakePostServiceImpl implements FakePostService {
    private final FakePostRepository fakePostRepository;

    @Override
    public List<FakePost> getFirst9ForMediaTabByUserId(Long userId) {
        return fakePostRepository.findFirst9ByUserId(userId);
    }
}
