package org.levelup.server;
import org.level.up.json.JsonService;
import org.level.up.json.impl.JsonServiceImpl;
import org.level.up.json.test.Cat;

import java.io.*;
import java.net.Socket;

public class ClientWorker implements Runnable {
    private Socket client;
    private static String eline;

    public ClientWorker(Socket client) {
        this.client = client;
    }

    private ClientWorker(){};

    @Override
    public void run() {
        JsonService service = new JsonServiceImpl();
        eline = "";
        try (BufferedReader fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
             BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {
            while (!Server.getExit()) {
                eline = fromClient.readLine();
                System.out.println(eline);
                Cat fromJson = service.fromJson(eline, Cat.class);
                //System.out.println(fromJson.getName() + "    " + fromJson.getAge());
                Server.addCats(fromJson);
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

