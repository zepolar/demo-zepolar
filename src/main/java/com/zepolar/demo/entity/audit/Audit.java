package com.zepolar.demo.entity.audit;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Setter
@Getter
@MappedSuperclass
public abstract class Audit {
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    protected void pre(){
        this.createdAt = Calendar.getInstance().getTime();
    }

    @PostUpdate
    protected void post(){
        this.updatedAt = Calendar.getInstance().getTime();
    }
}
