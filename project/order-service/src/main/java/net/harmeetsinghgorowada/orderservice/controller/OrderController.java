package net.harmeetsinghgorowada.orderservice.controller;

import net.harmeetsinghgorowada.basedomains.dto.Order;
import net.harmeetsinghgorowada.basedomains.dto.OrderEvent;
import net.harmeetsinghgorowada.orderservice.kafka.OrderProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order) {
        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("Order Status is in pending state");
        orderEvent.setOrder(order);

        orderProducer.sendMessage(orderEvent);
        
        return "Order Placed successfully";

    }
}
