package com.zepolar.demo.service;

import com.zepolar.demo.entity.BankAccount;
import com.zepolar.demo.entity.Person;
import com.zepolar.demo.repository.BankAccountRepository;
import com.zepolar.demo.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class AccountServiceTest {

    private AccountService accountService;

    @MockBean
    private BankAccountRepository bankAccountRepository;
    @MockBean
    private PersonRepository personRepository;

    @BeforeEach
    void setup(){
        this.accountService = new AccountService(bankAccountRepository, personRepository);
    }

    @Test
    @DisplayName("Should save the information about account - person")
    void shouldSaveInformationAboutAccount(){
        Person person = Person.builder()
                .nationalIdentity("123456789")
                .name("Name")
                .surname("Surname")
                .build();
        BankAccount bankAccount = BankAccount.builder()
                .number("987654321")
                .routing("852369")
                .person(person)
                .build();

        when(personRepository.save(any(Person.class)))
                .thenReturn(person);

        when(bankAccountRepository.save(any(BankAccount.class)))
                .thenReturn(bankAccount);

        this.accountService.createAccount("Name", "Surname", "123456789",
                "987654321", "852369");

        verify(personRepository, times(1)).save(any(Person.class));
        verify(bankAccountRepository, times(1)).save(any(BankAccount.class));
    }



}