package ru.example.webhookclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WebhookClientApplication {

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(WebhookClientApplication.class, args);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForEntity("http://localhost:8081/subscribe/order-change-event?id=1&url=http://localhost:8080/callback/order", Void.class);
        restTemplate.getForEntity("http://localhost:8081/subscribe/order-change-event?id=2&url=http://localhost:8080/callback/order", Void.class);
        restTemplate.getForEntity("http://localhost:8081/subscribe/order-change-event?id=3&url=http://localhost:8080/callback/order", Void.class);


        Thread.sleep(22000);
        restTemplate.getForEntity("http://localhost:8081/subscribe/order-change-event/unsub?id=3", Void.class);


    }
}
