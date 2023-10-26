import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        //Créer le plateau avec la dimension spécifiée
        int dimension = 4;
        Plateau plateau = new Plateau(dimension);

        //Créer les joueurs
        Joueur joueurNoir = new Joueur("Joueur Noir", Pion.COULEUR.noir);
        Joueur joueurBlanc = new Joueur("Joueur Blanc", Pion.COULEUR.blanc);


        //Créer le jeu
        Jeu jeu = new Jeu(plateau, joueurNoir, joueurBlanc);

        //Lancer le jeu
        jeu.jouer();
    }
}