package com.example.scrabble;


import java.util.ArrayList;


public class Deck {
    char[] letters;
    Deck(char[] letters){
        this.letters = letters;
    }


    //Returns the best playable word considering the letters in the deck and the board
    public MotPosable ajouer(Dico dico, Board board) {
        ArrayList<MotPosable> doable = board.doable(trueparti(dico), dico);
        MotPosable max = null;
        int maxi = 0;
        for (MotPosable mot:doable){
            if (mot.points(board)>=maxi) {
                max = mot;
                maxi = mot.points(board);
            }
        }
        return max;
    }

    public static boolean different(int[] listee) {
        int n = listee.length;
        for (int i = 0;i<n;i++)
            for (int j = i+1;j<n;j++)
                if (listee[i] == listee[j])
                    return false;
        return true;
    }
    //Needed in trueparti
    public void partition(ArrayList<MotComplet> poss, Dico dico, int[] liste, int index, int pe) {
        if (index>=pe) {

            if (different(liste)) {
                String word = "";
                for (int j = 0;j<pe;j++)
                    word+= letters[liste[j]];
                //System.out.println(Mot);
                for (int j = 0;j<pe;j++)
                    poss.addAll((new Mot(word)).proche(dico, j));
            }
            return;
        }

        for(int i=0;i<7;i++) {
            liste[index]=i;
            partition(poss, dico, liste,index+1,pe);
        }
    }

    //Returns a list of all lexically valid words made by taking the letters in the deck plus a "joker" one
    // (the one that is already on the board)
    public ArrayList<MotComplet> trueparti(Dico dictio) {
        ArrayList<MotComplet> poss = new ArrayList<MotComplet>();
        for (int i = 0;i<=7;i++){
            int[] liste = new int[i];
            partition(poss,dictio,liste,0,i);
        }
        return poss;
    }

}
