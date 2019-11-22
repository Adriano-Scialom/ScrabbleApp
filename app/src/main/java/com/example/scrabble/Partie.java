package com.example.scrabble;


import java.util.HashMap;
import java.util.Random;


//{}
public class Partie {
    Plateau plat;
    Deck j1;
    Deck j2;
    private Dico dictio;
    int score1 = 0;
    int score2 = 0;
    HashMap<Integer,Character> sac;
    Partie(Dico dico, Plateau plateau) {
        plat = plateau;
        plat.grid[7][7] = 'e';
        score1 = 0;
        score2 = 0;
        dictio = dico;
        sac = new HashMap<Integer,Character>();
        mettre(0,'a',9);
        mettre(9,'b',2);
        mettre(11,'c',2);
        mettre(13,'d',3);
        mettre(16,'e',15);
        mettre(31,'f',2);
        mettre(33,'g',2);
        mettre(35,'h',2);
        mettre(37,'i',8);
        mettre(45,'j',1);
        mettre(46,'k',1);
        mettre(47,'l',5);
        mettre(52,'m',3);
        mettre(55,'n',6);
        mettre(61,'o',6);
        mettre(67,'p',2);
        mettre(69,'q',1);
        mettre(70,'r',6);
        mettre(76,'s',6);
        mettre(82,'t',6);
        mettre(88,'u',6);
        mettre(94,'v',2);
        mettre(96,'w',1);
        mettre(97,'x',1);
        mettre(98,'y',1);
        mettre(99,'z',1);
        char[] dec1 = { 'a', 'a', 'a', 'a',
                'a', 'a', 'a' };
        j1 = new Deck(dec1);
        char[] dec2 = { 'a', 'a', 'a', 'a',
                'a', 'a', 'a' };
        j2 = new Deck(dec2);
        for (int i = 0;i<7;i++){
            try {
                j1.lettres[i] = tire();
                j2.lettres[i] = tire();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void mettre(int min,char c,int nombre){
        for (int i=min;i<min+nombre;i++)
            sac.put(i, c);
    }

    public char tire() throws Exception {
        if(!sac.isEmpty()){
            Random rand = new Random();
            int alea = rand.nextInt(100);
            while (!sac.containsKey(alea))
                alea = rand.nextInt(100);
            return sac.remove(alea);}
        else
            throw new Exception();
    }

    public boolean tour(int i) {
        MotPosable mot;
        boolean res = true;
        if (i==1){
            mot = j1.ajouer(dictio, plat);
            //System.out.println("Joueur 1");
            if (mot.lettres.length()==8){

            }
            score1+= mot.points(plat);}
        //System.out.println(" "+score1);}
        else{
            mot = j2.ajouer(dictio, plat);
            //System.out.println("Joueur 2");
            if (mot.lettres.length()==8){
                //System.out.print("SCRABBLE"+" ");
                System.out.println(mot.lettres+" "+mot.points(plat));
            }
            score2+= mot.points(plat);
            //System.out.println(" "+score2);
        }
        if (mot.dir==1){
            for (int a = mot.j-mot.pos;a<mot.j-mot.pos+mot.lettres.length();a++)
                plat.grid[mot.i][a]=mot.lettres.charAt(a+mot.pos-mot.j);
        }
        else{
            for (int a = mot.i-mot.pos;a<mot.i-mot.pos+mot.lettres.length();a++)
                plat.grid[a][mot.j]=mot.lettres.charAt(a+mot.pos-mot.i);
        }
        //if (Mot.lettres.length()==8 && Mot.points(plat)>130)
            //plat.imprime();
        mot.lettres = mot.lettres.substring(0, mot.pos) + mot.lettres.substring(mot.pos+1);
        int achanger[] = new int[mot.lettres.length()];
        for (int a = 0;a<mot.lettres.length();a++)
            achanger[a]=-1;
        if (i==1){
            for (int a = 0;a<mot.lettres.length();a++)
                for (int j = 0;j<7;j++)
                    if(j1.lettres[j]==mot.lettres.charAt(a) && !contains(achanger,j))
                        achanger[a] = j;
            try{
                for (int a = 0;a<mot.lettres.length();a++)
                    j1.lettres[achanger[a]]= tire();
            }
            catch(Exception e){
                res = false;}

        }
        else{
            for (int a = 0;a<mot.lettres.length();a++)
                for (int j = 0;j<7;j++)
                    if(j2.lettres[j]==mot.lettres.charAt(a) && !contains(achanger,j))
                        achanger[a] = j;
            try{
                for (int a = 0;a<mot.lettres.length();a++)
                    j2.lettres[achanger[a]]= tire();
            }
            catch(Exception e){
                res = false;}

        }
        return res;
    }
    public static boolean contains(int[] array, int v) {

        boolean result = false;

        for(int i : array){
            if(i == v){
                result = true;
                break;
            }
        }

        return result;
    }
    public void joue(){
        int tour = 1;
        while(tour(tour))
            tour = 1-tour;
    }

}

