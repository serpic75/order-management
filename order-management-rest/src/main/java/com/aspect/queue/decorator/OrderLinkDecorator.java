package com.aspect.queue.decorator;

import com.aspect.queue.model.transformers.OrderRepresentation;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.stereotype.Component;

import static com.aspect.queue.model.ControllerConstants.ORDER_SEGMENT;
import static org.springframework.hateoas.server.mvc.BasicLinkBuilder.linkToCurrentMapping;

@Component
public class OrderLinkDecorator implements LinkDecorator<OrderRepresentation> {
    @Override
    public OrderRepresentation decorate(OrderRepresentation resource) {
        resource.add(linkToCurrentMapping().slash(ORDER_SEGMENT)
                .slash(resource.getLinks(IanaLinkRelations.SELF)).withSelfRel());
        return resource;
    }
}
