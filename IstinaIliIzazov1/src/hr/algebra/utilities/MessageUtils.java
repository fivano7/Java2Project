/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utilities;

import hr.algebra.controller.ChatController;
import hr.algebra.model.ChatMessage;
import hr.algebra.model.MessengerService;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author filip
 */
public class MessageUtils {
    
    public static void sendMessage(String user, String message) {
        Registry registry;
        
        try {
            registry = LocateRegistry.getRegistry();
            MessengerService messangerService = (MessengerService) registry.lookup("MessengerService");
            ChatMessage newMessage = new ChatMessage(user, message, LocalDateTime.now());
            messangerService.sendMessage(newMessage);

        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static MessengerService getMessangerService() {
        Registry registry;
        
        try {
            registry = LocateRegistry.getRegistry();
            MessengerService messangerService = (MessengerService) registry.lookup("MessengerService");
            return messangerService;

        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
}
