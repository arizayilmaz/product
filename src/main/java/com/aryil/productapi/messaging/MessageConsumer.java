package com.aryil.productapi.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.aryil.productapi.config.RabbitMQConfig.*;

@Slf4j
@Service
public class MessageConsumer {

    @RabbitListener(queues = PRODUCT_QUEUE)
    public void consumeProductMessage(String message) {
        log.info("📥 [PRODUCT_QUEUE] {}", message);
    }

    @RabbitListener(queues = CATEGORY_QUEUE)
    public void consumeCategoryMessage(String message) {
        log.info("📥 [CATEGORY_QUEUE] {}", message);
    }

    @RabbitListener(queues = ADMIN_QUEUE)
    public void consumeAdminMessage(String message) {
        log.info("📥 [ADMIN_QUEUE] {}", message);
    }

}
