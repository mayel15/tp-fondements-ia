
import java.util.ArrayList;
import java.util.Scanner;

public class Jeu {
    private Plateau plateau;
    private Joueur joueurNoir;
    private Joueur joueurBlanc;
    private Joueur joueurActuel;
    private IA iaActuel;
    private IA iaNoir;
    private IA iaBlanc;

    public Jeu(Plateau plateau, Joueur joueurNoir, Joueur joueurBlanc, IA botNoir, IA botBlanc) {
        this.plateau = plateau;
        this.joueurNoir = joueurNoir;
        this.joueurBlanc = joueurBlanc;
        // c'est le joueur noir qui commence
        this.joueurActuel = joueurNoir;
        // c'est le IA noir qui commence
        this.iaActuel = botBlanc;
        this.iaNoir = botNoir;
        this.iaBlanc = botBlanc;
    }

    // pour l'option JoueurVSJoueur; et c'est joueur noir qui commence
    public Jeu(Plateau plateau, Joueur joueurNoir, Joueur joueurBlanc) {
        this.plateau = plateau;
        this.joueurNoir = joueurNoir;
        this.joueurBlanc = joueurBlanc;
        this.joueurActuel = joueurNoir;

    }

    // Pour l'option IAvsIA; et c'est iaBlanc qui commence
    public Jeu(Plateau plateau, IA iaNoir, IA iaBlanc) {
        this.plateau = plateau;
        this.iaActuel = iaBlanc;
        this.iaNoir = iaNoir;
        this.iaBlanc = iaBlanc;
    }

    // jeu entre 2 joueurs
    public void JoueurVSJoueur() {
        while (true) {
            // affichage de la grille
            System.out.println(plateau);

            // verification si le plateau est plein
            if (plateau.plateauEstPlein()) {
                checkWin();
                break;
            } else if (plateau.estPossibleDeJouer(joueurActuel.getCouleur())) {
                joueurActuel.jouerCoup(plateau);
            } else {
                System.out.println("Le joueur " + joueurActuel.getNom() + " ne peut pas jouer donc passe son tour !");
            }

            // au tour de l'adversaire de joueur
            joueurActuel = (joueurActuel == joueurNoir) ? joueurBlanc : joueurNoir;
        }
    }

    // jeu entre 2 IA
    public void IAvsIA() {

        while (true) {
            // affichage de la grille
            System.out.println(plateau);

            // verification si le plateau est plein
            if (plateau.plateauEstPlein()) {
                checkWin();
                break;
            } else if (plateau.estPossibleDeJouer(joueurActuel.getCouleur())) {
                iaActuel.jouerCoup(plateau);
            } else {
                System.out.println("Le joueur " + joueurActuel.getNom() + " ne peut pas jouer donc passe son tour !");
            }

            // au tour de l'adversaire de joueur
            iaActuel = (iaActuel == iaNoir) ? iaBlanc : iaNoir;
        }

    }

    // jeu entre un joueur et un IA
    public void JoueurVSIA() {
        int tourDeJeu = 0;
        while (true) {
            // affichage de la grille
            System.out.println(plateau);

            // verification si le plateau est plein
            if (plateau.plateauEstPlein()) {
                checkWin();
                break;
            } else if (tourDeJeu == 0 && plateau.estPossibleDeJouer(joueurNoir.getCouleur())) {
                joueurNoir.jouerCoup(plateau);
                tourDeJeu = 1;

            } else if (tourDeJeu == 1 && plateau.estPossibleDeJouer(iaBlanc.getCouleur() )) {
                iaBlanc.jouerCoup(plateau);
                tourDeJeu = 0;
            } else {
                System.out.println("Le " + joueurActuel.getNom() + " ne peut pas jouer donc passe son tour !");
                tourDeJeu = (tourDeJeu == 0) ? 1 : 0;
            }

        }
    }


    // permet de verifier si c'est gagne
    public void checkWin() {
        int pionsNoirs = 0;
        int pionsBlancs = 0;

        for (int ligne = 0; ligne < plateau.getGrille().size(); ligne++) {
            for (int colonne = 0; colonne < plateau.getGrille().get(0).size(); colonne++) {
                Pion pion = plateau.getGrille().get(ligne).get(colonne);
                if (pion.couleur == Pion.COULEUR.noir) {
                    pionsNoirs++;
                } else if (pion.couleur == Pion.COULEUR.blanc) {
                    pionsBlancs++;
                }
            }
        }

        if (pionsNoirs > pionsBlancs) {
            System.out.println("Joueur noir a gagné : "+pionsNoirs+" à "+pionsBlancs );
        } else if (pionsBlancs > pionsNoirs) {
            System.out.println("Joueur blanc a gagné : "+pionsBlancs+" à "+pionsNoirs );
        } else {
            System.out.println("Match nul : "+pionsNoirs+" Partout !" );
        }
    }

}