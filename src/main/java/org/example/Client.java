package org.example;

import com.example.grpc.GreetingServiceGrpc;
import com.example.grpc.GreetingServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

public class Client {
    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("localhost:8080")
                .usePlaintext().build();
        GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(managedChannel);
        GreetingServiceOuterClass.MsgRequest request = GreetingServiceOuterClass.MsgRequest.newBuilder()
                .build();
        Iterator<GreetingServiceOuterClass.MsgResponse> response = stub.greeting(request);
        while (response.hasNext())
            System.out.println(response.next());
        managedChannel.shutdownNow();
    }
}
