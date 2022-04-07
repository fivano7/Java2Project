/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controller;

import hr.algebra.ClientThread;
import static hr.algebra.ClientThread.NAME;
import hr.algebra.DAL.Repository;
import hr.algebra.IstinaIliIzazovApplication;
import hr.algebra.model.Igra;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author filip
 */
//Klijent1
public class PocetnaController implements Initializable {

    @FXML
    private TextField tfNickname;
    private Igra trenutnaIgra;

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

    //Klijent1
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        tfNickname.setText(PROPERTIES.getProperty(NAME));

        Stage mainStage = IstinaIliIzazovApplication.getMainStage();
        mainStage.setTitle("Početna");
        
        updateTrenutnaIgra(); //trenutnaIgra spremna za korištenje
        
        //////PIŠE I ČITA
        trenutnaIgra.setNadimakPrvogIgraca(PROPERTIES.getProperty(NAME));
        trenutnaIgra.setTypeOfRequest("setNadimakPrvogIgraca");
        executeChange("PocetnaController 1");
        
        //////
        String[] simpleName = this.getClass().getSimpleName().split("Controller");
        trenutnaIgra.setEkranPrvogIgraca(simpleName[0]);
    }

    @FXML
    private void pokreniIgru() throws IOException {

        String nickName = tfNickname.getText().trim();

        Repository.getTrenutnaIgra().setNadimakPrvogIgraca(nickName);

        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/OdabirKategorije.fxml"));

        Stage mainStage = IstinaIliIzazovApplication.getMainStage();
        mainStage.setScene(new Scene(root));

        mainStage.show();

    }

    @FXML
    private void pokreniIgruEntrom(KeyEvent event) throws IOException {

        if (event.getCode().equals(KeyCode.ENTER)) {
            pokreniIgru();
        }
    }

   private void updateTrenutnaIgra() {
        trenutnaIgra = new Igra("", "", "", "", 0, false, "", "", "");
        new ClientThread(trenutnaIgra).start();

        try {
            Thread.sleep(250);
        } catch (InterruptedException ex) {
            Logger.getLogger(PocetnaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        trenutnaIgra = ClientThread.getClientIgra();
        System.out.println("PocetnaController 1 - " + trenutnaIgra);
    }
    
     private void executeChange(String imeControllera) {
        new ClientThread(trenutnaIgra).start();
        
        try {
            Thread.sleep(250);
        } catch (InterruptedException ex) {
            Logger.getLogger(PocetnaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        trenutnaIgra = ClientThread.getClientIgra();
        Repository.setTrenutnaIgra(trenutnaIgra);
        System.out.println(imeControllera + " - " + ClientThread.getClientIgra());
    }

}
