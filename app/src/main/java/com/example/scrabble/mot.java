package com.example.scrabble;

import java.util.ArrayList;

public class mot {
    String contenu;
    mot(String contenu){
        this.contenu = contenu;
    }
    public long hashCod() {
        Long somme = (long) 0;
        int n = contenu.length();
        for (int i = 0;i<=n-2;i++) {
            somme+= ((int)(contenu.charAt(i))-96);
            somme*=27;
        }
        return somme+ ((int)(contenu.charAt(n-1))-96);
    }
    public ArrayList<motcomplet> proche(dico dictio,int pos){
        ArrayList<motcomplet> res = new ArrayList<motcomplet>();
        String deb = contenu.substring(0,pos);
        String fin = contenu.substring(pos);
        String motplus = deb+"a"+fin;
        long valuemot = (new mot(motplus)).hashCod();
        int puis = (int) Math.pow((double)27,(double)(contenu.length()-pos));
        valuemot -= puis;
        for (int i = 1;i<27;i++) {
            if (dictio.dico.containsKey(((valuemot += puis)))) {
                res.add(new motcomplet(deb+(char)(i+96)+fin,pos));

            }
        }
        return res;

    }
    public String toString() {
        return contenu;
    }
}
