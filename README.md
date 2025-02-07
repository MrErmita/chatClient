# 🗨️ ChatClient

![GitHub repo size](https://img.shields.io/github/repo-size/MrErmita/chatClient?style=for-the-badge)
![GitHub last commit](https://img.shields.io/github/last-commit/MrErmita/chatClient?style=for-the-badge)
![GitHub issues](https://img.shields.io/github/issues/MrErmita/chatClient?style=for-the-badge)

Un semplice client di chat basato su **Java**, utilizzando **socket TCP** e **thread** per la comunicazione in tempo reale tra più utenti. Perfetto per esplorare il networking in Java! 🚀

---

## ✨ Funzionalità
✅ Connessione a un server di chat
✅ Invio e ricezione di messaggi in tempo reale
✅ Supporto per più utenti
✅ Implementazione basata su **Java Threads** per la gestione delle connessioni

---

## ⚙️ Installazione

1. **Clona il repository**
```bash
 git clone https://github.com/MrErmita/chatClient.git
 cd chatClient
```
2. **Compila ed esegui il server**
```bash
javac -d bin src/com/example/MainServer.java
java -cp bin com.example.MainServer
```
3. **Compila ed esegui il client**
```bash
javac -d bin src/com/example/MainClient.java
java -cp bin com.example.MainClient
```

---

## 📡 Come Funziona
Il server utilizza un **ServerSocket** per accettare connessioni dai client. Ogni client viene gestito tramite un **Thread separato**, garantendo un'efficace gestione delle comunicazioni in parallelo.

Esempio di codice per il server:
```java
ServerSocket serverSocket = new ServerSocket(5500);
while (true) {
    Socket clientSocket = serverSocket.accept();
    new Thread(new ClientHandler(clientSocket)).start();
}
```

---

## 📷 Screenshot
![Demo](images/image.png)

---

## 🛠️ Tecnologie Utilizzate
- Java ☕
- Socket TCP/IP 📡
- Multi-threading 🧵

---

## 🚀 Contribuire
Se vuoi migliorare il progetto:
1. Fai un **fork** del repo 🍴
2. Crea un **branch** con la tua feature `git checkout -b nuova-feature`
3. **Commit** le modifiche `git commit -m 'Aggiunta nuova feature'`
4. Fai un **push** `git push origin nuova-feature`
5. Apri una **Pull Request** 🚀

---

## 📜 Licenza
Questo progetto è distribuito sotto licenza MIT. 📄

---

## 📬 Contatti
📧 Email: [alessio.bragetti@gmail.com](mailto:alessio.bragetti@gmail.com)
🐙 GitHub: [MrErmita](https://github.com/MrErmita)

💡 **Star il repository se ti è piaciuto! ⭐**
