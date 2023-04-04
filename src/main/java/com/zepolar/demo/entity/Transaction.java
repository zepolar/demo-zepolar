package com.zepolar.demo.entity;

import com.zepolar.demo.entity.audit.Audit;
import com.zepolar.demo.entity.enumeration.EnumStatus;
import com.zepolar.demo.entity.enumeration.EnumTypeOperation;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@Builder
public class Transaction extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private Double amount;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnumStatus status;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnumTypeOperation typeOperation;

    public Transaction( ) {

    }
}
