package com.zepolar.demo.entity.log;

import com.zepolar.demo.entity.enumeration.EnumStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
@Builder
public class WalletLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String wallet;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private EnumStatus status;

    public WalletLog(){

    }

    @PrePersist
    void prePersist(){
        this.createdAt = Calendar.getInstance().getTime();
    }

}
