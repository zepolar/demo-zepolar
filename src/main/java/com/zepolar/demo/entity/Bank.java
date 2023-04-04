package com.zepolar.demo.entity;

import com.zepolar.demo.entity.audit.Audit;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Bank extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean active;
    public Bank(){
        this.active = Boolean.TRUE;
    }
}
