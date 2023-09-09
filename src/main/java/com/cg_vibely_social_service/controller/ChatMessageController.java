package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.payload.message.ChatMessagesResponse;
import com.cg_vibely_social_service.service.ChatService;
import com.google.protobuf.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/messages")
@Tag(
        name = "Chat",
        description = "API endpoints for chatting function"
)
public class ChatMessageController {
    private final ChatService chatService;

    @GetMapping
    @Operation(
            summary = "Get newest messages between two users",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get chat message successfully",
                            content = @Content(
                                    schema = @Schema(implementation = ChatMessagesResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<?> getMessages(@RequestParam(required = false) String sender, String receiver,
                                         @PageableDefault(size = 20, sort = "time",direction = Sort.Direction.DESC) Pageable pageable) {
        ChatMessagesResponse chatMessagesResponse = chatService.findAllWithCacheActive(sender, receiver, pageable);
        return new ResponseEntity<>(chatMessagesResponse, HttpStatus.OK);
    }
}
