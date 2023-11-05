// ALMAJJO Alaaeddin & THIAM Pape

import java.util.Scanner;

public class Joueur {
    private String nom;
    private Pion.COULEUR couleur;
    private Scanner scanner;
    private int nombreCoupsJoues;

    public Joueur(String nom, Pion.COULEUR couleur) {
        this.nom = nom;
        this.couleur = couleur;
        this.scanner = new Scanner(System.in);
        this.nombreCoupsJoues = 0;
    }

    // permet de jouer un coup pour un joueur
    public void jouerCoup(Plateau plateau) {
        System.out.println("Tour de " + nom + ". Entrez la position (ligne colonne) pour placer votre pion :");
        int ligne = scanner.nextInt();
        int colonne = scanner.nextInt();
        plateau.placerPion(ligne, colonne, couleur);
        this.nombreCoupsJoues++;
    }

    public String getNom() {
        return nom;
    }

    public Pion.COULEUR getCouleur() {
        return couleur;
    }
}
