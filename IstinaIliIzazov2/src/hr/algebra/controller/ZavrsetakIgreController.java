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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class ZavrsetakIgreController implements Initializable {
    
    private Igra trenutnaIgra;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Stage mainStage = IstinaIliIzazovApplication.getMainStage();
        mainStage.setTitle("Završetak igre");
        
        trenutnaIgra = Repository.getTrenutnaIgra();
        String[] simpleName = this.getClass().getSimpleName().split("Controller");
        trenutnaIgra.setEkranDrugogIgraca(simpleName[0]);
    }

    @FXML
    private void igrajPonovno(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/Pocetna.fxml"));

        Stage mainStage = IstinaIliIzazovApplication.getMainStage();
        mainStage.setScene(new Scene(root));

        mainStage.show();
    }

    @FXML
    private void izadiIzAplikacije(ActionEvent event) {
        Platform.exit();
        System.exit(0);
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

}
