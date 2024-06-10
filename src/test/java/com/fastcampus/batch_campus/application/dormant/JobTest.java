package com.fastcampus.batch_campus.application.dormant;

import com.fastcampus.batch_campus.batch.BatchStatus;
import com.fastcampus.batch_campus.batch.Job;
import com.fastcampus.batch_campus.batch.JobExecution;
import com.fastcampus.batch_campus.batch.TaskletJob;
import com.fastcampus.batch_campus.customer.Customer;
import com.fastcampus.batch_campus.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DormantBatchJobTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private Job dormantBatchJob;

    @BeforeEach
    public void setup() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName(value = "로그인 시간이 1년을 경과한 고객이 3명이고, 1년 이내 로그인한 고객이 5명이면, 3명의 고객이 휴면전환 대상자이다.")
    void test1() {
        // given
        saveCustomer(366);
        saveCustomer(366);
        saveCustomer(366);

        saveCustomer(364);
        saveCustomer(364);
        saveCustomer(364);
        saveCustomer(364);
        saveCustomer(364);

        // when
        JobExecution result = dormantBatchJob.execute();

        // then
        long dormantCount = customerRepository.findAll().stream().filter(it -> it.getStatus() == Customer.Status.DORMANT).count();

        assertThat(dormantCount).isEqualTo(3);
        assertThat(result.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }

    @Test
    @DisplayName(value = "고객이 10명이 있지만, 모두 다 휴면 전환 대상이면 휴면전환대상은 10명이다.")
    void test2() {
        // given
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);
        saveCustomer(400);

        // when
        JobExecution result = dormantBatchJob.execute();

        // then
        long dormantCount = customerRepository.findAll().stream().filter(it -> it.getStatus() == Customer.Status.DORMANT).count();

        assertThat(dormantCount).isEqualTo(10);
        assertThat(result.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }

    @Test
    @DisplayName(value = "고객이 없는 경우에도 배치는 정상동작해야한다.")
    void test3() {
        // when
        JobExecution result = dormantBatchJob.execute();

        // then
        long dormantCount = customerRepository.findAll().stream().filter(it -> it.getStatus() == Customer.Status.DORMANT).count();

        assertThat(dormantCount).isEqualTo(0);
        assertThat(result.getStatus()).isEqualTo(BatchStatus.COMPLETED);
    }

    @Test
    @DisplayName(value = "배치가 실패하면 BatchStatus 는 FAILED 를 반환해야한다.")
    void test4() {
        // given
        Job job = new TaskletJob(null);

        // when
        JobExecution result = job.execute();

        // then
        assertThat(result.getStatus()).isEqualTo(BatchStatus.FAILED);
    }

    @Test
    @DisplayName(value = "358일 전에 로그인한 고객에게 휴면계정 예정자라고 메일을 발송해야한다.")
    void test5() {
        // given
        saveCustomer(358);
        saveCustomer(358);
        saveCustomer(358);
        saveCustomer(35);
        saveCustomer(35);

        // when

        // then
        dormantBatchJob.execute();
    }

    private void saveCustomer(long loginMinusDays) {
        final String uuid = UUID.randomUUID().toString();
        final Customer test = new Customer(uuid, uuid + "test@fastcampus.com");
        test.setLoginAt(LocalDateTime.now().minusDays(loginMinusDays));
        customerRepository.save(test);
    }
}