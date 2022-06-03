package com.anstel.MCA_LOGIC_Server_HD;

import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
//import com.anstel.client.MCA_Logic;

/**
 * Classe Java servant à recevoir les demandes de numérotation automatique à
 * destination d'InIn émises depuis iET/Eole2/Zephi2 au travers d'une socket.
 *
 * Reference : http://www.cs.uic.edu/~troy/spring05/cs450/sockets/socket.html
 *
 * @author Thierry Baribaud
 * @version 3.0.0
 */
public class MCA_Logic_Server_HD extends Thread {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final int PORT = 4000;
    protected Socket clientSocket;
    private String noTelephone = null;
    private String login = null;
    private String clientRef = null;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        MCA_Logic_Server_HD mca_Logic_Server_HD = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println(SDF.format(new Date()) + " Connection Socket Created");
            try {
                while (true) {
                    System.out.println(SDF.format(new Date()) + " Waiting for Connection");
                    mca_Logic_Server_HD = new MCA_Logic_Server_HD(serverSocket.accept());
                }
            } catch (IOException e) {
                System.err.println(SDF.format(new Date()) + " Accept failed.");
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println(SDF.format(new Date()) + " Could not listen on port: PORT.");
            System.exit(1);
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.err.println(SDF.format(new Date()) + " Could not close port: PORT.");
                System.exit(1);
            }
        }
    }

    private MCA_Logic_Server_HD(Socket clientSoc) {
        clientSocket = clientSoc;
        start();
    }

    @Override
    public void run() {
        StringTokenizer ST;
        int n;
        MCA_Logic mca_Logic;

        System.out.println("----------------------------------------------------------------");
        System.out.println(SDF.format(new Date()) + " New Communication Thread Started");

        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println(SDF.format(new Date()) + " from:" + clientSocket.getInetAddress() + ", data received: " + inputLine);
                ST = new StringTokenizer(inputLine, ";");
                n = 0;
                if (ST.hasMoreElements()) {
                    n++;
                    noTelephone = ST.nextToken();
                }
                if (ST.hasMoreElements()) {
                    n++;
                    login = ST.nextToken();
                }
                if (ST.hasMoreElements()) {
                    n++;
                    clientRef = ST.nextToken();
                }

                if (n == 3) {
                    System.out.println(SDF.format(new Date()) + " Login=" + login
                            + ", NoTelephone=" + noTelephone
                            + ", ClientRef=" + clientRef);
                    mca_Logic = new MCA_Logic(noTelephone, login, clientRef);
                    mca_Logic.Run();
                    mca_Logic = null;
                } else {
                    System.out.println(SDF.format(new Date()) + " Bad request");
                }
            }
            in.close();
            clientSocket.close();
            System.out.println(SDF.format(new Date()) + " New Communication Thread Terminated");
        } catch (IOException e) {
            System.err.println(SDF.format(new Date()) + " Problem with Communication Server");
            System.exit(1);
        }
    }
}
