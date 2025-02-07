package com.example.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class ThreadRicevi implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private JTextArea chatArea;

    public ThreadRicevi(Socket socket, JTextArea chatArea) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.chatArea = chatArea;
    }

    @Override
    public void run() {
        String messaggio;

        try {
            while ((messaggio = in.readLine()) != null) {
                String finalMessaggio = messaggio;
                SwingUtilities.invokeLater(() -> chatArea.append(finalMessaggio + "\n"));
            }
            SwingUtilities.invokeLater(() -> chatArea.append("Server Chiuso\n"));
            socket.close();
        } catch (IOException e) {
            SwingUtilities.invokeLater(() -> chatArea.append("Errore di connessione\n"));
        }
    }
}
