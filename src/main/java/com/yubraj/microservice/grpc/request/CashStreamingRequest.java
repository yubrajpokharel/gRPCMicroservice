package com.yubraj.microservice.grpc.request;

import com.yubraj.microservice.grpc.database.AccountDatabase;
import com.yubraj.microservice.models.grpc.Balance;
import com.yubraj.microservice.models.grpc.DepositRequest;
import io.grpc.stub.StreamObserver;

public class CashStreamingRequest implements StreamObserver<DepositRequest> {

    private StreamObserver<Balance> balanceObserver;
    int accountBalance;

    public CashStreamingRequest(StreamObserver<Balance> balance) {
        this.balanceObserver = balance;
    }

    @Override
    public void onNext(DepositRequest depositRequest) {

        this.accountBalance = AccountDatabase.addBalance(depositRequest.getAccountNumber(), depositRequest.getAmount());


    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        Balance balance = Balance.newBuilder().setAmount(this.accountBalance).build();
        this.balanceObserver.onNext(balance);
        this.balanceObserver.onCompleted();
    }
}
