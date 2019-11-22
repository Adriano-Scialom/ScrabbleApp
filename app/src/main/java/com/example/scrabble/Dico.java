package com.example.scrabble;


import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import android.util.Log;


public class Dico {
    HashMap<Long, Mot> dico;

    Dico(){
        this.dico = new HashMap<Long, Mot>();
    }

    public void charge(InputStream is) throws IOException{
        String ligne = "";
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        ligne = new String(buffer);
        String[] table = ligne.split("\n");
        for (String mot:table){
            if (mot.length()<9){
            dico.put(new Mot(mot).hashCod(),new Mot(mot));}
        }
        Log.v("Chargé",String.valueOf(dico.size()));

    }


}