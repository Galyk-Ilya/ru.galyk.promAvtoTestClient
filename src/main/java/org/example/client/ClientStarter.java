package org.example.client;

import org.example.client.service.VideoService;

class ClientStarter {

    public static void main(String[] args) {
        VideoService service = new VideoService("localhost:8080", 10);
        service.start();
    }
}