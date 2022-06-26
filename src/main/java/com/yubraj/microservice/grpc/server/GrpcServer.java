package com.yubraj.microservice.grpc.server;

import com.yubraj.microservice.grpc.service.BankService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(6565)
                .addService(new BankService())
                .build();

        server.start();
        server.awaitTermination();
    }
}
