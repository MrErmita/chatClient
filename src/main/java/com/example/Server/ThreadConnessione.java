package com.example.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadConnessione implements Runnable {
    private Socket client; // Socket per la comunicazione con il client
    private BufferedReader in; // Stream di input per ricevere messaggi dal client
    private ListaClient listaClient; // Riferimento alla lista dei client connessi
    private String nomeClient; // Nome identificativo del client

    // Costruttore che inizializza il socket, la lista dei client e lo stream di input
    public ThreadConnessione(Socket client, ListaClient listaClient, String nomeClient) throws IOException {
        this.client = client;
        this.listaClient = listaClient;
        this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        this.nomeClient = nomeClient;
    }

    @Override
    public void run() {
        String messaggio;
        boolean primo = true; // Indica se è il primo messaggio ricevuto dal client

        try {
            while (!Thread.interrupted()) { // Continua a leggere finché il thread non viene interrotto
                messaggio = in.readLine(); // Legge un messaggio inviato dal client
                if (primo) {
                    nomeClient = messaggio; // Il primo messaggio ricevuto viene usato come nome del client
                    System.out.println("Client " + nomeClient + " connesso");
                    primo = false;
                } else {
                    // Invia il messaggio ricevuto a tutti gli altri client connessi
                    listaClient.sendAll(nomeClient + ": " + messaggio, client);
                }
            }
        } catch (IOException e) {
            System.out.println("Connessione interrotta con " + nomeClient); // Gestisce la disconnessione del client
        }
    }
}
