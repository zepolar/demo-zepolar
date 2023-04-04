package com.zepolar.demo.repository;

import com.zepolar.demo.entity.log.PaymentLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentLogRepository extends CrudRepository<PaymentLog, Long> {
}
