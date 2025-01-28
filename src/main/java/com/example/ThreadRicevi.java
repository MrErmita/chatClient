package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadRicevi implements Runnable{
    private Socket socket;
    BufferedReader in;

    public ThreadRicevi(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        String messaggio;

        try {
            messaggio = in.readLine();

            while(messaggio != null) {
                System.out.println(messaggio);
                messaggio = in.readLine();
            }
            System.out.println("Serveri Chiuso");
            socket.close();
        } catch (IOException e) {
            System.out.println("Errore di connessione");
        }

    }
    
}
