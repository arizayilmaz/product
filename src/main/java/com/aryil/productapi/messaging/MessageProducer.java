package com.aryil.productapi.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.aryil.productapi.config.RabbitMQConfig.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendProductMessage(String message) {
        log.info("📤 [SEND PRODUCT] {}", message);
        rabbitTemplate.convertAndSend(EXCHANGE, "product.event", message);
    }

    public void sendCategoryMessage(String message) {
        log.info("📤 [SEND CATEGORY] {}", message);
        rabbitTemplate.convertAndSend(EXCHANGE, "category.event", message);
    }

    public void sendAdminMessage(String message) {
        log.info("📤 [SEND ADMIN] {}", message);
        rabbitTemplate.convertAndSend(EXCHANGE, "admin.event", message);
    }

}
