package com.aspect.queue.config;

import com.aspect.queue.model.OrderComparator;
import com.aspect.queue.model.UniquePriorityBlockingQueue;
import com.aspect.queue.model.transformers.OrderRepresentation;
import com.aspect.queue.web.validator.OrderValidator;
import com.aspect.queue.web.validator.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartUpConfig {

    @Bean
    public Validator<OrderRepresentation> orderRepresentationValidator(){
        return new OrderValidator();
    }

    @Bean
    public UniquePriorityBlockingQueue uniquePriorityBlockingQueue(){
        return new UniquePriorityBlockingQueue(1, new OrderComparator());
    }
}
