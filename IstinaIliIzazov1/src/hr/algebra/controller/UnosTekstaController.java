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
import javafx.scene.control.Button;
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
//PLAYER2
public class UnosTekstaController implements Initializable {

    @FXML
    private Label lblOdabir;
    @FXML
    private Label lblUnos;
    @FXML
    private TextArea taTekst;
    @FXML
    private Button btnUnesiTekst;

    private Igra trenutnaIgra;
    private String nickNamePrvogIgraca;
    private String nickNameDrugogIgraca;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Repository.setTrenutnaIgra(ClientThread.getClientIgra());
        trenutnaIgra = Repository.getTrenutnaIgra();
        trenutnaIgra.setTypeOfRequest(""); //vraća cijeli objekt nepromjenjeni
        new ClientThread(trenutnaIgra).start();

        try {
            Thread.sleep(250);
        } catch (InterruptedException ex) {
            Logger.getLogger(PocetnaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        trenutnaIgra = Repository.getTrenutnaIgra();

        Stage mainStage = IstinaIliIzazovApplication.getMainStage();

        /*if (trenutnaIgra.isOdabranaIstina()) {
        mainStage.setTitle("Unos vašeg pitanja");
        } else {
        mainStage.setTitle("Unos vašeg izazova");
        }*/
        String[] simpleName = this.getClass().getSimpleName().split("Controller");
        trenutnaIgra.setEkranPrvogIgraca(simpleName[0]);

        //nickNamePrvogIgraca = Repository.getTrenutnaIgra().getNadimakPrvogIgraca();
        //nickNameDrugogIgraca = Repository.getTrenutnaIgra().getNadimakDrugogIgraca();
        //postaviTekstove(trenutnaIgra.isOdabranaIstina());
    }

    @FXML
    private void unesiTekst() throws IOException {

        if (formValid()) {

            trenutnaIgra.setTekstPitanja(taTekst.getText().trim());

            Parent root = FXMLLoader.load(getClass().getResource("/hr/algebra/view/EkranCekanjaIzvrsenosti.fxml"));

            Stage mainStage = IstinaIliIzazovApplication.getMainStage();
            mainStage.setScene(new Scene(root));

            mainStage.show();

            mainStage = IstinaIliIzazovApplication.getMainStage();

            mainStage.setScene(new Scene(root));
            mainStage.show();

        }

    }

    private void postaviTekstove(boolean odabranaIstina) {

        taTekst.setWrapText(true);

        if (odabranaIstina) {
            lblOdabir.setText(nickNamePrvogIgraca + " je odabrao ISTINU! "
                    + nickNameDrugogIgraca + " - pitaj ga pitanje!");

            lblUnos.setText("Molimo unesite vaše pitanje");

            btnUnesiTekst.setText("PITAJ");
        } else {
            lblOdabir.setText(nickNamePrvogIgraca + " je odabrao IZAZOV! "
                    + nickNameDrugogIgraca + " - postavi mu izazov!");

            lblUnos.setText("Molimo unesite vaš izazov");

            btnUnesiTekst.setText("IZAZOVI");
        }
    }

    @FXML
    private void unesiTekstEnter(KeyEvent event) throws IOException {
        if (event.getCode().equals(KeyCode.ENTER)) {
            unesiTekst();
        }
    }

    private boolean formValid() {

        Boolean ok = true;

        StringBuilder pogreske = new StringBuilder();

        if (taTekst.getText().trim().isEmpty()) {
            pogreske.append("Unos teksta je obavezan!\n");
            ok = false;
        }

        if (taTekst.getText().trim().length() > 60) {
            pogreske.append("Pitanje ne smije imati više od 60 znakova!\n");
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
