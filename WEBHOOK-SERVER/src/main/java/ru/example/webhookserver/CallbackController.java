package ru.example.webhookserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/subscribe")
public class CallbackController {

    private Map<Integer, Unsubscribe> unsubscribes = new HashMap<>();

    @Autowired
    private ChangeOrderStatusPublisher publisher;


    @GetMapping("/order-change-event")
    private void sub(@RequestParam Integer id, @RequestParam String url) {
        Unsubscribe unsubscribe = publisher.subscribe(id, order -> {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.postForEntity(url, order, Void.class);
        });

        unsubscribes.put(id, unsubscribe);
    }

    @GetMapping("/order-change-event/unsub")
    private void unsub(@RequestParam Integer id) {
        if (unsubscribes.containsKey(id))
            unsubscribes.get(id).unsubscribe();
    }
}
