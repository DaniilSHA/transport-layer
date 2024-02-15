package org.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.grpc.CalcServiceGrpc;
import org.example.grpc.CalcServiceOuterClass;

import java.io.IOException;

public class GrpcClient {
    public static void main(String[] args) throws IOException, InterruptedException {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8079).usePlaintext().build();
        CalcServiceGrpc.CalcServiceBlockingStub stub = CalcServiceGrpc.newBlockingStub(channel);
        CalcServiceOuterClass.CalcResponse response = stub.calc(CalcServiceOuterClass.CalcRequest.newBuilder()
                .setA(2.3)
                .setB(4.6)
                .setC(1.1)
                .build());
        System.out.println("get from server: " + response.getRes());
    }
}
