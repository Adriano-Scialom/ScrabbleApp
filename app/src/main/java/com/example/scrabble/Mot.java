package com.example.scrabble;

import java.util.ArrayList;

public class Mot {
    String contenu;
    Mot(String contenu){
        this.contenu = contenu;
    }
    //HashCode, needed to return a long so I couldn't use the hashcode orthography
    //Is in basis 27 (not 26) in order to differentiate between for example "aword" and "word"
    long hashCod() {
        long somme = (long) 0;
        int n = contenu.length();
        for (int i = 0;i<=n-2;i++) {
            somme+= ((int)(contenu.charAt(i))-96);
            somme*=27;
        }
        return somme+ ((int)(contenu.charAt(n-1))-96);
    }
    //Given a String (contenu) and an int (pos) returns all the words lexically correct with the shape of :
    //pos first characters of contenu + any letter + end of contenu
     ArrayList<MotComplet> proche(Dico dico, int pos){
        ArrayList<MotComplet> res = new ArrayList<MotComplet>();
        String deb = contenu.substring(0,pos);
        String fin = contenu.substring(pos);
        String motplus = deb+"a"+fin;
        long valuemot = (new Mot(motplus)).hashCod();
        int puis = (int) Math.pow((double)27,(double)(contenu.length()-pos));
        valuemot -= puis;
        for (int i = 1;i<27;i++) {
            if (dico.dico.containsKey(((valuemot += puis)))) {
                res.add(new MotComplet(deb+(char)(i+96)+fin,pos));
            }
        }
        return res;
    }


    public String toString() {
        return contenu;
    }
}
