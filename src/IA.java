// ALMAJJO Alaaeddin & THIAM Pape

import java.util.ArrayList;
import java.util.List;

public class IA {
    private String nom;
    private Pion.COULEUR couleur;
    private int profondeurMax;
    private int nombreCoupJoues;
    private int[][] grilleEvaluation;

    private STRATEGIE strategie;
    private ALGO algo;


    public IA(String nom, ALGO algo, Pion.COULEUR couleur, int profondeurMax, STRATEGIE strategie) {
        this.nom = nom;
        this.couleur = couleur;
        this.profondeurMax = profondeurMax;
        this.strategie = strategie;
        this.nombreCoupJoues = 0;
        this.algo = algo;
        this.initializeEvaluationGrid();
    }

    // permet d'initialiser la grille du plateau
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
    
    enum ALGO {
        minmax,
        alphaBeta, 
        negamax
    }

    enum STRATEGIE {
        positionnel,
        absolu,
        mobilite,
        mixte
    }

    // permet  de joueur le meilleur coup; parametre avecLog pour gerer les affichages
    public void jouerCoup(Plateau plateau, boolean avecLog) {
        int[] meilleurCoup = new int[2];
        switch (algo) {
            case minmax:
                meilleurCoup = minimax(plateau, profondeurMax, true);
                break;
            case alphaBeta:
                meilleurCoup = alphaBeta(plateau, profondeurMax, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
                break;
            case negamax:
                meilleurCoup = negamax(plateau, profondeurMax, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
                break;
        }
        if(avecLog) System.out.println("Tour de l'IA : " + this.nom);
        int ligne = meilleurCoup[0];
        int colonne = meilleurCoup[1];
        if(avecLog) {
            System.out.println(ligne);
            System.out.println(colonne);
        }

        plateau.placerPion(ligne, colonne, couleur);
        this.nombreCoupJoues++;
    }

    // algorithme min max permettant de construire l'arbre des possibilites
    private int[] minimax(Plateau plateau, int profondeur, boolean maximizingPlayer) {
        if (profondeur == 0 || plateau.plateauEstPlein()) {
            int score = 0;
            switch (strategie) {
                case positionnel:
                    score = evaluerPositionnel(plateau);
                    return new int[]{score, -1, -1};
                case absolu:
                    score = evaluerAbsolu(plateau, couleur);
                    return new int[]{score, -1, -1};
                case mobilite:
                    score = evaluerMobilite(plateau, couleur);
                    return new int[]{score, -1, -1};
                case mixte:
                    score = evaluerMixte(plateau, couleur);
                    return new int[]{score, -1, -1};
            }

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

    // version alpha beta du minimax
    private int[] alphaBeta(Plateau plateau, int profondeur, int alpha, int beta, boolean maximizingPlayer) {
        if (profondeur == 0 || plateau.plateauEstPlein()) {
            int score = 0;
            switch (strategie) {
                case positionnel:
                    score = evaluerPositionnel(plateau);
                    return new int[]{score, -1, -1};
                case absolu:
                    score = evaluerAbsolu(plateau, couleur);
                    return new int[]{score, -1, -1};
                case mobilite:
                    score = evaluerMobilite(plateau, couleur);
                    return new int[]{score, -1, -1};
                case mixte:
                    score = evaluerMixte(plateau, couleur);
                    return new int[]{score, -1, -1};
            }
        }

        List<int[]> coupsPossibles = obtenirCoupsPossibles(plateau, couleur);

        if (maximizingPlayer) {
            int[] meilleurCoup = {-1, -1};
            for (int[] coup : coupsPossibles) {
                Plateau copiePlateau = plateau.clone();
                if (copiePlateau.positionValide(coup[0], coup[1], couleur)) {
                    copiePlateau.placerPion(coup[0], coup[1], couleur);

                    int[] scoreCoup = alphaBeta(copiePlateau, profondeur - 1, alpha, beta, false);
                    if (scoreCoup[0] > alpha) {
                        alpha = scoreCoup[0];
                        meilleurCoup[0] = coup[0];
                        meilleurCoup[1] = coup[1];
                    }
                    if (beta <= alpha) {
                        break; // elagage
                    }
                }
            }
            return meilleurCoup;
        } else {
            int[] pireCoup = {-1, -1};
            for (int[] coup : coupsPossibles) {
                Plateau copiePlateau = plateau.clone();
                Pion.COULEUR adversaire = (couleur == Pion.COULEUR.noir) ? Pion.COULEUR.blanc : Pion.COULEUR.noir;

                if (copiePlateau.positionValide(coup[0], coup[1], adversaire)) {
                    copiePlateau.placerPion(coup[0], coup[1], adversaire);

                    int[] scoreCoup = alphaBeta(copiePlateau, profondeur - 1, alpha, beta, true);
                    if (scoreCoup[0] < beta) {
                        beta = scoreCoup[0];
                        pireCoup[0] = coup[0];
                        pireCoup[1] = coup[1];
                    }
                    if (beta <= alpha) {
                        break; // elagage
                    }
                }
            }
            return pireCoup;
        }
    }

    // version nega max du min max
    private int[] negamax(Plateau plateau, int profondeur, int alpha, int beta, boolean maximizingPlayer) {
        if (profondeur == 0 || plateau.plateauEstPlein()) {
            int score = 0;
            switch (strategie) {
                case positionnel:
                    score = evaluerPositionnel(plateau);
                    return new int[]{score, -1, -1};
                case absolu:
                    score = evaluerAbsolu(plateau, couleur);
                    return new int[]{score, -1, -1};
                case mobilite:
                    score = evaluerMobilite(plateau, couleur);
                    return new int[]{score, -1, -1};
                case mixte:
                    score = evaluerMixte(plateau, couleur);
                    return new int[]{score, -1, -1};
            }
        }

        List<int[]> coupsPossibles = obtenirCoupsPossibles(plateau, couleur);

        int meilleurScore = Integer.MIN_VALUE;
        int[] meilleurCoup = {-1, -1};

        for (int[] coup : coupsPossibles) {
            Plateau copiePlateau = plateau.clone();
            if (copiePlateau.positionValide(coup[0], coup[1], couleur)) {
                copiePlateau.placerPion(coup[0], coup[1], couleur);

                int[] scoreCoup = negamax(copiePlateau, profondeur - 1, -beta, -alpha, !maximizingPlayer);

                int score = -scoreCoup[0]; // changement de signe pour le NegaMax

                if (score > meilleurScore) {
                    meilleurScore = score;
                    meilleurCoup[0] = coup[0];
                    meilleurCoup[1] = coup[1];
                }
                alpha = Math.max(alpha, score);

                if (alpha >= beta) {
                    break; // elagage
                }
            }
        }
        return meilleurCoup;
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

    // strategie d'evaluation positionnelle
    private int evaluerPositionnel(Plateau plateau) {
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

    // strategie d'evaluation absolue
    public int evaluerAbsolu(Plateau plateau, Pion.COULEUR joueur) {
        int score = 0;
        for (int ligne = 0; ligne < plateau.getGrille().size(); ligne++) {
            for (int colonne = 0; colonne < plateau.getGrille().get(0).size(); colonne++) {
                Pion pion = plateau.getGrille().get(ligne).get(colonne);
                if (pion.couleur == joueur) {
                    score++;
                } else if (pion.couleur != Pion.COULEUR.neutre) {
                    score--;
                }
            }
        }
        return score;
    }

    // strategie d'evaluation mobilite
    public int evaluerMobilite(Plateau plateau, Pion.COULEUR joueur) {
        int score = 0;
        int mobiliteJoueur = obtenirCoupsPossibles(plateau, joueur).size();
        int mobiliteAdversaire = obtenirCoupsPossibles(plateau, (joueur == Pion.COULEUR.noir) ? Pion.COULEUR.blanc : Pion.COULEUR.noir).size();

        // Ajoutez des bonus pour la possession des coins du plateau
        int coinsJoueur = obtenirCoinsPossedes(plateau, joueur);
        int coinsAdversaire = obtenirCoinsPossedes(plateau, (joueur == Pion.COULEUR.noir) ? Pion.COULEUR.blanc : Pion.COULEUR.noir);

        score = mobiliteJoueur - mobiliteAdversaire + (coinsJoueur - coinsAdversaire);
        return score;
    }

    // strategie d'evaluation mixte
    public int evaluerMixte(Plateau plateau, Pion.COULEUR joueur) {
        int score = 0;

        // phase de jeu en fonction du nombre de coups joues
        int phase = determinerPhase(nombreCoupJoues);

        // choix de la strategie de jeu elon la phase
        switch (phase) {
            case 1: // debut de partie
                score = evaluerPositionnel(plateau);
                break;
            case 2: // milieu de partie
                score = evaluerMobilite(plateau, joueur);
                break;
            case 3: // fin de partie
                score = evaluerAbsolu(plateau, joueur);
                break;
        }

        return score;
    }

    public int determinerPhase(int coupsJoues) {
        if (coupsJoues < 20) {
            return 1; // début de partie
        } else if (coupsJoues < 35) {
            return 2; // milieu de partie
        } else {
            return 3; // fin de partie
        }
    }

    public int obtenirCoinsPossedes(Plateau plateau, Pion.COULEUR joueur) {
        int coinsPossedes = 0;

        // coin superieur gauche
        if (plateau.getGrille().get(0).get(0).getColor() == joueur) {
            coinsPossedes++;
        }

        // coin superieur droit
        if (plateau.getGrille().get(0).get(plateau.getGrille().get(0).size() - 1).getColor() == joueur) {
            coinsPossedes++;
        }

        // coin inferieur gauche
        if (plateau.getGrille().get(plateau.getGrille().size() - 1).get(0).getColor() == joueur) {
            coinsPossedes++;
        }

        // coin inferieur droit
        if (plateau.getGrille().get(plateau.getGrille().size() - 1).get(plateau.getGrille().get(0).size() - 1).getColor() == joueur) {
            coinsPossedes++;
        }

        return coinsPossedes;
    }


    public Pion.COULEUR getCouleur() {
        return couleur;
    }

    public String getNom() {
        return nom;
    }

    public int getProfondeurMax() {
        return profondeurMax;
    }

    public void setProfondeurMax(int profondeurMax) {
        this.profondeurMax = profondeurMax;
    }

    public STRATEGIE getStrategie() {
        return strategie;
    }

    public void setStrategie(STRATEGIE strategie) {
        this.strategie = strategie;
    }

    public ALGO getAlgo() {
        return algo;
    }

    public void setAlgo(ALGO algo) {
        this.algo = algo;
    }

    public void setNombreCoupJoues(int nombreCoupJoues) {
        this.nombreCoupJoues = nombreCoupJoues;
    }

    @Override
    public String toString() {
        return "IA{" +
                "nom='" + nom + '\'' +
                ", couleur=" + couleur +
                ", profondeurMax=" + profondeurMax +
                ", nombreCoupJoues=" + nombreCoupJoues +
                ", strategie=" + strategie +
                ", algo=" + algo +
                '}';
    }
}
