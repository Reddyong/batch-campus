package com.fastcampus.batch_campus.batch;

public interface ItemProcessor<I, O> {
    O process(I item);
}
