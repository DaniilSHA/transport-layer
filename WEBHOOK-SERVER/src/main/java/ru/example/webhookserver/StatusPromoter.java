package ru.example.webhookserver;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class StatusPromoter {

    @Autowired
    private ChangeOrderStatusPublisher publisher;


    @PostConstruct
    private void init() {

        for (int i = 1; i < 4; i++) {
            int finalI = i;
            CompletableFuture.delayedExecutor(20, TimeUnit.SECONDS).execute(() -> {
                Status[] statuses = Status.values();
                int length = Status.values().length;

                for (int j = 1; j <= length-1; j++) {

                    publisher.changeStatus(finalI, statuses[j]);

                    try {
                        Thread.sleep(finalI * 1000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
        }
    }
}
