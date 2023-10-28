package com.aspect.queue.model.transformers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Objects;

@ApiModel("Order")
@Relation(value = "queue", collectionRelation = "orders")
public class OrderRepresentation extends RepresentationModel {

    public static final String ORDER_ID = "orderId";
    public static final String WAITING_SECONDS = "waitingSeconds";
    public static final String ORDER_INSERT_DATE = "customerSystemName";
    public static final String ORDER_PRIORITY = "withPriority";
    public static final String ORDER_POSITION = "orderPosition";
    public static final String AVERAGE_WAIT_TIME = "averageWaitTime";

    @ApiModelProperty("A queue identifier")
    @JsonProperty(ORDER_ID)
    private final Long representationId;

    @ApiModelProperty("A queue insert date in milliseconds")
    @JsonProperty(ORDER_INSERT_DATE)
    private final Long orderInsertDate;

    @ApiModelProperty("Waiting Seconds in the queue")
    @JsonProperty(WAITING_SECONDS)
    private final Long seconds;

    @ApiModelProperty("A queue name")
    @JsonProperty(ORDER_PRIORITY)
    private final Long orderPriority;

    @ApiModelProperty("An queue position")
    @JsonProperty(ORDER_POSITION)
    private final Integer orderPosition;

    @ApiModelProperty("Average wait Time")
    @JsonProperty(AVERAGE_WAIT_TIME)
    private final Double averageWaitTime;

//    public OrderRepresentation() {
//        this.seconds = null;
//        representationId = null;
//        orderInsertDate = null;
//        orderPriority = null;
//        orderPosition = null;
//        averageWaitTime = null;
//    }

    @JsonCreator
    public OrderRepresentation(@JsonProperty(ORDER_ID) Long representationId,
            @JsonProperty(ORDER_INSERT_DATE) Long orderInsertDate,
            @JsonProperty(ORDER_PRIORITY) Long orderPriority,
            @JsonProperty(WAITING_SECONDS) Long seconds,
            @JsonProperty(ORDER_POSITION) Integer orderPosition,
            @JsonProperty(AVERAGE_WAIT_TIME) Double averageWaitTime){
        this.representationId = representationId;
        this.orderInsertDate = orderInsertDate;
        this.orderPriority = orderPriority;
        this.seconds = seconds;
        this.orderPosition = orderPosition;
        this.averageWaitTime = averageWaitTime;
    }

    public Long getRepresentationId() {
        return representationId;
    }

    public Long getOrderInsertDate() {
        return orderInsertDate;
    }

    public Long getOrderPriority() {
        return orderPriority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.orderPriority, this.orderPriority, this.orderInsertDate,
                this.representationId);
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof OrderRepresentation)) {
            return false;
        }
        final OrderRepresentation other = (OrderRepresentation) obj;
        return Objects.equals(this.orderPriority, other.orderPriority)
                && Objects.equals(this.orderInsertDate, other.orderInsertDate) && Objects.equals(this.representationId, other.representationId);
    }

    @Override
    public String toString() {
        return "OrderRepresentation{" +
                "representationId=" + representationId +
                ", orderInsertDate=" + orderInsertDate +
                ", seconds=" + seconds +
                ", orderPriority=" + orderPriority +
                ", orderPosition=" + orderPosition +
                ", averageWaitTime=" + averageWaitTime +
                '}';
    }
}
