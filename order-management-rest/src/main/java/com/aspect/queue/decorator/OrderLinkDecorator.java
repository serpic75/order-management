package com.aspect.queue.decorator;

import com.aspect.queue.model.transformers.OrderRepresentation;
import org.springframework.stereotype.Component;

import static com.aspect.queue.model.ControllerConstants.ORDER_SEGMENT;
import static org.springframework.hateoas.mvc.BasicLinkBuilder.linkToCurrentMapping;

@Component
public class OrderLinkDecorator implements LinkDecorator<OrderRepresentation> {
    @Override
    public OrderRepresentation decorate(OrderRepresentation resource) {
        resource.add(linkToCurrentMapping().slash(ORDER_SEGMENT)
                .slash(resource.getId()).withSelfRel());
        return resource;
    }
}
