package com.zepolar.demo.service;

import com.zepolar.demo.entity.BankAccount;
import com.zepolar.demo.entity.Person;
import com.zepolar.demo.repository.BankAccountRepository;
import com.zepolar.demo.repository.PersonRepository;
import com.zepolar.demo.request.AccountRequest;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private BankAccountRepository bankAccountRepository;
    private PersonRepository personRepository;

    public AccountService(BankAccountRepository bankAccountRepository, PersonRepository personRepository){
        this.bankAccountRepository = bankAccountRepository;
        this.personRepository = personRepository;
    }

    public void createAccount(String name, String surname, String dni, String accountNumber, String routingNumber){
        Person person = Person.builder()
                .nationalIdentity(dni)
                .name(name).surname(surname)
                .build();
        BankAccount bankAccount = BankAccount
                .builder()
                .number(accountNumber)
                .routing(routingNumber)
                .person(person)
                .build();
        personRepository.save(person);
        bankAccountRepository.save(bankAccount);
    }

    public void createAccount(AccountRequest accountRequest){
        this.createAccount(accountRequest.getName(), accountRequest.getSurname(), accountRequest.getDni(), accountRequest.getAccountNumber(), accountRequest.getRoutingNumber());
    }

}
