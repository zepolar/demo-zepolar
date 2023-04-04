package com.zepolar.demo.repository;

import com.zepolar.demo.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

    Page<Transaction> findByAmountEqualsAndCreatedAtBeforeOrderByCreatedAtDesc(Double amount, Date createdAt, Pageable pageable);

    Page<Transaction> findByAmountEqualsOrderByCreatedAtDesc(Double amount, Pageable pageable);

    Page<Transaction> findByCreatedAtBeforeOrderByCreatedAtDesc(Date createdAt, Pageable pageable);

    Page<Transaction> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
