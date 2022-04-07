/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.messangerserver;

import hr.algebra.model.MessengerService;
import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessangerServiceServer {

    public static final String PROPS_FILE = "server.properties";
    public static final String PORT = "PORT";
    public static final Properties PROPERTIES = new Properties();

    static {
        try {
            PROPERTIES.load(new FileInputStream(PROPS_FILE));
        } catch (IOException ex) {
            Logger.getLogger(MessangerServiceServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {

        System.err.println("Server started on port " + PROPERTIES.getProperty(PORT));

        MessengerService messengerService = new MessengerServiceImpl();
        try {
            MessengerService stub = (MessengerService) UnicastRemoteObject.exportObject((MessengerService) messengerService, 0);
            Registry registry = LocateRegistry.createRegistry(Integer.valueOf(PROPERTIES.getProperty(PORT)));
            registry.rebind("MessengerService", stub);

        } catch (RemoteException ex) {
            Logger.getLogger(MessangerServiceServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
