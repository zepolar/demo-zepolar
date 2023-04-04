package com.zepolar.demo.entity;

import com.zepolar.demo.entity.audit.Audit;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@Builder
public class Person extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String nationalIdentity;

    public Person( ) {
    }

    public String fullName(){
        return this.name + " " + this.surname;
    }
}
