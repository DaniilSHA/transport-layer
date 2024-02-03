package ru.example.webhookserver;

public interface ChangeOrderStatusCallback {
    void activate(Order order);
}
