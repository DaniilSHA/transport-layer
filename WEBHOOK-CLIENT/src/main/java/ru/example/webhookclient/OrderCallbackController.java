package ru.example.webhookclient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/callback")
public class OrderCallbackController {

    @PostMapping("/order")
    public void order (@RequestBody Order order) {
        System.out.println(new Date() + ": " + order);
    }
}
