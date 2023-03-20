package org.example;

import com.example.grpc.GreetingServiceGrpc;
import com.example.grpc.GreetingServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import javax.swing.*;
import java.util.Arrays;
import java.util.Iterator;

class TestClient {
    static JFrame window = new JFrame();//окно
    static JLabel screen = new JLabel();//контейнер

    static ImageIcon ic; // изображение в формате java

    public static void main(String[] args) {
        ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("localhost:8080")
                .usePlaintext().build();
        GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(managedChannel);
        GreetingServiceOuterClass.MsgRequest request = GreetingServiceOuterClass.MsgRequest.newBuilder()
                .build();
        Iterator<GreetingServiceOuterClass.MsgResponse> response = stub.greeting(request);
        playing(response);
        managedChannel.shutdownNow();
    }
    public static void playing(Iterator<GreetingServiceOuterClass.MsgResponse> response){

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//закрыть окно
        window.setVisible(true);//видимость окна
        int i = 1;
        while (response.hasNext()) {
            i++;
           // System.out.println(Arrays.toString(response.next().toByteArray()));
            System.out.println(i);
            ic = new ImageIcon(response.next().toByteArray());
            screen.setIcon(ic);
            window.setContentPane(screen);  //изображение в контейнер -> окно
            window.pack();
        }
    }
}
