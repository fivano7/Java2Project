/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controller;

import hr.algebra.model.MessengerService;
import hr.algebra.utilities.MessageUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class ChatController implements Initializable {

    @FXML
    private TextField tfMessage;
    @FXML
    private TextArea taChat;
    MessengerService messangerService;
    private int initialLength = 0;

    public static final String PROPS_FILE = "player.properties";
    public static final String NAME = "NAME";
    public static final Properties PROPERTIES = new Properties();

    static {
        try {
            PROPERTIES.load(new FileInputStream(PROPS_FILE));
        } catch (IOException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        RefreshChat();

    }

    public void RefreshChat() {
        try {
            messangerService = MessageUtils.getMessangerService();

            WriteUpdate();

            initialLength = messangerService.getChatHistory().size();
            new Timer().scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    try {
                        if (messangerService.getChatHistory().size() != initialLength) {
                            initialLength = messangerService.getChatHistory().size();
                            WriteUpdate();
                        }
                    } catch (RemoteException ex) {
                        Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }, 0, 250);

        } catch (RemoteException ex) {
            Logger.getLogger(ChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void WriteUpdate() throws RemoteException {
        StringBuilder sb = new StringBuilder();

        messangerService.getChatHistory().forEach(m -> {
            if (PROPERTIES.getProperty(NAME).equals(m.getUsername())) {
                m.setUsername("Ja");
            } else {
                m.setUsername("" + m.getUsername());
            }

            sb.append(m.toString()).append("\n");
            taChat.setText(sb.toString());

        });
    }

    @FXML
    private void sendMessage(ActionEvent event) {

         if (tfMessage.getText().length() != 0) {
            MessageUtils.sendMessage(PROPERTIES.getProperty(NAME), tfMessage.getText().trim());

            tfMessage.setText("");
        }
    }
}
