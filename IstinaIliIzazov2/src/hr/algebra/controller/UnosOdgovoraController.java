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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author filip
 */
public class UnosOdgovoraController implements Initializable {

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
        trenutnaIgra = Repository.getTrenutnaIgra();
        
         Stage mainStage = IstinaIliIzazovApplication.getMainStage();
         mainStage.setTitle("Unos odgovora");

        String[] simpleName = this.getClass().getSimpleName().split("Controller");
        trenutnaIgra.setEkranDrugogIgraca(simpleName[0]);
        
        nickNamePrvogIgraca = Repository.getTrenutnaIgra().getNadimakPrvogIgraca();
        nickNameDrugogIgraca = Repository.getTrenutnaIgra().getNadimakDrugogIgraca();

        postaviTekstove();
    }

    @FXML
    private void odgovoriNaPitanje() throws IOException {

        if (formValid()) {
            trenutnaIgra.setTekstOdgovora(taOdgovor.getText().trim());

            Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/OdgovorNaIstinuIliIzazov.fxml"));
            Stage mainStage = IstinaIliIzazovApplication.getMainStage();
            mainStage.setScene(new Scene(root));

            mainStage.show();
        }
    }

    private void postaviTekstove() {
        taOdgovor.setWrapText(true);
        lblTkoKome.setText(nickNamePrvogIgraca + ", " + nickNameDrugogIgraca + " ti je postavio pitanje:");
        lblPitanje.setText(trenutnaIgra.getTekstPitanja());

    }

    @FXML
    private void odgovoriNaPitanjeEnter(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            odgovoriNaPitanje();
        }
    }

    private boolean formValid() {

        Boolean ok = true;

        StringBuilder pogreske = new StringBuilder();

        if (taOdgovor.getText().trim().isEmpty()) {
            pogreske.append("Unos odgovora je obavezan!\n");
            ok = false;
        }
        

        if (ok == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pogreška kod unosa podataka");
            alert.setHeaderText("Kod unosa podataka dogodile su se sljedeće pogreške:");
            alert.setContentText(pogreske.toString());

            alert.showAndWait();
        }

        return ok;

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
