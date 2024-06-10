package com.fastcampus.batch_campus.application.dormant;

import com.fastcampus.batch_campus.EmailProvider;
import com.fastcampus.batch_campus.batch.JobExecution;
import com.fastcampus.batch_campus.batch.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class DormantBatchJobExecutionListener implements JobExecutionListener {

    private final EmailProvider emailProvider;

    public DormantBatchJobExecutionListener() {
        this.emailProvider = new EmailProvider.Fake();
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        // 비즈니스 로직
        emailProvider.send("admin@fastcampus.com",
                "배치 완료 알림",
                "Job 이 수행되었습니다. status : " + jobExecution.getStatus());
    }
}
