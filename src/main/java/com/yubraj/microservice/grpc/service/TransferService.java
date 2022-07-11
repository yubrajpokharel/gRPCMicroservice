package com.yubraj.microservice.grpc.service;

import com.yubraj.microservice.grpc.request.TransferStreamingRequest;
import com.yubraj.microservice.models.grpc.TransferRequest;
import com.yubraj.microservice.models.grpc.TransferResponse;
import com.yubraj.microservice.models.grpc.TransferServiceGrpc.TransferServiceImplBase;
import io.grpc.stub.StreamObserver;

public class TransferService extends TransferServiceImplBase {

    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
        return new TransferStreamingRequest(responseObserver);
    }
}
