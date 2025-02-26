package com.example.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainServer {

    public static void main(String[] args) {
        final int PORT = 5500; // Porta su cui il server ascolta le connessioni
        ServerSocket serverSocket = null; // Dichiarato fuori dal try per essere accessibile nel finally

        try {
            // Creazione del server socket per accettare connessioni in ingresso
            serverSocket = new ServerSocket(PORT);
            ArrayList<Thread> listaThreadConnessioni = new ArrayList<>(); // Lista dei thread attivi per ogni client
            ListaClient listaClient = new ListaClient(); // Lista dei client connessi
            System.out.println("Server Aperto");
            System.out.println("In attesa di connessioni...");

            while (true) {
                // Accetta una nuova connessione da un client
                Socket nuovoClient = serverSocket.accept();
                listaClient.addClient(nuovoClient); // Aggiunge il client alla lista

                // Crea e avvia un nuovo thread per gestire la comunicazione con il client
                Thread connessioneThread = new Thread(new ThreadConnessione(nuovoClient, listaClient, null));
                listaThreadConnessioni.add(connessioneThread);
                listaThreadConnessioni.get(listaThreadConnessioni.size() - 1).start();
            }

        } catch (IOException e) {
            System.out.println("Errore di connessione: " + e.getMessage());
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                    System.out.println("Server chiuso correttamente.");
                } catch (IOException e) {
                    System.out.println("Errore nella chiusura del server: " + e.getMessage());
                }
            }
        }
    }
}
