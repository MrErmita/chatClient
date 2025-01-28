package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadConnessione implements Runnable {
    private Socket client;
    private BufferedReader in;
    private ListaClient listaClient;
    private String nomeClient;

    public ThreadConnessione(Socket client, ListaClient listaClient, String nomeClient) throws IOException {
        this.client = client;
        this.listaClient = listaClient;
        this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        this.nomeClient = nomeClient;
    }

    @Override
    public void run() {
        String messaggio;
        boolean primo = true;

        try {
            while (!Thread.interrupted()) {
                messaggio = in.readLine();
                if(primo) {
                    nomeClient = messaggio;
                    System.out.println("Client " + nomeClient + " connesso");
                    primo = false;
                } else {
                    listaClient.sendAll(nomeClient + ": " + messaggio, client);
                }
            }
        } catch (IOException e) {
            System.out.println("Connessione interrotta con" + nomeClient);
        }
    }
}
