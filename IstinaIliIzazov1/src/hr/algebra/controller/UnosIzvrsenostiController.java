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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class UnosIzvrsenostiController implements Initializable {

    @FXML
    private Label lblTkoKome;
    @FXML
    private Label lblIzazov;
    @FXML
    private Button btnDa;

    private Igra trenutnaIgra;
    private String nickNamePrvogIgraca;
    private String nickNameDrugogIgraca;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       updateTrenutnaIgra();

        Stage mainStage = IstinaIliIzazovApplication.getMainStage();
        mainStage.setTitle("Unos izvršenosti");

        String[] simpleName = this.getClass().getSimpleName().split("Controller");
        trenutnaIgra.setEkranDrugogIgraca(simpleName[0]);

        nickNamePrvogIgraca = Repository.getTrenutnaIgra().getNadimakPrvogIgraca();
        nickNameDrugogIgraca = Repository.getTrenutnaIgra().getNadimakDrugogIgraca();

        postaviTekstove();
    }

    @FXML
    private void odgovoriIzazov(ActionEvent event) throws IOException {
        
         Button odabraniGumb = (Button) event.getSource();

        if (odabraniGumb == btnDa) {
            trenutnaIgra.setTekstOdgovora("DA");
            trenutnaIgra.setTypeOfRequest("setTekstOdgovora");
            executeChange("UnosIzvrsenostiController 1");
        } else {
            trenutnaIgra.setTekstOdgovora("NE");
            trenutnaIgra.setTypeOfRequest("setTekstOdgovora");
            executeChange("UnosIzvrsenostiController 1");
        }

        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/OdgovorNaIstinuIliIzazov.fxml"));
        Stage mainStage = IstinaIliIzazovApplication.getMainStage();
        mainStage.setScene(new Scene(root));

        mainStage.show();
    }

    private void postaviTekstove() {
        lblTkoKome.setText(nickNamePrvogIgraca + ", " + nickNameDrugogIgraca + " ti je postavio izazov:");
        lblIzazov.setText(trenutnaIgra.getTekstPitanja());
        
        
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
