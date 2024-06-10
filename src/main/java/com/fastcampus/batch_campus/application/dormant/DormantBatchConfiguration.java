package com.fastcampus.batch_campus.application.dormant;

import com.fastcampus.batch_campus.batch.Job;
import com.fastcampus.batch_campus.batch.Step;
import com.fastcampus.batch_campus.batch.StepJobBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DormantBatchConfiguration {

    @Bean
    public Job dormantBatchJob(
            Step preDormantBatchStep,
            Step dormantBatchStep,
            DormantBatchJobExecutionListener dormantBatchJobExecutionListener
    ) {
        return new StepJobBuilder()
                .start(preDormantBatchStep)
                .next(dormantBatchStep)
                .build();
    }

    @Bean
    public Step preDormantBatchStep(
            AllCustomerItemReader preDormantItemReader,
            PreDormantItemProcessor preDormantItemProcessor,
            PreDormantItemWriter preDormantItemWriter
    ) {
        return Step.builder()
                .itemReader(preDormantItemReader)
                .itemProcessor(preDormantItemProcessor)
                .itemWriter(preDormantItemWriter)
                .build();
    }

    @Bean
    public Step dormantBatchStep(
            AllCustomerItemReader itemReader,
            DormantBatchItemProcessor itemProcessor,
            DormantBatchItemWriter itemWriter
    ) {
        return Step.builder()
                .itemReader(itemReader)
                .itemProcessor(itemProcessor)
                .itemWriter(itemWriter)
                .build();
    }
}
