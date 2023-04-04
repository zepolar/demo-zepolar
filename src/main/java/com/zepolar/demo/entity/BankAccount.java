package com.zepolar.demo.entity;

import com.zepolar.demo.entity.audit.Audit;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@Builder
public class BankAccount extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String number;

    @Column(unique = true, nullable = false)
    private String routing;

    @OneToOne(fetch = FetchType.EAGER)
    private Person person;

    public BankAccount(){

    }
}
