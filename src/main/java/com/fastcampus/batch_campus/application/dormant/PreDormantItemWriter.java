package com.fastcampus.batch_campus.application.dormant;

import com.fastcampus.batch_campus.EmailProvider;
import com.fastcampus.batch_campus.batch.ItemWriter;
import com.fastcampus.batch_campus.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PreDormantItemWriter implements ItemWriter<Customer> {

    private final EmailProvider emailProvider;

    @Autowired
    public PreDormantItemWriter() {
        this.emailProvider = new EmailProvider.Fake();
    }

    public PreDormantItemWriter(EmailProvider emailProvider) {
        this.emailProvider = emailProvider;
    }

    @Override
    public void write(Customer customer) {
        emailProvider.send(
                customer.getEmail(),
                "곧 휴면계정으로 전횐이 됩니다.",
                "휴면계정으로 사용되기 원치 않으신다면 1주일 내에 로그인을 해주세요."
        );

    }
}
