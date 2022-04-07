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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author filip
 */
//PLAYER1
public class OdgovorNaIstinuIliIzazovController implements Initializable {

    @FXML
    private Label lblTkoKome;
    @FXML
    private Label lblPitanje;
    @FXML
    private TextArea taOdgovor;

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

        if (trenutnaIgra.isOdabranaIstina()) {
        mainStage.setTitle("Odgovor na istinu");
        } else {
        mainStage.setTitle("Odgovor na izazov");
        }
        
        
        String[] simpleName = this.getClass().getSimpleName().split("Controller");
        trenutnaIgra.setEkranPrvogIgraca(simpleName[0]);
        
        nickNamePrvogIgraca = Repository.getTrenutnaIgra().getNadimakPrvogIgraca();
        nickNameDrugogIgraca = Repository.getTrenutnaIgra().getNadimakDrugogIgraca();

        postaviTekstove(trenutnaIgra.isOdabranaIstina());
    }

    @FXML
    private void sljedecaRunda(ActionEvent event) throws IOException {
        
        
        trenutnaIgra.setBrojRunde(+1);
        
        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/OdabirKategorije.fxml"));

        Stage mainStage = IstinaIliIzazovApplication.getMainStage();
        mainStage.setScene(new Scene(root));

        mainStage.show();
    }

    @FXML
    private void zavrsiIgru(ActionEvent event) throws IOException {
        
        trenutnaIgra.setBrojRunde(0);
        
        Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/ZavrsetakIgre.fxml"));

        Stage mainStage = IstinaIliIzazovApplication.getMainStage();
        mainStage.setScene(new Scene(root));

        mainStage.show();
    }

    private void postaviTekstove(boolean odabranaIstina) {
        
        taOdgovor.setWrapText(true);

         if (odabranaIstina) {
        lblTkoKome.setText(nickNamePrvogIgraca + ", " + nickNameDrugogIgraca + " ti je postavio pitanje koje je glasilo:");
        } else {
        lblTkoKome.setText(nickNamePrvogIgraca + ", izazvan si od " + nickNameDrugogIgraca + " izazovom koji je glasio:");
        }

        lblPitanje.setText(trenutnaIgra.getTekstPitanja());

         if (!trenutnaIgra.isOdabranaIstina()) {
        
        if (trenutnaIgra.getTekstOdgovora().contains("DA")) {
        taOdgovor.setText("IZAZOV JE IZVRŠEN");
        } else {
        taOdgovor.setText("IZAZOV NIJE IZVRŠEN");
        }
        
        } else {
        taOdgovor.setText(trenutnaIgra.getTekstOdgovora());
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
