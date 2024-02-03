package ru.example.webhookserver;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChangeOrderStatusPublisher {

    private Map<Integer, Order> orders = new HashMap<>();
    {
        orders.put(1, new Order(1, "order 1", Status.CREATED));
        orders.put(2, new Order(2, "order 2", Status.CREATED));
        orders.put(3, new Order(3, "order 3", Status.CREATED));
    }

    @Deprecated
    public synchronized Status getStatus(Integer id) {

        if (orders.containsKey(id))
            return orders.get(id).getStatus();

        throw new IllegalStateException();
    }

    public synchronized void changeStatus(Integer id, Status status) {
        if (orders.containsKey(id)) {
            orders.get(id).setStatus(status);
            fireEvent(id);
        }
    }

    private Map<Integer, List<ChangeOrderStatusCallback>> callbacks = new HashMap<>();


    public Unsubscribe subscribe(Integer id, ChangeOrderStatusCallback callback) {

        if (callbacks.containsKey(id))
            callbacks.get(id).add(callback);
        else
            callbacks.put(id, new ArrayList<>() {{
                add(callback);
            }});

        return () -> callbacks.get(id).remove(callback);
    }

    public void fireEvent(Integer id) {
        if (callbacks.containsKey(id))
            callbacks.get(id).forEach(callback -> callback.activate(orders.get(id)));
    }

}
