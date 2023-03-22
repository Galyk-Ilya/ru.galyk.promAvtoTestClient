package org.example.client.service;

import com.example.grpc.GreetingServiceGrpc;
import com.example.grpc.GreetingServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.Iterator;

@Slf4j
public class VideoService {

    private static final JFrame window = new JFrame();
    private static final JLabel screen = new JLabel();

    private final String address;

    private final int compress;

    public VideoService(String address, int compress) {
        this.address = address;
        this.compress = compress;
    }

    public void start() {
        ManagedChannel managedChannel = ManagedChannelBuilder.forTarget(address)
                .usePlaintext().build();
        GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(managedChannel);
        GreetingServiceOuterClass.MsgRequest request = GreetingServiceOuterClass.MsgRequest.newBuilder()
                .setCompressLvl(compress)
                .build();
        Iterator<GreetingServiceOuterClass.MsgResponse> response = stub.greeting(request);
        log.info("Server connection established");
        playing(response);
        log.info("Server connection shutdown");
        managedChannel.shutdownNow();
    }

    private void playing(Iterator<GreetingServiceOuterClass.MsgResponse> response) {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        while (response.hasNext()) {
            byte[] bytes = response.next().getGreeting().toByteArray();
            ImageIcon ic = new ImageIcon(bytes);
            screen.setIcon(ic);
            window.setContentPane(screen);
            window.pack();
        }
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }
}