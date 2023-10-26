import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // taille ou dimension du plateau
        int dimension = 4;

        //Créer le plateau avec la dimension spécifiée
        int dimension = 4;
        Plateau plateau = new Plateau(dimension);

        // Créez les joueurs et IA
        Joueur joueurNoir = new Joueur("Joueur Noir", Pion.COULEUR.noir);
        Joueur joueurBlanc = new Joueur("Joueur Blanc", Pion.COULEUR.blanc);
        IA bot1 = new IA("IA Noir", Pion.COULEUR.noir, 6);
        IA bot2 = new IA("IA Blanc", Pion.COULEUR.blanc, 6);

        // Créez le jeu
        Jeu jeu = new Jeu(plateau, joueurNoir, joueurBlanc, bot1, bot2);


        // Menu du jeu d'Othello
        System.out.println("********* Bienvenue dans le jeu d'Othello ! *********");
        System.out.println("**************** [1] Joueur VS Joueur ****************");
        System.out.println("******************* [2] IA vs IA *******************");
        System.out.println("***************** [3] Joueur VS IA *****************\n");

        System.out.println("**************** Choisir une option : *****************");
        System.out.println("**************** => Option choisie : ");

        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            option = scanner.nextInt();
        } while (option != 1 && option != 2 && option != 3);


        switch(option) {
            case 1:
                System.out.println("********** Lancement de Joueur vs Joueur ***********\n");
                jeu.JoueurVSJoueur();
                break;
            case 2:
                System.out.println("************** Lancement de IA vs IA ***************\n");
                jeu.IAvsIA();
                break;
            case 3:
                System.out.println("************ Lancement de Joueur vs IA *************\n");
                jeu.JoueurVSIA();
                break;
            default:
                jeu.IAvsIA();
        }

    }
}