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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author filip
 */
public class ServerThread extends Thread {

    /* CLIENT_PORT=1989
    GROUP=230.0.0.1
    NAME=PLAYER1*/
    public static final String PROPS_FILE = "server.properties";
    public static final String CLIENT_PORT = "CLIENT_PORT";
    public static final String GROUP = "GROUP";
    public static final String NAME = "NAME";
    private static Igra trenutnaIgra = new Igra("", "", "", "", 0, false, "", "", "");

    //public static final Properties props = JndiUtils.readConfigFile("c:\\");
    public static final Properties PROPERTIES = new Properties();

    static {
        try {
            PROPERTIES.load(new FileInputStream(PROPS_FILE));
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {

        try (ServerSocket serverSocket = new ServerSocket(Integer.valueOf(PROPERTIES.getProperty("CLIENT_PORT")))) {

            System.err.println("Server listening on port: " + serverSocket.getLocalPort());
            System.out.println("Prvi klijent: " + PROPERTIES.getProperty("CLIENT1") + ", Drugi klijent: " + PROPERTIES.getProperty("CLIENT2"));

            while (true) {

                Socket clientSocket = serverSocket.accept();
                System.err.println("Client connected from: " + clientSocket.getPort());

                new Thread(() -> procesExternalizableClient(clientSocket)).start();

            }

        } catch (Exception ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void procesExternalizableClient(Socket clientSocket) {
        try (ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {

            Igra igra = (Igra) ois.readObject();
            if (igra.getTypeOfRequest() != null) {

                if ("setNadimakPrvogIgraca".equalsIgnoreCase(igra.getTypeOfRequest())) {
                    trenutnaIgra.setNadimakPrvogIgraca(igra.getNadimakPrvogIgraca());
                    System.out.println("Promjena nadimka1");
                } else if ("setNadimakDrugogIgraca".equalsIgnoreCase(igra.getTypeOfRequest())) {
                    trenutnaIgra.setNadimakDrugogIgraca(igra.getNadimakDrugogIgraca());
                    System.out.println("Promjena nadimka2");
                } else if ("setOdabranaIstina".equalsIgnoreCase(igra.getTypeOfRequest())) {
                    trenutnaIgra.setOdabranaIstina(igra.isOdabranaIstina());
                    System.out.println("Promjena odabranaIstina");
                } else if ("setTekstOdgovora".equalsIgnoreCase(igra.getTypeOfRequest())) {
                    trenutnaIgra.setTekstOdgovora(igra.getTekstOdgovora());
                    System.out.println("Promjena odgovora");
                } else if ("setTekstPitanja".equalsIgnoreCase(igra.getTypeOfRequest())) {
                    trenutnaIgra.setTekstPitanja(igra.getTekstPitanja());
                    System.out.println("Promjena pitanja");
                }

            }

            oos.writeObject(trenutnaIgra);

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}


            //NE RADI
            /* Igra igra = (Igra) ois.readObject();
            switch (igra.getTypeOfRequest()) {
            case "setNadimakPrvogIgraca":
            trenutnaIgra.setNadimakPrvogIgraca(igra.getNadimakPrvogIgraca());
            trenutnaIgra.setBrojRunde(999);
            break;
            case "setNadimakDrugogIgraca":
            trenutnaIgra.setNadimakDrugogIgraca(igra.getNadimakDrugogIgraca());
            trenutnaIgra.setBrojRunde(888);
            break;
            }
            oos.writeObject(trenutnaIgra);*/
            //RADIs
            /*trenutnaIgra = (Igra) ois.readObject();
            trenutnaIgra.setBrojRunde(777);
            oos.writeObject(trenutnaIgra);*/