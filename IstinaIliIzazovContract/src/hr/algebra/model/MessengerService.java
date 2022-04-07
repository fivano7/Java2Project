/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author lecturer14
 */
public interface MessengerService extends Remote {
    void sendMessage(ChatMessage chatMessage) throws RemoteException;
    
    List<ChatMessage> getChatHistory() throws RemoteException;
    
    ChatMessage getLastChatMessage() throws RemoteException;
}