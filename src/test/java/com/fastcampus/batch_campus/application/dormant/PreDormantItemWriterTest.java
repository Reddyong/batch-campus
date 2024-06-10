package com.fastcampus.batch_campus.application.dormant;

import com.fastcampus.batch_campus.EmailProvider;
import com.fastcampus.batch_campus.customer.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PreDormantItemWriterTest {

    private PreDormantItemWriter preDormantItemWriter;

    @Test
    @DisplayName(value = "1주일 뒤에 휴면계정 전환 예정자라고 이메일을 전송해야한다.")
    void test1() {
        // given
        EmailProvider mockEmailProvider = mock(EmailProvider.class);
        Customer customer = new Customer("Hong", "test@fastcampus.com");
        this.preDormantItemWriter = new PreDormantItemWriter(mockEmailProvider);

        // when
        preDormantItemWriter.write(customer);

        // then
        verify(mockEmailProvider, atLeastOnce()).send(any(), any(), any());
    }

}