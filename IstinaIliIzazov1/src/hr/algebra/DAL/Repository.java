/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.DAL;

import hr.algebra.model.Igra;

/**
 *
 * @author filip
 */
public class Repository {
    
    private static Igra trenutnaIgra = new Igra("", "", "", "", 0, false, "", "", "");

    public static Igra getTrenutnaIgra() {
        return trenutnaIgra;
    }

    public static void setTrenutnaIgra(Igra trenutnaIgra) {
        Repository.trenutnaIgra = trenutnaIgra;
    }
    
    
      
}
