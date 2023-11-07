// ALMAJJO Alaaeddin & THIAM Pape

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // plateau avec la diension specifie qui est de base 8
        int dimension = 8;
        Plateau plateau = new Plateau(dimension);

        // joueurs et IA
        Joueur joueurNoir = new Joueur("Joueur Noir", Pion.COULEUR.noir);
        Joueur joueurBlanc = new Joueur("Joueur Blanc", Pion.COULEUR.blanc);
        IA bot1 = new IA("IA Noir", IA.ALGO.alphaBeta, Pion.COULEUR.noir, 6, IA.STRATEGIE.positionnel);
        IA bot2 = new IA("IA Blanc", IA.ALGO.alphaBeta, Pion.COULEUR.blanc, 6, IA.STRATEGIE.positionnel);

        // jeu
        Jeu jeu = new Jeu(plateau, joueurNoir, joueurBlanc, bot1, bot2);


        // menu du jeu d'Othello
        System.out.println("********* Bienvenue dans le jeu d'Othello ! **********");
        System.out.println("**************** [1] Joueur VS Joueur ****************");
        System.out.println("***************** [2] Joueur VS IA *******************");
        System.out.println("******************* [3] IA vs IA *********************");
        System.out.println("******************** [4] Stats ***********************\n");

        System.out.println("*************** Choisir une option : *****************");
        System.out.println("**************** => Option choisie : ");

        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            option = scanner.nextInt();
        } while (option != 1 && option != 2 && option != 3 && option != 4);


        switch(option) {
            case 1:
                System.out.println("********** Lancement de Joueur vs Joueur ***********\n");
                jeu.JoueurVSJoueur();
                break;
            case 2:
                System.out.println("************ Lancement de Joueur vs IA *************\n");
                jeu.JoueurVSIA();
                break;
            case 3:
                System.out.println("************** Lancement de IA vs IA ***************\n");
                jeu.IAvsIA(true);
                break;
            case 4:
                System.out.println("******** Lancement des stats pour IA vs IA *********\n");
                jeu.statsIAvsIA();
                break;
            default:
                jeu.IAvsIA(true);
        }

    }
}
