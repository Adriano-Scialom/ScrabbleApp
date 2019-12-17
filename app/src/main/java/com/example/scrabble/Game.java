package com.example.scrabble;


import java.util.HashMap;
import java.util.Random;


//{}
public class Game {
    Board board;
    Deck j1;
    Deck j2;
    private Dico dico;
    int score1 = 0;
    int score2 = 0;
    HashMap<Integer,Character> bag;

    //Instantiate a game and fills the bag
    Game(Dico dico, Board plateau) {
        board = plateau;
        board.grid[7][7] = 'e';
        score1 = 0;
        score2 = 0;
        this.dico = dico;
        bag = new HashMap<Integer,Character>();
        put(0,'a',9);
        put(9,'b',2);
        put(11,'c',2);
        put(13,'d',3);
        put(16,'e',15);
        put(31,'f',2);
        put(33,'g',2);
        put(35,'h',2);
        put(37,'i',8);
        put(45,'j',1);
        put(46,'k',1);
        put(47,'l',5);
        put(52,'m',3);
        put(55,'n',6);
        put(61,'o',6);
        put(67,'p',2);
        put(69,'q',1);
        put(70,'r',6);
        put(76,'s',6);
        put(82,'t',6);
        put(88,'u',6);
        put(94,'v',2);
        put(96,'w',1);
        put(97,'x',1);
        put(98,'y',1);
        put(99,'z',1);
        char[] dec1 = { 'a', 'a', 'a', 'a',
                'a', 'a', 'a' };
        j1 = new Deck(dec1);
        char[] dec2 = { 'a', 'a', 'a', 'a',
                'a', 'a', 'a' };
        j2 = new Deck(dec2);
        for (int i = 0;i<7;i++){
            try {
                j1.letters[i] = tire();
                j2.letters[i] = tire();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //Puts number times the letter c in the bag (to replenish it at the beginning of a game)
    void put(int min, char c, int number){
        for (int i=min;i<min+number;i++)
            bag.put(i, c);
    }
    //Return a char taken randomly from the bag of throws an Exception if the bag is empty
    public char tire() throws Exception {
        if(!bag.isEmpty()){
            Random rand = new Random();
            int alea = rand.nextInt(100);
            while (!bag.containsKey(alea))
                alea = rand.nextInt(100);
            return bag.remove(alea);}
        else
            throw new Exception();
    }

    boolean tour(int i) {
        MotPosable mot;
        boolean res = true;
        if (i==1){
            mot = j1.ajouer(dico, board);
            //System.out.println("Joueur 1");
            if (mot.lettres.length()==8){

            }
            score1+= mot.points(board);}
        //System.out.println(" "+score1);}
        else{
            mot = j2.ajouer(dico, board);
            //System.out.println("Joueur 2");
            if (mot.lettres.length()==8){
                //System.out.print("SCRABBLE"+" ");
                System.out.println(mot.lettres+" "+mot.points(board));
            }
            score2+= mot.points(board);
            //System.out.println(" "+score2);
        }
        if (mot.dir==1){
            for (int a = mot.j-mot.pos;a<mot.j-mot.pos+mot.lettres.length();a++)
                board.grid[mot.i][a]=mot.lettres.charAt(a+mot.pos-mot.j);
        }
        else{
            for (int a = mot.i-mot.pos;a<mot.i-mot.pos+mot.lettres.length();a++)
                board.grid[a][mot.j]=mot.lettres.charAt(a+mot.pos-mot.i);
        }
        //if (Mot.letters.length()==8 && Mot.points(board)>130)
            //board.imprime();
        mot.lettres = mot.lettres.substring(0, mot.pos) + mot.lettres.substring(mot.pos+1);
        int achanger[] = new int[mot.lettres.length()];
        for (int a = 0;a<mot.lettres.length();a++)
            achanger[a]=-1;
        if (i==1){
            for (int a = 0;a<mot.lettres.length();a++)
                for (int j = 0;j<7;j++)
                    if(j1.letters[j]==mot.lettres.charAt(a) && !contains(achanger,j))
                        achanger[a] = j;
            try{
                for (int a = 0;a<mot.lettres.length();a++)
                    j1.letters[achanger[a]]= tire();
            }
            catch(Exception e){
                res = false;}

        }
        else{
            for (int a = 0;a<mot.lettres.length();a++)
                for (int j = 0;j<7;j++)
                    if(j2.letters[j]==mot.lettres.charAt(a) && !contains(achanger,j))
                        achanger[a] = j;
            try{
                for (int a = 0;a<mot.lettres.length();a++)
                    j2.letters[achanger[a]]= tire();
            }
            catch(Exception e){
                res = false;}

        }
        return res;
    }

    static boolean contains(int[] array, int v) {
        boolean result = false;
        for(int i : array){
            if(i == v){
                result = true;
                break;
            }
        }
        return result;
    }

}

