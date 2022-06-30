package com.yubraj.microservice.grpc.response;

import com.yubraj.microservice.models.grpc.Money;
import io.grpc.stub.StreamObserver;

public class MoneyStreamingResponse implements StreamObserver<Money> {

    @Override
    public void onNext(Money money) {
        System.out.println("Money Received : " + money.getValue());
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Failed : " + throwable.getMessage());
    }

    @Override
    public void onCompleted() {
        System.out.println("Done server!!");
    }
}
