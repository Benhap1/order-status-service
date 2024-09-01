package com.kafka.listener;

import com.kafka.event.OrderEvent;
import com.kafka.event.OrderStatusEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class OrderEventListener {

    private final KafkaTemplate<String, OrderStatusEvent> kafkaTemplate;

    public OrderEventListener(KafkaTemplate<String, OrderStatusEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "order-topic", groupId = "order-status-group", containerFactory = "kafkaListenerContainerFactory")
    public void listen(OrderEvent orderEvent) {
        // Создание события статуса заказа
        OrderStatusEvent statusEvent = new OrderStatusEvent("CREATED", Instant.now());
        // Отправка события в топик order-status-topic
        kafkaTemplate.send("order-status-topic", statusEvent);
    }
}
