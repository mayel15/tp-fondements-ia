import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int dimension = 4; // Vous pouvez ajuster la taille du plateau ici

        // Créez le plateau avec la dimension spécifiée
        Plateau plateau = new Plateau(dimension);

        // Créez les joueurs
        Joueur joueurNoir = new Joueur("Joueur Noir", Pion.COULEUR.noir);
        Joueur joueurBlanc = new Joueur("Joueur Blanc", Pion.COULEUR.blanc);

        // Créez le jeu
        Jeu jeu = new Jeu(plateau, joueurNoir, joueurBlanc);

        // Lancez le jeu
        jeu.jouer();
    }
}