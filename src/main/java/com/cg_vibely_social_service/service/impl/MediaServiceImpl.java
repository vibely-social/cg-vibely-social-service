package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.entity.Media;
import com.cg_vibely_social_service.payload.response.MediaResponseDto;
import com.cg_vibely_social_service.payload.response.MediaStringResponse;
import com.cg_vibely_social_service.repository.MediaRepository;
import com.cg_vibely_social_service.repository.UserRepository;
import com.cg_vibely_social_service.service.MediaService;
import com.cg_vibely_social_service.utils.GsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {
    private final MediaRepository mediaRepository;
    private final UserRepository userRepository;
    private final Converter<MediaResponseDto, Media> converter;

    private final RedisTemplate<String, String> redisTemplate;
    private final GsonUtils<MediaStringResponse> gsonUtils;
    private final ValueOperations<String, String> cachedData;

    @Override
    public List<MediaResponseDto> getMediaForUser(Long id, int page) {
        String requestedPage = "user_" + id + "_" + page;
        List<MediaResponseDto> mediaResponseDtoList;

        if (redisTemplate.hasKey(requestedPage)) {
            String dtoString = cachedData.get(requestedPage);
            mediaResponseDtoList = gsonUtils.parseToObject(dtoString, MediaStringResponse.class).getListString();

        } else {
            if (page <= 9) {
                for (int currentPage = 0; currentPage <= 9; currentPage++) {
                    String currentPageKey = "user_" + id + "_" + currentPage;
                    PageRequest pageRequest = PageRequest.of(currentPage, 30, Sort.by("createdAt").descending());
                    Page<Media> mediaPage = mediaRepository.findAllByUserId(id, pageRequest);
                    if (mediaPage.isEmpty()) {
                        break;
                    }
                    List<Media> media = mediaPage.getContent();
                    List<MediaResponseDto> dtoList = converter.revert(media);
                    MediaStringResponse mediaStringResponse = MediaStringResponse.builder()
                            .listString(dtoList).build();
                    String dtoString = gsonUtils.parseToString(mediaStringResponse);
                    cachedData.set(currentPageKey, dtoString);
                }

                String dtoString = cachedData.get(requestedPage);
                mediaResponseDtoList = gsonUtils.parseToObject(dtoString, MediaStringResponse.class).getListString();

            } else {
                PageRequest pageRequest = PageRequest.of(page, 30, Sort.by("createdAt").descending());
                Page<Media> mediaPage = mediaRepository.findAllByUserId(id, pageRequest);
                List<Media> media = mediaPage.getContent();
                List<MediaResponseDto> dtoList = converter.revert(media);

                mediaResponseDtoList = dtoList;
            }
        }

        return mediaResponseDtoList;
    }

    @Override
    public void resetMediaCache(Long id) {
        String userKey = "user_" + id + "_";
        for (int index = 0; index <= 9; index++) {
            if (redisTemplate.hasKey(userKey + index)) {
                redisTemplate.delete(userKey + index);
            }
        }
    }
}
