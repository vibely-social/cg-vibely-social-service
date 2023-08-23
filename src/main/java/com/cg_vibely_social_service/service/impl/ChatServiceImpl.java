package com.cg_vibely_social_service.service.impl;

import com.cg_vibely_social_service.converter.Converter;
import com.cg_vibely_social_service.entity.ChatMessage;
import com.cg_vibely_social_service.payload.message.ChatMessageDto;
import com.cg_vibely_social_service.payload.message.ChatMessagesResponse;
import com.cg_vibely_social_service.repository.ChatMessageRepository;
import com.cg_vibely_social_service.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatMessageRepository messageRepository;
    private final Comparator<ChatMessage> chatMessageComparator;
    private final Converter<ChatMessageDto, ChatMessage> chatMessageConverter;

    @Override
    public void save(ChatMessageDto messageDto) {
        ChatMessage chatMessage = new ChatMessage();
        BeanUtils.copyProperties(messageDto,chatMessage);
        messageRepository.save(chatMessage);
    }

    @Override
    public ChatMessagesResponse findAll(String senderEmail, String receiverEmail, Pageable pageable) {
        Page<ChatMessage> messagePage= messageRepository
                .findAllBySenderAndReceiverOrSenderAndReceiver(senderEmail,receiverEmail,receiverEmail,senderEmail,pageable);
        List<ChatMessage> messageList = new ArrayList<>(messagePage.getContent());
        messageList.sort(chatMessageComparator);
        List<ChatMessageDto> finalList = chatMessageConverter.revert(messageList);
        return ChatMessagesResponse.builder()
                .totalPage(messagePage.getTotalPages())
                .messageList(finalList)
                .build();
    }
}
