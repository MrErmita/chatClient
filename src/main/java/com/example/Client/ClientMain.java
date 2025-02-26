package com.example.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClientMain {
    private static JTextArea chatArea;
    private static JTextField inputField;
    private static PrintWriter out;
    private static String username;
    
    public static void main(String[] args) {
        Socket clientSocket;
        
        // Creazione dell'interfaccia grafica del client
        JFrame frame = new JFrame("Chat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());
        
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        JButton sendButton = new JButton("Invia");
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        frame.add(inputPanel, BorderLayout.SOUTH);
        
        frame.setVisible(true);
        
        try {
            // Connessione al server sulla porta 5500
            clientSocket = new Socket("localhost", 5500);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            
            // Richiesta del nome utente tramite un popup
            username = JOptionPane.showInputDialog(frame, "Inserisci il tuo nome utente:");
            frame.setTitle(username);
            if (username != null && !username.trim().isEmpty()) {
                out.println(username); // Invia il nome utente al server
                chatArea.append("Nome utente impostato: " + username + "\n");
            }
            
            // Avvio del thread per la ricezione dei messaggi
            Thread riceviThread = new Thread(new ThreadRicevi(clientSocket, chatArea));
            riceviThread.start();
            
            // Definizione dell'azione per inviare messaggi
            ActionListener sendAction = e -> {
                String message = inputField.getText().trim();
                if (!message.isEmpty()) {
                    out.println(message); // Invia il messaggio al server
                    chatArea.append("Tu: " + message + "\n");
                    inputField.setText("");
                }
            };
            
            // Collegamento del listener ai componenti UI
            sendButton.addActionListener(sendAction);
            inputField.addActionListener(sendAction);
            
        } catch (IOException e) {
            // Gestione errore in caso di problemi di connessione
            chatArea.append("Errore di connessione al server\n");
        }
    }
}