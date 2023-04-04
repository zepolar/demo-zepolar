package com.zepolar.demo.repository;

import com.zepolar.demo.entity.Bank;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BankRepositoryTest extends DatabaseTest{

    @Autowired
    private BankRepository bankRepository;

    @Test
    @DisplayName("Should save bank information on db")
    public void shoudSaveInformation(){
        Bank bank = new Bank();
        bank.setName("Test");
        Bank saved = this.bankRepository.save(bank);
        assertNotNull(saved, "Bank should not be null");
    }
}