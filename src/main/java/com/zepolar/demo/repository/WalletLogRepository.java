package com.zepolar.demo.repository;

import com.zepolar.demo.entity.log.WalletLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletLogRepository extends CrudRepository<WalletLog, Long> {
}
