package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8079).addService(new CalcService()).build();
        server.start();
        System.out.println("server start");
        server.awaitTermination();
    }
}
