//package com.cg_vibely_social_service.scheduling.store_redis_messages;
//
//import com.cg_vibely_social_service.payload.message.ChatMessageDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//@Configuration
//@EnableBatchProcessing
//@RequiredArgsConstructor
//public class StoreRedisMessageJobConfig {
//    private static final int STORE_MESSAGE_JUNK_SIZE = 1;
//
//    @Bean
//    public Job storeRedisMessages(JobRepository jobRepository, Step step1) {
//        return new JobBuilder("storeRedisMessages", jobRepository)
//                .start(step1)
//                .build();
//    }
//
//    @Bean
//    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("step1", jobRepository)
//                .<ChatMessageDto,ChatMessageDto>chunk(STORE_MESSAGE_JUNK_SIZE,transactionManager)
//                .build();
//    }
//}
