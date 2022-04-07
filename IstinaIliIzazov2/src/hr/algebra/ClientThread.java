/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra;

import hr.algebra.model.Igra;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author filip
 */
//KLIJENT2
public class ClientThread extends Thread {

    /* CLIENT_PORT=1989
    GROUP=230.0.0.1
    NAME=PLAYER1*/
    public static final String PROPS_FILE = "player.properties";
    public static final String CLIENT_PORT = "CLIENT_PORT";
    public static final String GROUP = "GROUP";
    public static final String NAME = "NAME";
    public static final Properties PROPERTIES = new Properties();
    //Properties readConfigFile = JndiUtils.readConfigFile(PROPS_FILE);
    public static Igra clientIgra;

    static {
        try {
            PROPERTIES.load(new FileInputStream(PROPS_FILE));
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Igra getClientIgra() {
        return clientIgra;
    }

    public ClientThread(Igra clientIgra) {
        ClientThread.clientIgra = clientIgra;
    }

    @Override
    public void run() {

        try (Socket clientSocket = new Socket(PROPERTIES.getProperty(GROUP), Integer.parseInt(PROPERTIES.getProperty(CLIENT_PORT)))) {

            System.err.println("Client connects to: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

            sendExternalizableRequest(clientSocket);

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendExternalizableRequest(Socket clientSocket) throws IOException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());

        oos.writeObject(clientIgra);
        clientIgra = (Igra) ois.readObject();

        System.out.println("CLIENT2: " + clientIgra);
    }

}
