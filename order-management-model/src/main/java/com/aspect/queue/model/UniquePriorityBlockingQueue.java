package com.aspect.queue.model;

import com.aspect.queue.model.exceptions.DuplicatedOrderException;
import org.springframework.http.HttpStatus;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

public class UniquePriorityBlockingQueue extends PriorityBlockingQueue<Order> {

    private static final long serialVersionUID = 1905122041950251207L;

    private Set<Order> set = new HashSet<>();
    private OrderComparator comparator = new OrderComparator();


    public UniquePriorityBlockingQueue(){

    }

    public UniquePriorityBlockingQueue(int initialCapacity,
            Comparator<Order> comparator) {
        super(initialCapacity, comparator);
    }

    public synchronized Long getMaxPriority() {
        return size() > 0 ? Optional.of(peek()).get().getPriority() : 1l;
    }

    public boolean insert(Order e) {
        boolean isAdded = false;
        if (set.add(e)) {
            isAdded=offer(e);
            sortQueue();
            return isAdded;
        }
        throw new DuplicatedOrderException("Order " + e.getId().getId() + " already present");
    }

    @Override
    public Order take() throws InterruptedException {
        Order order = super.take();
        set.remove(order);
        sortQueue();
        return order;
    }

    private void resetPriority() {
        PriorityBlockingQueue<Order> newPriorityBlockingQueue = new PriorityBlockingQueue<>(this.size(), comparator);
        Order item =this.poll();
        while(item != null){
            this.set.remove(item);
            newPriorityBlockingQueue.add(item);
            item = this.poll();
        }

        this.addAll(newPriorityBlockingQueue);
        this.set.addAll(newPriorityBlockingQueue);
    }

    public synchronized boolean remove(Order key) {
        if (super.remove(key)) {
            return set.remove(key);
        }
        return false;
    }

    private void sortQueue() {
        setPriorities();
        if(!this.isEmpty()){
            resetPriority();
        }
    }

    private void setPriorities() {
        stream().filter(o ->
                o.getId().getIdClass().equals(ClassifiedIdentifier.IdClass.MANAGMENTOVERRIDE)).forEach(o ->
            o.setPriority(getMaxPriority() + getSeconds(o)));
        stream().filter(o ->
                o.getId().getIdClass().equals(ClassifiedIdentifier.IdClass.VIP)).forEach(o ->
             o.setPriority((long) Math.max(4, getVipFormula(getSeconds(o), true))));
        stream().filter(o ->
                o.getId().getIdClass().equals(ClassifiedIdentifier.IdClass.PRIORITY)).forEach(o ->
             o.setPriority((long) Math.max(3, getVipFormula(getSeconds(o), false))));
        stream().filter(o ->
                o.getId().getIdClass().equals(ClassifiedIdentifier.IdClass.NORMAL)).forEach(o ->
            o.setPriority(getSeconds(o)));
    }


    private long getSeconds(Order order) {
        order.setWaitingTime(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - order.getTimestamp()));
        return order.getWaitingTime();
    }

    private Double getVipFormula(long seconds, boolean isVip) {
        if (isVip) {
            return Optional.of(2 * seconds * Math.log(seconds)).get().equals(Double.NaN) ?
                    0.0 :
                    Optional.of(2 * seconds * Math.log(seconds)).get();
        } else {
            return Optional.of(seconds * Math.log(seconds)).get().equals(Double.NaN) ?
                    0.0 :
                    Optional.of(seconds * Math.log(seconds)).get();
        }
    }

    public Integer getIndexOf(Order order) {
        int i =0;
        for(Order item : this){
            if(item.equals(order)){
                break;
            }
            i++;
        }
        return i;
    }
}
