package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.entity.Media;
import com.cg_vibely_social_service.entity.User;
import com.cg_vibely_social_service.payload.response.MediaResponseDto;
import com.cg_vibely_social_service.repository.MediaRepository;
import com.cg_vibely_social_service.repository.UserRepository;
import com.cg_vibely_social_service.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {
    private final MediaRepository mediaRepository;
    private final UserRepository userRepository;
    private final Converter<MediaResponseDto, Media> converter;

    @Override
    public List<MediaResponseDto> getMediaForUser(Long id, int page) {
        PageRequest pageRequest = PageRequest.of(page, 60, Sort.by("createdAt").descending());
        Page<Media> mediaPage = mediaRepository.findAllByUserId(id, pageRequest);
        List<Media> media = mediaPage.getContent();
        return converter.revert(media);
    }
}
