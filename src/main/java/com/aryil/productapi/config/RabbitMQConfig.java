package com.aryil.productapi.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PRODUCT_QUEUE = "product_queue";
    public static final String CATEGORY_QUEUE = "category_queue";

    public static final String EXCHANGE = "product_category_exchange";

    public static final String PRODUCT_ROUTING_KEY = "product.#";
    public static final String CATEGORY_ROUTING_KEY = "category.#";

    public static final String ADMIN_QUEUE = "admin_queue";
    public static final String ADMIN_ROUTING_KEY = "admin.#";

    @Bean
    public Queue adminQueue() {
        return new Queue(ADMIN_QUEUE, true);
    }

    @Bean
    public Binding bindingAdminQueue(Queue adminQueue, TopicExchange exchange) {
        return BindingBuilder.bind(adminQueue).to(exchange).with(ADMIN_ROUTING_KEY);
    }

    @Bean
    public Queue productQueue() {
        return new Queue(PRODUCT_QUEUE, true); // durable queue
    }

    @Bean
    public Queue categoryQueue() {
        return new Queue(CATEGORY_QUEUE, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding bindingProductQueue(Queue productQueue, TopicExchange exchange) {
        return BindingBuilder.bind(productQueue).to(exchange).with(PRODUCT_ROUTING_KEY);
    }

    @Bean
    public Binding bindingCategoryQueue(Queue categoryQueue, TopicExchange exchange) {
        return BindingBuilder.bind(categoryQueue).to(exchange).with(CATEGORY_ROUTING_KEY);
    }
}
