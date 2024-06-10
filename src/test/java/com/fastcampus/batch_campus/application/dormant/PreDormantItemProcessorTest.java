package com.fastcampus.batch_campus.application.dormant;

import com.fastcampus.batch_campus.customer.Customer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PreDormantItemProcessorTest {

    private PreDormantItemProcessor preDormantItemProcessor;

    @BeforeEach
    void setup() {
        preDormantItemProcessor = new PreDormantItemProcessor();
    }

    @Test
    @DisplayName(value = "로그인 날짜가 오늘로부터 358일 전이면, customer 를 반환해야한다.")
    void test1() {
        // given
        Customer customer = new Customer("Hong", "test@fastcampus.com");

        // 오늘은 2024.06.10 예정자는 2023.06.17
        customer.setLoginAt(LocalDateTime.now().minusDays(365).plusDays(7));

        // when
        Customer result = preDormantItemProcessor.process(customer);

        // then
        assertThat(result).isEqualTo(customer);
        assertThat(result).isNotNull();
    }

    @Test
    @DisplayName(value = "로그인 날짜가 오늘로부터 358일 전이 아니면 null 을 반환해야한다.")
    void test2() {
        // given
        Customer customer = new Customer("Hong", "test@fastcampus.com");

        // when
        Customer result = preDormantItemProcessor.process(customer);

        // then
        assertThat(result).isNull();
    }

}