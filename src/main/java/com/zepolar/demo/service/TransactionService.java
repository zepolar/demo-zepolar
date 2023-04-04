package com.zepolar.demo.service;

import com.zepolar.demo.entity.BankAccount;
import com.zepolar.demo.entity.Transaction;
import com.zepolar.demo.entity.enumeration.EnumStatus;
import com.zepolar.demo.entity.enumeration.EnumTypeOperation;
import com.zepolar.demo.entity.log.PaymentLog;
import com.zepolar.demo.exception.TransactionException;
import com.zepolar.demo.payment.PaymentAPI;
import com.zepolar.demo.payment.request.*;
import com.zepolar.demo.payment.response.PaymentResponse;
import com.zepolar.demo.repository.BankAccountRepository;
import com.zepolar.demo.repository.PaymentLogRepository;
import com.zepolar.demo.repository.TransactionRepository;
import com.zepolar.demo.repository.WalletLogRepository;
import com.zepolar.demo.request.EnumCurrency;
import com.zepolar.demo.request.TransactionRequest;
import com.zepolar.demo.wallet.WalletAPI;
import com.zepolar.demo.wallet.request.WalletRequest;
import com.zepolar.demo.wallet.response.BalanceResponse;
import com.zepolar.demo.wallet.response.WalletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class TransactionService {

    private final Logger logger = Logger.getLogger(TransactionService.class.getName());

    private WalletAPI walletAPI;
    private final PaymentAPI paymentAPI;
    private final WalletLogRepository walletLogRepository;
    private final PaymentLogRepository paymentLogRepository;
    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionService(WalletAPI walletAPI,
                              PaymentAPI paymentAPI,
                              BankAccountRepository bankAccountRepository,
                              WalletLogRepository walletLogRepository,
                              PaymentLogRepository paymentLogRepository,
                              TransactionRepository transactionRepository) {
        this.paymentAPI = paymentAPI;
        this.walletAPI = walletAPI;
        this.bankAccountRepository = bankAccountRepository;
        this.walletLogRepository =walletLogRepository;
        this.paymentLogRepository = paymentLogRepository;
        this.transactionRepository = transactionRepository;
    }

    public void applyChanges(TransactionRequest transactionRequest) {
        logger.info("Applying changes to "+ transactionRequest);
        Optional<BankAccount> optionalSource = bankAccountRepository.findByNumber(transactionRequest.getSourceNumber());
        Optional<BankAccount> optionalDestination = bankAccountRepository.findByNumber(transactionRequest.getDestinationNumber());

        EnumCurrency currencySource = EnumCurrency.valueOf(transactionRequest.getSourceCurrency());
        EnumCurrency currencyDestination = EnumCurrency.valueOf(transactionRequest.getDestinationCurrency());

        if (optionalSource.isPresent() && optionalDestination.isPresent()) {
            BankAccount destination = optionalDestination.get();
            BankAccount source = optionalSource.get();

            BalanceResponse balanceResponse = this.walletAPI.retrieveBalance(source.getPerson().getId());
            if (balanceResponse.getBalance().compareTo(0d) > 0 &&
                    balanceResponse.getBalance().compareTo(transactionRequest.getAmount()) > 0) {

                Double realValue = transactionRequest.getAmount() - transactionRequest.getAmount()*0.10;

                PaymentResponse paymentResponse = this.executePayment(source, currencySource, transactionRequest.getAmount(), destination, currencyDestination);
                WalletResponse walletResponseSource = this.withdrawWallet(source.getPerson().getId(), transactionRequest.getAmount());
                WalletResponse walletResponseDestine = this.topupWallet(destination.getPerson().getId(), realValue);

                PaymentLog paymentLog = PaymentLog.builder().payment(paymentResponse.toString()).build();

                Transaction debit = Transaction
                        .builder()
                        .typeOperation(EnumTypeOperation.Debit)
                        .amount(transactionRequest.getAmount())
                        .userId(walletResponseSource.getUserId())
                        .status(EnumStatus.CREATED)
                        .build();

                Transaction credit = Transaction
                        .builder()
                        .typeOperation(EnumTypeOperation.Credit)
                        .amount(realValue)
                        .userId(walletResponseDestine.getUserId())
                        .status(EnumStatus.CREATED)
                        .build();

                logger.info("<< Start Saving Log >>");
                paymentLogRepository.save(paymentLog);
                transactionRepository.save(debit);
                transactionRepository.save(credit);
                logger.info("<< Finish Saving Log >>");
            } else {
                throw new TransactionException("No money");
            }
        } else {
            throw new TransactionException("Something is wrong");
        }
    }

    public Page<Transaction> retrieve(Double amount, Date createdAt, Pageable pageable){
        if(null==amount){
            if(null==createdAt){
                return transactionRepository.findAllByOrderByCreatedAtDesc(pageable);
            }
            else{
                return transactionRepository.findByCreatedAtBeforeOrderByCreatedAtDesc(createdAt, pageable);
            }
        }
        else{
            if (createdAt==null){
                return transactionRepository.findByAmountEqualsOrderByCreatedAtDesc(amount, pageable);
            }
            else{
                return transactionRepository.findByAmountEqualsAndCreatedAtBeforeOrderByCreatedAtDesc(amount, createdAt, pageable);
            }
        }
    }



    private PaymentResponse executePayment(BankAccount sourceAccount, EnumCurrency sourceCurrency, Double amount,
                                           BankAccount destinationAccount, EnumCurrency destinationCurrency) {

        Account accountSource = Account.builder()
                .accountNumber(sourceAccount.getNumber())
                .routingNumber(sourceAccount.getRouting())
                .currency(sourceCurrency.name())
                .build();

        Account accountDestination = Account.builder()
                .accountNumber(destinationAccount.getNumber())
                .routingNumber(destinationAccount.getRouting())
                .currency(destinationCurrency.name())
                .build();

        SourceInformation sourceInformation = SourceInformation.builder().name(sourceAccount.getPerson().fullName()).build();
        Destination destination = Destination.builder().account(accountDestination).name(destinationAccount.getPerson().fullName()).build();

        Source source = Source.builder().type("COMPANY").account(accountSource).sourceInformation(sourceInformation).build();

        PaymentRequest paymentRequest = PaymentRequest.builder()
                .source(source)
                .amount(amount)
                .destination(destination)
                .build();

        return this.paymentAPI.createPayment(paymentRequest);
    }

    private WalletResponse withdrawWallet(Long userId, Double amount) {
        logger.info("withdraw Wallet to User "+userId + " with amount "+amount);
        WalletRequest walletRequest = WalletRequest.builder()
                .amount(amount * -1d)
                .userId(userId)
                .build();
        return this.walletAPI.createTransaction(walletRequest);
    }

    private WalletResponse topupWallet(Long userId, Double amount) {
        logger.info("topup Wallet to User "+userId + " with amount "+amount);
        WalletRequest walletRequest = WalletRequest.builder()
                .amount(amount)
                .userId(userId)
                .build();
        return this.walletAPI.createTransaction(walletRequest);
    }

}
