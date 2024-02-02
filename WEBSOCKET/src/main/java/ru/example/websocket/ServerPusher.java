package ru.example.websocket;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ServerPusher {

    @Autowired
    private SimpMessagingTemplate template;

    @PostConstruct
    public void init() {
        CompletableFuture.runAsync(() -> {
            while (true) {
                template.convertAndSend("/chat/common", new ClientMsg("system", "ok..."));
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
