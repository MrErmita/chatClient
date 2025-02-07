package com.example.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientMain {
    private static JTextArea chatArea;
    private static JTextField inputField;
    private static PrintWriter out;
    private static String username;
    
    public static void main(String[] args) {
        Socket clientSocket;
        
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
            clientSocket = new Socket("localhost", 5500);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            
            username = JOptionPane.showInputDialog(frame, "Inserisci il tuo nome utente:");
            if (username != null && !username.trim().isEmpty()) {
                out.println(username);
                chatArea.append("Nome utente impostato: " + username + "\n");
            }
            
            Thread riceviThread = new Thread(new ThreadRicevi(clientSocket));
            riceviThread.start();
            
            ActionListener sendAction = e -> {
                String message = inputField.getText().trim();
                if (!message.isEmpty()) {
                    out.println(username + ": " + message);
                    chatArea.append("Tu: " + message + "\n");
                    inputField.setText("");
                }
            };
            
            sendButton.addActionListener(sendAction);
            inputField.addActionListener(sendAction);
            
        } catch (IOException e) {
            chatArea.append("Errore di connessione al server\n");
        }
    }
}
