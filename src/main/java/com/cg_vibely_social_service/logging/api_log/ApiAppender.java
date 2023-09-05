package com.cg_vibely_social_service.logging.api_log;

import com.cg_vibely_social_service.payload.log.LogData;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ApiAppender extends AppenderSkeleton {
    private final RestTemplate restTemplate;
    @Value("${logging.appender.api}")
    private String LOG_API;

    public ApiAppender() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    protected void append(LoggingEvent event) {
        String message = event.getMessage().toString();
        String level = event.getLevel().toString();
        long timestamp = event.getTimeStamp();
        try {
            LogData logData = new LogData(message, level, timestamp);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<LogData> content = new HttpEntity<>(logData, headers);

            ResponseEntity<Object> responseEntity = restTemplate.postForEntity(LOG_API, content, Object.class);
            // Handle API response
        } catch (Exception e) {
            // Handle if failed to sent log
            e.printStackTrace();
        }
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }
}
