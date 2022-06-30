package com.yubraj.microservice.grpc;


import com.google.common.util.concurrent.Uninterruptibles;
import com.yubraj.microservice.grpc.response.MoneyStreamingResponse;
import com.yubraj.microservice.grpc.service.BankService;
import com.yubraj.microservice.models.grpc.Balance;
import com.yubraj.microservice.models.grpc.BankCheckRequest;
import com.yubraj.microservice.models.grpc.BankServiceGrpc;
import com.yubraj.microservice.models.grpc.WithdrawRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankServiceTest {

    BankServiceGrpc.BankServiceBlockingStub serviceBlockingStub;
    BankServiceGrpc.BankServiceStub bankServiceStub;

    @BeforeAll
    public void setUp() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        this.serviceBlockingStub = BankServiceGrpc.newBlockingStub(channel);
        this.bankServiceStub = BankServiceGrpc.newStub(channel);
    }


    @Test
    public void getBalanceTest () {

        BankCheckRequest request = BankCheckRequest.newBuilder().setAccountNumber(10).build();
        Balance balance = this.serviceBlockingStub.getBalance(request);
        System.out.println(balance);
    }

    @Test
    public void withDrawTest() {
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder().setAccountNumber(9).setAmount(50).build();
        serviceBlockingStub.withDraw(withdrawRequest).forEachRemaining(
                money -> {
                    System.out.println(money.getValue());
                }
        );
    }

    @Test
    public void withDrawTestAsync() {
        WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder().setAccountNumber(11).setAmount(50).build();
        bankServiceStub.withDraw(withdrawRequest, new MoneyStreamingResponse());
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
    }

}
