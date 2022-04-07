/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.io.Serializable;

/**
 *
 * @author filip
 */
public class Igra implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nadimakPrvogIgraca;
    private String nadimakDrugogIgraca;
    private String tekstPitanja;
    private String tekstOdgovora;
    private int brojRunde;
    private boolean odabranaIstina;
    private String ekranPrvogIgraca;
    private String ekranDrugogIgraca;
    private String typeOfRequest;

    public Igra(String nadimakPrvogIgraca, String nadimakDrugogIgraca, String tekstPitanja, String tekstOdgovora, int brojRunde, boolean odabranaIstina, String ekranPrvogIgraca, String ekranDrugogIgraca, String typeOfRequest) {
        this.nadimakPrvogIgraca = nadimakPrvogIgraca;
        this.nadimakDrugogIgraca = nadimakDrugogIgraca;
        this.tekstPitanja = tekstPitanja;
        this.tekstOdgovora = tekstOdgovora;
        this.brojRunde = brojRunde;
        this.odabranaIstina = odabranaIstina;
        this.ekranPrvogIgraca = ekranPrvogIgraca;
        this.ekranDrugogIgraca = ekranDrugogIgraca;
        this.typeOfRequest = typeOfRequest;
    }
    
    
    
    public String getTypeOfRequest() {
    return typeOfRequest;
    }
    
    public void setTypeOfRequest(String typeOfRequest) {
    this.typeOfRequest = typeOfRequest;
    }

    public Igra() {
    }

    public String getEkranPrvogIgraca() {
        return ekranPrvogIgraca;
    }

    public void setEkranPrvogIgraca(String ekranPrvogIgraca) {
        this.ekranPrvogIgraca = ekranPrvogIgraca;
    }

    public String getEkranDrugogIgraca() {
        return ekranDrugogIgraca;
    }

    public void setEkranDrugogIgraca(String ekranDrugogIgraca) {
        this.ekranDrugogIgraca = ekranDrugogIgraca;
    }

    public String getNadimakPrvogIgraca() {
        return nadimakPrvogIgraca;
    }

    public void setNadimakPrvogIgraca(String nadimakPrvogIgraca) {
        this.nadimakPrvogIgraca = nadimakPrvogIgraca;
    }

    public String getNadimakDrugogIgraca() {
        return nadimakDrugogIgraca;
    }

    public void setNadimakDrugogIgraca(String nadimakDrugogIgraca) {
        this.nadimakDrugogIgraca = nadimakDrugogIgraca;
    }

    public String getTekstPitanja() {
        return tekstPitanja;
    }

    public void setTekstPitanja(String tekstPitanja) {
        this.tekstPitanja = tekstPitanja;
    }

    public String getTekstOdgovora() {
        return tekstOdgovora;
    }

    public void setTekstOdgovora(String tekstOdgovora) {
        this.tekstOdgovora = tekstOdgovora;
    }

    public int getBrojRunde() {
        return brojRunde;
    }

    public void setBrojRunde(int brojRunde) {
        this.brojRunde = brojRunde;
    }

    public boolean isOdabranaIstina() {
        return odabranaIstina;
    }

    public void setOdabranaIstina(boolean odabranaIstina) {
        this.odabranaIstina = odabranaIstina;
    }
    
    /*   @Override
    public void writeExternal(ObjectOutput out) throws IOException {
    
    out.writeUTF(nadimakPrvogIgraca);
    out.writeUTF(nadimakDrugogIgraca);
    out.writeUTF(tekstPitanja);
    out.writeUTF(tekstOdgovora);
    out.writeInt(brojRunde);
    out.writeBoolean(odabranaIstina);
    out.writeUTF(ekranPrvogIgraca);
    out.writeUTF(ekranDrugogIgraca);
    }
    
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    nadimakPrvogIgraca = in.readUTF();
    nadimakDrugogIgraca = in.readUTF();
    tekstPitanja = in.readUTF();
    tekstOdgovora = in.readUTF();
    brojRunde = in.readInt();
    odabranaIstina = in.readBoolean();
    ekranPrvogIgraca = in.readUTF();
    ekranDrugogIgraca = in.readUTF();
    }*/

     @Override
    public String toString() {
        return "Igra{" + "nadimakPrvogIgraca=" + nadimakPrvogIgraca + ", nadimakDrugogIgraca=" + nadimakDrugogIgraca + ", tekstPitanja=" + tekstPitanja + ", tekstOdgovora=" + tekstOdgovora + ", brojRunde=" + brojRunde + ", odabranaIstina=" + odabranaIstina + ", ekranPrvogIgraca=" + ekranPrvogIgraca + ", ekranDrugogIgraca=" + ekranDrugogIgraca + ", typeOfRequest=" + typeOfRequest + '}';
    }
    
    
}
