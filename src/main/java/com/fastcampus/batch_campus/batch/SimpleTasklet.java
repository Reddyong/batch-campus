package com.fastcampus.batch_campus.batch;

import com.fastcampus.batch_campus.batch.ItemProcessor;
import com.fastcampus.batch_campus.batch.ItemReader;
import com.fastcampus.batch_campus.batch.ItemWriter;
import com.fastcampus.batch_campus.batch.Tasklet;
import org.springframework.stereotype.Component;

public class SimpleTasklet<I, O> implements Tasklet {

    private final ItemReader<I> itemReader;
    private final ItemProcessor<I, O> itemProcessor;
    private final ItemWriter<O> itemWriter;

    public SimpleTasklet(ItemReader<I> itemReader, ItemProcessor<I, O> itemProcessor, ItemWriter<O> itemWriter) {
        this.itemReader = itemReader;
        this.itemProcessor = itemProcessor;
        this.itemWriter = itemWriter;
    }

    @Override
    public void execute() {
        while (true) {
            // read
            I read = itemReader.read();
            if (read == null) {
                break;
            }

            // process
            O process = itemProcessor.process(read);
            if (process == null) {
                continue;
            }

            // write
            itemWriter.write(process);
        }
    }
}
