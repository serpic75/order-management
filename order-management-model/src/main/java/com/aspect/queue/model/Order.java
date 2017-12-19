package com.aspect.queue.model;

import java.io.Serializable;
import java.util.Objects;

public class Order implements Comparable<Order>, Serializable {

    private ClassifiedIdentifier id;
    private Long timestamp;
    private Long priority;
    private long waitingTime;

    public Order(ClassifiedIdentifier id, Long timestamp){
        this.id=id;
        this.timestamp = timestamp;
        this.priority=id.getRank().longValue();
    }

    public Order(ClassifiedIdentifier classifiedIdentifier) {
        this.id =classifiedIdentifier;
    }

    public ClassifiedIdentifier getId() {
        return id;
    }

    public void setId(ClassifiedIdentifier id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Long getPriority() {
        return priority;
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(long waitingTime) {
        this.waitingTime = waitingTime;
    }

    @Override
    public int compareTo(Order o) {
        return o.getPriority().compareTo(this.priority);
    }

    @Override
    public int hashCode() {
        return  Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Order that = (Order) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", withTimestamp=" + timestamp +
                ", withPriority=" + priority +
                ", waitingTime=" + waitingTime +
                '}';
    }
}
