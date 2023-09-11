package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.payload.response.MediaResponseDto;
import com.cg_vibely_social_service.service.MediaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
@Tag(
        name = "Media",
        description = "API endpoints for interacting with media file"
)
public class MediaController {
    private final MediaService mediaService;

    @GetMapping("/{id}")
    @Operation(
            summary = "Get media by pagination",
            parameters = {
                    @Parameter(
                            name = "id",
                            description = "ID of the user",
                            in = ParameterIn.PATH,
                            required = true
                    ),
                    @Parameter(
                            name = "page",
                            description = "Number of the next page",
                            in = ParameterIn.QUERY
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Get media successfully",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = MediaResponseDto.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Failed to get media"
                    )
            }
    )
    public ResponseEntity<?> getMedia(@PathVariable("id") Long id,
                                      @RequestParam(name = "page", defaultValue = "0") int page) {
        List<MediaResponseDto> mediaList = mediaService.getMediaForUser(id, page);
        if (!mediaList.isEmpty()) {
            return new ResponseEntity<>(mediaList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
