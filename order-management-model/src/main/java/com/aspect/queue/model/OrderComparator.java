package com.aspect.queue.model;

import java.io.Serializable;
import java.util.Comparator;

public class OrderComparator implements Comparator<Order>, Serializable {
    @Override
    public int compare(Order o1, Order o2) {
        return o2.getId().getRank() - o1.getId().getRank();
    }
}
