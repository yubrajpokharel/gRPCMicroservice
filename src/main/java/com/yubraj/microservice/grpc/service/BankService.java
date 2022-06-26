package com.yubraj.microservice.grpc.service;

import com.yubraj.microservice.grpc.database.AccountDatabase;
import com.yubraj.microservice.models.grpc.Balance;
import com.yubraj.microservice.models.grpc.BankCheckRequest;
import com.yubraj.microservice.models.grpc.BankServiceGrpc;
import com.yubraj.microservice.models.grpc.BankServiceGrpc.BankServiceImplBase;
import io.grpc.stub.StreamObserver;

public class BankService extends BankServiceImplBase
{
    @Override
    public void getBalance(BankCheckRequest request, StreamObserver<Balance> responseObserver) {
        int accountNumber = request.getAccountNumber();
        Balance balance = Balance.newBuilder().setAmount(AccountDatabase.getBalance(accountNumber)).build();
        responseObserver.onNext(balance);
        responseObserver.onCompleted();
    }
}
