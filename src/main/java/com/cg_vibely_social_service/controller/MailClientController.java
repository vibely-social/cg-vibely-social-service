package com.cg_vibely_social_service.controller;

import com.cg_vibely_social_service.payload.sdi.ClientSdi;
import com.cg_vibely_social_service.service.ClientService;
import com.cg_vibely_social_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/forgot_password")
@RequiredArgsConstructor
@Tag(
        name = "Mail Client",
        description = "API endpoints for handling sending email function"
)
public class MailClientController {

    private final ClientService clientService;
    private final UserService userService;

    @PostMapping
    @Operation(
            summary = "Sending email",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Sent email successfully"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Data not acceptable"
                    )
            }
    )
    public ResponseEntity<Boolean> create(
            @RequestBody ClientSdi sdi
    ) {
        if (!userService.checkValidEmail(sdi.getEmail())) {
            return ResponseEntity.ok(clientService.create(sdi));
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
