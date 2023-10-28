package com.aspect.queue.web;

import com.aspect.queue.decorator.LinkDecorator;
import com.aspect.queue.model.ClassifiedIdentifier;
import com.aspect.queue.model.Order;
import com.aspect.queue.model.OrderRestError;
import com.aspect.queue.model.exceptions.OrderException;
import com.aspect.queue.model.provider.BaseProvider;
import com.aspect.queue.model.transformers.OrderRepresentation;
import com.aspect.queue.model.transformers.OrderRepresentationBuilder;
import com.aspect.queue.model.transformers.OrderTransformer;
import com.aspect.queue.model.transformers.Transformer;
import com.aspect.queue.web.validator.Validator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.aspect.queue.model.ControllerConstants.BASE_URI;
import static com.aspect.queue.model.ControllerConstants.INSERT_ENDPOINT;
import static com.aspect.queue.model.ControllerConstants.URI_SEPARATOR;

@Api(value = "", description = "APIs to manage ")
@RestController
@RequestMapping(BASE_URI + URI_SEPARATOR + INSERT_ENDPOINT)
public class OrderController {
    private final LinkDecorator<OrderRepresentation> decorator;
    private final BaseProvider<Order> provider;
    private final Validator<OrderRepresentation> validator;
    private final Transformer<Order, OrderRepresentation> transformer;

    @Autowired
    public OrderController(
            @Qualifier("orderLinkDecorator") LinkDecorator<OrderRepresentation> decorator,
            @Qualifier("orderRepresentationValidator") Validator<OrderRepresentation> validator,
            @Qualifier("orderProvider") BaseProvider<Order> provider,
            OrderTransformer transformer) {
        this.decorator = decorator;
        this.validator = validator;
        this.transformer = transformer;
        this.provider = provider;
    }

    /**
     * @param orderIdParam
     * @param timeStamp
     * @return
     */

    @ApiOperation(value = "Create a new Order", notes = "Use this operation to define a new queue. ")
    @ApiResponses({
            @ApiResponse(code = 201, message = "The queue is included in the response.",
                    response = OrderRepresentation.class),
            @ApiResponse(code = 401, message = "The Attempt is Unauthorized.", response = OrderRepresentation.class),
            @ApiResponse(code = 400, message = "The request validation failed.", response = OrderRestError.class),
            @ApiResponse(code = 403, message = "The Attempt is forbidden.", response = OrderRestError.class),
            @ApiResponse(code = 409, message = "The queue is already in use.", response = OrderRestError.class) })
    @PostMapping(value = "/add/{orderId}/{timeStamp}", produces = { "application/json" })
    public ResponseEntity<OrderRepresentation> createOrder(
            @ApiParam(value = "The identifier to create an Order", required = true) @PathVariable(
                    value = "orderId") String orderIdParam,
            @ApiParam(value = "The timeStamp to create an Order", required = true) @PathVariable(
                    "timeStamp") String timeStamp) {
        OrderRepresentation representation = new OrderRepresentationBuilder(Long.valueOf(orderIdParam))
                .withTimestamp(Long.valueOf(timeStamp)).createRepresentation();
        return createOrUpdate(true, representation, transformer, decorator, provider);
    }

    /**
     * @return a ResponseEntity
     */

    @ApiOperation(value = "Delete The Order on Top", notes = "Retrieve the Order on top of the Queue and remove it. ")
    @ApiResponses({
            @ApiResponse(code = 204, message = "The highest ranked ID and the time it was entered into the queue is part of  the response."),
            @ApiResponse(code = 400, message = "The request validation failed.", response = OrderRestError.class),
            @ApiResponse(code = 401, message = "The authentication failed.", response = OrderRestError.class),
            @ApiResponse(code = 403, message = "The request could not be authorized.", response = OrderRestError.class),
            @ApiResponse(code = 404, message = "The Order could not be found.", response = OrderRestError.class) })
    @DeleteMapping(value = "/deleteTopOrder", produces = { "application/json" })
    public ResponseEntity<OrderRepresentation> deleteTopOrder() {

        Order foundOrder = findTop(provider);
        OrderRepresentation orderRepresentation = transformer.transform(foundOrder);
        return new ResponseEntity<>(orderRepresentation, HttpStatus.NO_CONTENT);
    }

    /**
     * @param orderIdParam
     * @return
     * @throws OrderException
     */
    @ApiOperation(value = "Get An Order by ID and delete it", notes = "Retrieve an Order by its ID. ")
    @ApiResponses({
            @ApiResponse(code = 204, message = "The Order deleted is part of the response."),
            @ApiResponse(code = 400, message = "The request validation failed.", response = OrderRestError.class),
            @ApiResponse(code = 401, message = "The authentication failed.", response = OrderRestError.class),
            @ApiResponse(code = 403, message = "The request could not be authorized.", response = OrderRestError.class),
            @ApiResponse(code = 404, message = "The Order  could not be found.", response = OrderRestError.class) })
    @DeleteMapping(value = "/deleteById/{orderId}")
    public ResponseEntity<OrderRepresentation> deleteOrderById(
            @ApiParam(value = "The identifier of the existing Order") @PathVariable("orderId") String orderIdParam) {
        Order key = new Order(new ClassifiedIdentifier(getid(orderIdParam)));
        Order foundOrder = find(provider, key);
        OrderRepresentation orderRepresentation = transformer.transform(foundOrder);
        orderRepresentation = decorator.decorate(orderRepresentation);
        return new ResponseEntity<>(orderRepresentation, HttpStatus.OK);
    }

    /**
     */
    @ApiOperation(value = "List all Orders", notes = "This operation lists all orders.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "A list of orders is included in the response."),
            @ApiResponse(code = 400, message = "The request validation failed.", response = OrderRestError.class),
            @ApiResponse(code = 401, message = "The authentication failed.", response = OrderRestError.class),
            @ApiResponse(code = 403, message = "The request could not be authorized.",
                    response = OrderRestError.class), })
    @GetMapping(value = "/getAll", produces = { "application/json" })
    public ResponseEntity<List<OrderRepresentation>> findAllOrders() throws OrderException {

        List<Order> orders = findAll(provider);

        List<OrderRepresentation> representation = createRepresentationList(orders);
        return new ResponseEntity<>(representation, HttpStatus.OK);
    }

    /**
     * @return
     * @throws OrderException
     */
    @ApiOperation(value = "Average waiting of all Orders", notes = "This operation shows the average wait time.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "An endpoint to get the average wait time."),
            @ApiResponse(code = 400, message = "The request validation failed.", response = OrderRestError.class),
            @ApiResponse(code = 401, message = "The authentication failed.", response = OrderRestError.class),
            @ApiResponse(code = 403, message = "The request could not be authorized.", response = OrderRestError.class), })
    @GetMapping(value = "/avgwaittime",produces = { "application/json" })
    public ResponseEntity<OrderRepresentation> findAverageWaiting() throws OrderException {
        OrderRepresentation representation = transformer.transformValue(provider.averageWaitingTime());
        return new ResponseEntity<>(representation, HttpStatus.OK);
    }

    /**
     * @param orderIdParam
     * @return
     * @throws OrderException
     */
    @ApiOperation(value = "Find the position of an Order", notes = "An endpoint to get the position of a specific ID in the queue.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "An endpoint to get the position of a specific ID in the queue."),
            @ApiResponse(code = 400, message = "The request validation failed.", response = OrderRestError.class),
            @ApiResponse(code = 401, message = "The authentication failed.", response = OrderRestError.class),
            @ApiResponse(code = 403, message = "The request could not be authorized.", response = OrderRestError.class) })
    @GetMapping(value = "/position/{orderId}", produces = { "application/json"})
    public ResponseEntity<OrderRepresentation> findPosition(
            @ApiParam(value = "The identifier of the existing Order") @PathVariable(
                    "orderId") String orderIdParam) throws OrderException {

        Optional<Order> order = checkOrder(orderIdParam);
        OrderRepresentation representation = transformer.transformValue(provider.findPosition(order.get()));
        return new ResponseEntity<>(representation, HttpStatus.OK);
    }

    private Optional<Order> checkOrder(@ApiParam(value = "The identifier of the existing Order") @PathVariable(
            "orderId") String orderIdParam) {
        Order order = new Order(new ClassifiedIdentifier(getid(orderIdParam)));
        if (provider.isPresent(order)) {
            return Optional.of(order);
        }
        return Optional.empty();
    }

    private List<OrderRepresentation> createRepresentationList(List<Order> orders) {
        return orders.stream().map(o -> transformer.transform(o)).collect(Collectors.toList()).stream()
                .map(r -> decorator.decorate(r)).collect(
                        Collectors.toList());
    }

    private List<Order> findAll(BaseProvider<Order> provider) {
        return provider.findAll();
    }

    static <O, R extends RepresentationModel> ResponseEntity<R> createOrUpdate(boolean doCreate, R representation,
            Transformer<O, R> transformer, LinkDecorator<R> decorator, BaseProvider<O> provider)
            throws OrderException {

        O input = transformer.extract(representation);
        O result = provider.create(input);
        R resource = transformer.transform(result);
        R response = decorator.decorate(resource);
        HttpStatus httpStatus = doCreate ? HttpStatus.CREATED : HttpStatus.OK;
        return new ResponseEntity<R>(response, httpStatus);
    }


    private Order find(BaseProvider<Order> provider, Order key) {
        return provider.find(key);
    }

    private Order findTop(BaseProvider<Order> provider) {
        return provider.findTop().get();
    }

    private Long getid(String orderIdParam) {
        return Long.parseLong(orderIdParam);
    }
}
