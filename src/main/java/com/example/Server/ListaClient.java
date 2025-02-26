package com.example.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ListaClient {
    private ArrayList<Socket> listaSockets; // Lista dei client connessi

    // Costruttore per inizializzare la lista dei client
    public ListaClient() {
        this.listaSockets = new ArrayList<Socket>();
    }

    // Aggiunge un client alla lista in modo sincronizzato per gestire accessi concorrenti
    public synchronized void addClient(Socket c) throws IOException {
        this.listaSockets.add(c);
    }

    // Rimuove un client dalla lista, chiudendo prima il suo socket
    public synchronized void removeClient(int i) throws IOException {
        listaSockets.get(i).close(); // Chiude la connessione
        listaSockets.remove(i); // Rimuove il client dalla lista
    }

    // Invia un messaggio a tutti i client connessi, tranne il mittente
    public synchronized void sendAll(String message, Socket client) throws IOException {
        for (Socket socket : listaSockets) {
            if (socket != client) { // Evita di rinviare il messaggio al mittente
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println(message);
                out.flush(); // Assicura che il messaggio venga inviato subito
            }
        }
    }
}
