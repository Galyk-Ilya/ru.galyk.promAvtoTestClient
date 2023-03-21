package org.example;

import com.example.grpc.GreetingServiceGrpc;
import com.example.grpc.GreetingServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.Iterator;

class Client {
    static JFrame window = new JFrame();

    static JLabel screen = new JLabel();

    static ImageIcon ic;

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

    public static void playing(Iterator<GreetingServiceOuterClass.MsgResponse> response) {

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        while (response.hasNext()) {
            byte[] bytes = response.next().getGreeting().toByteArray();
            ic = new ImageIcon(bytes);
            screen.setIcon(ic);
            window.setContentPane(screen);
            window.pack();
        }
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }
}