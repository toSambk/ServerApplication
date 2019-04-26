package org.levelup.server;
import java.io.*;
import java.net.Socket;

public class ClientWorker implements Runnable {
    private Socket client;

    public ClientWorker(Socket client) {
        this.client = client;
    }

    private ClientWorker(){};

    @Override
    public void run() {
        String eline = "";
        try (BufferedReader fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
             BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {
            while (!Server.getExit()) {
                eline = fromClient.readLine();
                System.out.println(eline);
                toClient.write(" Got a message - " + eline + "\n");
                toClient.flush();
                if (eline.equals("exit")) {
                    Server.setExit(true);
                }
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }


}

