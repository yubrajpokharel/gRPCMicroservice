package com.yubraj.microservice.grpc;


import com.yubraj.microservice.models.grpc.Balance;
import com.yubraj.microservice.models.grpc.BankCheckRequest;
import com.yubraj.microservice.models.grpc.BankServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankServiceTest {

    BankServiceGrpc.BankServiceBlockingStub serviceBlockingStub;

    @BeforeAll
    public void setUp() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        this.serviceBlockingStub = BankServiceGrpc.newBlockingStub(channel);
    }


    @Test
    public void getBalanceTest () {

        BankCheckRequest request = BankCheckRequest.newBuilder().setAccountNumber(10).build();
        Balance balance = this.serviceBlockingStub.getBalance(request);
        System.out.println(balance);
    }

}
