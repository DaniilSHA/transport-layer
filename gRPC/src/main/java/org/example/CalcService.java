package org.example;

import io.grpc.stub.StreamObserver;
import org.example.grpc.CalcServiceGrpc;
import org.example.grpc.CalcServiceOuterClass;

public class CalcService extends CalcServiceGrpc.CalcServiceImplBase {

    @Override
    public void calc(CalcServiceOuterClass.CalcRequest request, StreamObserver<CalcServiceOuterClass.CalcResponse> responseObserver) {
        double a = request.getA();
        double b = request.getB();
        double c = request.getC();

        double res = a + b + c;

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        responseObserver.onNext(CalcServiceOuterClass.CalcResponse.newBuilder().setRes(res).build());
        responseObserver.onCompleted();
    }
}
