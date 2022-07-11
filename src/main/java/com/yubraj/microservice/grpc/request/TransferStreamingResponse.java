package com.yubraj.microservice.grpc.request;

import com.yubraj.microservice.models.grpc.TransferResponse;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

public class TransferStreamingResponse implements StreamObserver<TransferResponse> {


    private CountDownLatch latch;

    public TransferStreamingResponse(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onNext(TransferResponse transferResponse) {
        System.out.println("Status : " + transferResponse.getStatus());
        transferResponse.getAccountList()
                .stream()
                .map(x -> x.getAccountNumber() + " : " + x.getAmount())
                .forEach(System.out::println);

        System.out.println("-----------------------------------");
    }

    @Override
    public void onError(Throwable throwable) {
        this.latch.countDown();
    }

    @Override
    public void onCompleted() {
        System.out.println("All transfer done!");
        this.latch.countDown();
    }
}
