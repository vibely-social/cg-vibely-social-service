package com.cg_vibely_social_service.payload.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateVisualizeRequest {
    private MultipartFile file;
    private String fileName;
}
