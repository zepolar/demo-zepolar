package com.zepolar.demo.repository;

import com.zepolar.demo.entity.BankAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends CrudRepository<BankAccount, Long> {

    Optional<BankAccount> findByNumber(String number);

    Optional<BankAccount> findByRouting(String routing);
}
