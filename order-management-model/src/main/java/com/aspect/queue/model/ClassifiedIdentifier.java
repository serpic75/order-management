package com.aspect.queue.model;


import com.aspect.queue.model.exceptions.NonClassifiedIdentifierException;
import org.apache.commons.lang3.Range;

import java.util.Objects;

import static com.aspect.queue.model.ClassifiedIdentifier.IdClass.MANAGMENTOVERRIDE;
import static com.aspect.queue.model.ClassifiedIdentifier.IdClass.NORMAL;
import static com.aspect.queue.model.ClassifiedIdentifier.IdClass.PRIORITY;
import static com.aspect.queue.model.ClassifiedIdentifier.IdClass.VIP;

public class ClassifiedIdentifier implements Comparable<ClassifiedIdentifier>{

    enum IdClass {
        MANAGMENTOVERRIDE,
        VIP,
        PRIORITY,
        NORMAL
    }

    private static final Range<Long> idRange =  Range.between(1L, 9223372036854775807L);

    private final Long id;
    private IdClass idClass;
    private Integer rank;

    public Long getId() {
        return id;
    }

    public IdClass getIdClass() {
        return idClass;
    }

    public Integer getRank() {
        return rank;
    }

    public void setIdClass(Long id) {
        if(idRange.contains(id)) {
            if (id % 3 == 0 && id % 5 == 0) {
                this.idClass = MANAGMENTOVERRIDE;
                this.rank = 4;
            } else if (id % 5 == 0) {
                this.idClass = VIP;
                this.rank = 3;
            } else if (id % 3 == 0) {
                this.idClass = PRIORITY;
                this.rank = 2;
            } else {
                this.idClass = NORMAL;
                this.rank = 1;
            }
        } else {
            throw new NonClassifiedIdentifierException("Id not permitted - out of range");
        }
    }

    public ClassifiedIdentifier(Long id){
        this.id =id;
        this.setIdClass(id);
    }

    @Override
    public int compareTo(ClassifiedIdentifier o) {
        return this.idClass.compareTo(o.idClass);
    }

    @Override
    public String toString() {
        return "ClassifiedIdentifier{" +
                "id=" + id +
                ", idClass=" + idClass +
                ", rank=" + rank +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idClass, rank);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ClassifiedIdentifier that = (ClassifiedIdentifier) obj;
        return Objects.equals(id, that.id) &&
                Objects.equals(idClass, that.idClass) &&
                Objects.equals(rank, that.rank);
    }
}
