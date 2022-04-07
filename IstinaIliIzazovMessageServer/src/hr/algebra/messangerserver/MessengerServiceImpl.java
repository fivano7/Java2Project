/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.messangerserver;

import hr.algebra.model.ChatMessage;
import hr.algebra.model.MessengerService;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


public class MessengerServiceImpl implements MessengerService {

    List<ChatMessage> chatMessagesList = new ArrayList<>();

    @Override
    public void sendMessage(ChatMessage chatMessage) throws RemoteException {
        chatMessagesList.add(chatMessage);
    }

    @Override
    public List<ChatMessage> getChatHistory() throws RemoteException {
        return chatMessagesList;
    }

    @Override
    public ChatMessage getLastChatMessage() throws RemoteException {
        
        if (!chatMessagesList.isEmpty()) {
            return chatMessagesList.get(chatMessagesList.size() - 1);
        }
        return null;
    }

}
