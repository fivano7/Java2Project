/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.controller;

import hr.algebra.ClientThread;
import hr.algebra.DAL.Repository;
import hr.algebra.IstinaIliIzazovApplication;
import hr.algebra.model.Igra;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author filip
 */

//PLAYER1
public class EkranCekanjaController implements Initializable {

    private Igra trenutnaIgra;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void loadPage(MouseEvent event) {

        try {

            Thread.sleep(500);

            updateTrenutnaIgra();

            Parent root = null;
            Stage mainStage = null;

            if (trenutnaIgra.isOdabranaIstina()) {

                root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/UnosOdgovora.fxml"));
                mainStage = IstinaIliIzazovApplication.getMainStage();

            } else {
                root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/UnosIzvrsenosti.fxml"));
                mainStage = IstinaIliIzazovApplication.getMainStage();
            }

            mainStage.setScene(new Scene(root));
            mainStage.show();

        } catch (IOException ex) {
            Logger.getLogger(EkranCekanjaController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(EkranCekanjaController.class.getName()).log(Level.SEVERE, null, ex);
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