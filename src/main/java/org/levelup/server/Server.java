package org.levelup.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private Server() {
    }

    private static boolean exitFlag;
    private static ServerSocket server;

    public static void startServer(int port) {
        try {
            ServerSocket server = new ServerSocket(port); //слушает порт - принимает запросы
            setServer(server);
            Thread myThread = new Thread (new LisThread());
            myThread.setDaemon(true);
            myThread.start();
            while (!getExit()) {
                Thread.sleep(200);
            }
            System.out.println("Logging out!!!");
        } catch (IOException exc) {
            exc.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void setExit(boolean exitFlagLocal) {
        exitFlag = exitFlagLocal;
    }

    public static boolean getExit() {
        return exitFlag;
    }

    public static void  setServer (ServerSocket serverloc) {
        server = serverloc;
    }

    public static class LisThread implements Runnable {

        @Override
        public void run() {
            try {
                while(true) {
                    Socket client = server.accept();//блокирующий метод
                    Thread clientThread = new Thread(new ClientWorker(client));
                    clientThread.setDaemon(true);
                    clientThread.start();
                }
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }

}


