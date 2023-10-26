import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IA {
    private String nom;
    private Pion.COULEUR couleur;
    private int profondeurMax;

    private int[][] grilleEvaluation;


    public IA(String nom, Pion.COULEUR couleur, int profondeurMax) {
        this.nom = nom;
        this.couleur = couleur;
        this.profondeurMax = profondeurMax;
        this.initializeEvaluationGrid();
    }

    public void initializeEvaluationGrid() {
        this.grilleEvaluation = new int[][] {
                {500, -150, 30, 10, 10, 30, -150, 500},
                {-150, -250, 0, 0, 0, 0, -250, -150},
                {30, 0, 1, 2, 2, 1, 0, 30},
                {10, 0, 2, 16, 16, 2, 0, 10},
                {10, 0, 2, 16, 16, 2, 0, 10},
                {30, 0, 1, 2, 2, 1, 0, 30},
                {-150, -250, 0, 0, 0, 0, -250, -150},
                {500, -150, 30, 10, 10, 30, -150, 500}
        };
    }

    // permetr  de joueur le meilleur coup
    public void jouerCoup(Plateau plateau) {
        int[] meilleurCoup = minimax(plateau, profondeurMax, true);
        System.out.println("Tour de l'IA : " + this.nom);
        int ligne = meilleurCoup[0];
        int colonne = meilleurCoup[1];
        System.out.println(ligne);
        System.out.println(colonne);

        plateau.placerPion(ligne, colonne, couleur);

    }

    // algorithme min max permettant de construire l'arbre des possibilités
    private int[] minimax(Plateau plateau, int profondeur, boolean maximizingPlayer) {
        if (profondeur == 0 || plateau.plateauEstPlein()) {
            int score = evaluerPlateau(plateau);
            return new int[]{score, -1, -1};
        }

        List<int[]> coupsPossibles = obtenirCoupsPossibles(plateau, couleur);

        if (maximizingPlayer) {
            int meilleurScore = Integer.MIN_VALUE;
            int[] meilleurCoup = {-1, -1};

            for (int[] coup : coupsPossibles) {
                Plateau copiePlateau = plateau.clone();

                if (copiePlateau.positionValide(coup[0], coup[1], couleur)) {
                    copiePlateau.placerPion(coup[0], coup[1], couleur);

                    int[] scoreCoup = minimax(copiePlateau, profondeur - 1, false);

                    if (scoreCoup[0] > meilleurScore) {
                        meilleurScore = scoreCoup[0];
                        meilleurCoup[0] = coup[0];
                        meilleurCoup[1] = coup[1];
                    }
                }
            }

            return meilleurCoup;
        } else {
            int pireScore = Integer.MAX_VALUE;
            int[] pireCoup = {-1, -1};

            for (int[] coup : coupsPossibles) {
                Plateau copiePlateau = plateau.clone();
                Pion.COULEUR adversaire = (couleur == Pion.COULEUR.noir) ? Pion.COULEUR.blanc : Pion.COULEUR.noir;

                if (copiePlateau.positionValide(coup[0], coup[1], adversaire)) {
                    copiePlateau.placerPion(coup[0], coup[1], adversaire);

                    int[] scoreCoup = minimax(copiePlateau, profondeur - 1, true);

                    if (scoreCoup[0] < pireScore) {
                        pireScore = scoreCoup[0];
                        pireCoup[0] = coup[0];
                        pireCoup[1] = coup[1];
                    }
                }
            }

            return pireCoup;
        }
    }

    // permet d'obtenir l'ensemble des coups possibles
    private List<int[]> obtenirCoupsPossibles(Plateau plateau, Pion.COULEUR couleur) {
        List<int[]> coupsPossibles = new ArrayList<>();
        for (int ligne = 0; ligne < plateau.getGrille().size(); ligne++) {
            for (int colonne = 0; colonne < plateau.getGrille().get(0).size(); colonne++) {
                //System.out.println(couleur);
                boolean positionValide = plateau.positionValide(ligne, colonne, couleur);
                if (positionValide) {
                    coupsPossibles.add(new int[]{ligne, colonne});
                }
            }
        }
        return coupsPossibles;
    }

    // fonction d'évaluation de l'algorithme minmax
    private int evaluerPlateau(Plateau plateau) {
        int score = 0;

        for (int ligne = 0; ligne < plateau.getGrille().size(); ligne++) {
            for (int colonne = 0; colonne < plateau.getGrille().get(0).size(); colonne++) {
                Pion pion = plateau.getGrille().get(ligne).get(colonne);
                int valeurCellule = grilleEvaluation[ligne][colonne];

                if (pion.couleur == couleur) {
                    score += valeurCellule;
                } else if (pion.couleur != Pion.COULEUR.neutre) {
                    score -= valeurCellule;
                }
            }
        }

        return score;
    }

    public Pion.COULEUR getCouleur() {
        return couleur;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return "IA{" +
                "couleur=" + couleur +
                ", profondeurMax=" + profondeurMax +
                ", grilleEvaluation=" + Arrays.toString(grilleEvaluation) +
                '}';
    }
}
