package com.example.scrabble;


import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class deck {
    char[] lettres;
    deck(char[] lettres){
        this.lettres = lettres;
    }

    public LinkedList<mot> correspond(){
        LinkedList<mot> res = new LinkedList<mot>();
        for (int i= 1;i<128;i++) {
            String petitmot = "";
            int i2 = i;
            int longueur = 0;
            for (int j = 0; j<7;j++) {
                if (i2%2==1)
                    longueur+=1;
                i2 = (int)i2/2;
            }
            char[] petitliste = new char[longueur];
            int place = 0;
            for (int j = 0; j<7;j++) {
                if (i2%2==1) {
                    petitliste[place] = lettres[j];
                    place+=1;}
                i2 = (int)i2/2;
            }


        }
        return res;
    }

    public motposable ajouer(dico dictio,plateau plat) {
        ArrayList<motposable> faisable = plat.faisable(trueparti(dictio), dictio);
        motposable max = null;
        int maxi = 0;
        for (motposable mot:faisable){
            if (mot.points(plat)>=maxi) {
                max = mot;
                maxi = mot.points(plat);
            }
        }
        return max;
    }

    public static boolean different(int[] listee) {
        int n = listee.length;
        for (int i = 0;i<n;i++)
            for (int j = i+1;j<n;j++)
                if (listee[i]== listee[j])
                    return false;
        return true;
    }
    public void partition(ArrayList<motcomplet> poss, dico dictio, int[] liste, int index,int pe) {
        if (index>=pe) {
            // la liste est construite -> FIN

            if (different(liste)) {
                String mot = "";
                for (int j = 0;j<pe;j++)
                    mot+=lettres[liste[j]];
                //System.out.println(mot);
                for (int j = 0;j<pe;j++)
                    poss.addAll((new mot(mot)).proche(dictio, j));
            }
            return;
        }

        // ajoute un nouvel element candidat dans la liste
        // - sans ordre -> candidat: tous les elements
        // - avec ordre -> candidat: seulement les elements supérieurs au précédant

        for(int i=0;i<7;i++) {
            liste[index]=i;
            partition(poss, dictio, liste,index+1,pe);
        }
    }
    public ArrayList<motcomplet> trueparti(dico dictio) {
        ArrayList<motcomplet> poss = new ArrayList<motcomplet>();
        for (int i = 0;i<=7;i++){
            int[] liste = new int[i];
            partition(poss,dictio,liste,0,i);
        }
        return poss;
    }

}
