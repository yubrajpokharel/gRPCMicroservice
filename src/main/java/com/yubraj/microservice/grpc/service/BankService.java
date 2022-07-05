package com.yubraj.microservice.grpc.service;

import com.yubraj.microservice.grpc.database.AccountDatabase;
import com.yubraj.microservice.grpc.request.CashStreamingRequest;
import com.yubraj.microservice.models.grpc.*;
import com.yubraj.microservice.models.grpc.BankServiceGrpc.BankServiceImplBase;
import io.grpc.Status;
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

    @Override
    public void withDraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
        int accNumber = request.getAccountNumber();
        int amount = request.getAmount();
        int balance = AccountDatabase.getBalance(accNumber);

        if(balance < amount) {
            Status status = Status.FAILED_PRECONDITION.withDescription("Not enough money in acc, remaining balance is " + balance);
            responseObserver.onError(status.asException());
            return;
        }

        for (int i = 0; i < amount/10; i++) {
            Money money = Money.newBuilder().setValue(10).build();
            responseObserver.onNext(money);
            AccountDatabase.deductBalance(accNumber, 10);
        }

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<DepositRequest> cashDeposit(StreamObserver<Balance> responseObserver) {
        return new CashStreamingRequest(responseObserver);
    }
}
