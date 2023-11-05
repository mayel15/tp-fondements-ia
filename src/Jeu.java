// ALMAJJO Alaaeddin & THIAM Pape

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                checkWin(true);
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

    // jeu entre 2 IA; parametre avecLog pour gerer les affichages
    public Pion.COULEUR IAvsIA(boolean avecLog) {
        int nbToursPasse = 0;

        if (avecLog) {
            System.out.println("Configuration des IAs : ");
            System.out.println(iaBlanc);
            System.out.println(iaNoir);
        }

        while (true) {
            // affichage de la grille
            if(avecLog) System.out.println(plateau);


            // timeout
            if(avecLog) this.timeout();

            // verification si le plateau est plein
            if (plateau.plateauEstPlein() || nbToursPasse == 2) {
                //if (!(avecLog)) System.out.println(plateau);
                return checkWin(avecLog);
            } else if (plateau.estPossibleDeJouer(joueurActuel.getCouleur())) {
                iaActuel.jouerCoup(plateau, avecLog);
            } else {
                if(avecLog) System.out.println("Le joueur " + iaActuel.getNom() + " ne peut pas jouer donc passe son tour !");
                nbToursPasse++;
            }

            // au tour de l'adversaire de joueur
            iaActuel = (iaActuel == iaNoir) ? iaBlanc : iaNoir;
        }

    }

    public void statsIAvsIA() {
        int nbMatchs = 0;

        IA.ALGO[] algos = new IA.ALGO[] {IA.ALGO.minmax, IA.ALGO.alphaBeta, IA.ALGO.negamax };
        IA.STRATEGIE[] strategies = new IA.STRATEGIE[] {IA.STRATEGIE.positionnel, IA.STRATEGIE.absolu, IA.STRATEGIE.mobilite, IA.STRATEGIE.mixte };

        Map<IA.ALGO, Integer> scoresAlgo = new HashMap<>();
        Map<IA.STRATEGIE, Integer> scoresStrategie = new HashMap<>();

        for (IA.ALGO algo : algos) {
            scoresAlgo.put(algo, 0);
        }

        for (IA.STRATEGIE strategie : strategies) {
            scoresStrategie.put(strategie, 0);
        }

        for (IA.ALGO algo1 : algos) {
            for (IA.STRATEGIE strategie1 : strategies) {

                    iaBlanc.setAlgo(algo1);
                    iaBlanc.setStrategie(strategie1);
                    iaBlanc.setNombreCoupJoues(0);

                    for (IA.ALGO algo2 : algos) {
                        for (IA.STRATEGIE strategie2 : strategies) {

                                if (!(algo1 == algo2 && strategie1 == strategie2)) {
                                    iaNoir.setAlgo(algo2);
                                    iaNoir.setStrategie(strategie2);
                                    iaNoir.setNombreCoupJoues(0);

                                    Pion.COULEUR gagnant = this.IAvsIA(false);
                                    if (gagnant == Pion.COULEUR.blanc) {
                                        int n = scoresAlgo.get(iaBlanc.getAlgo());
                                        scoresAlgo.put(iaBlanc.getAlgo(), n + 1);
                                        int l = scoresStrategie.get(iaBlanc.getStrategie());
                                        scoresStrategie.put(iaBlanc.getStrategie(), l + 1);
                                    } else if (gagnant == Pion.COULEUR.noir) {
                                        int n = scoresAlgo.get(iaNoir.getAlgo());
                                        scoresAlgo.put(iaNoir.getAlgo(), n + 1);
                                        int l = scoresStrategie.get(iaNoir.getStrategie());
                                        scoresStrategie.put(iaNoir.getStrategie(), l + 1);
                                    }

                                    plateau.initialiserGrille(8);

                                    nbMatchs++;
                                }
                        }
                    }

            }
        }
        System.out.println("Statistiques [ (key=value) ; key -> parametre : value -> nbVictoire ]");
        System.out.println("Nombre total de matchs : " + nbMatchs);
        System.out.println("Algos : " + scoresAlgo);
        System.out.println("Strategies : " + scoresStrategie);

    }




    // le timeout
    public void timeout() {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // jeu entre un joueur et un IA
    public void JoueurVSIA() {
        int tourDeJeu = 0;
        System.out.println("Configuration de l'IA : ");
        System.out.println(iaBlanc);
        while (true) {
            // affichage de la grille
            System.out.println(plateau);

            // verification si le plateau est plein
            if (plateau.plateauEstPlein()) {
                checkWin(true);
                break;
            } else if (tourDeJeu == 0 && plateau.estPossibleDeJouer(joueurNoir.getCouleur())) {
                joueurNoir.jouerCoup(plateau);
                tourDeJeu = 1;

            } else if (tourDeJeu == 1 && plateau.estPossibleDeJouer(iaBlanc.getCouleur() )) {
                iaBlanc.jouerCoup(plateau, true);
                tourDeJeu = 0;
            } else {
                System.out.println("Le " + joueurActuel.getNom() + " ne peut pas jouer donc passe son tour !");
                tourDeJeu = (tourDeJeu == 0) ? 1 : 0;
            }

        }
    }


    // permet de verifier si c'est gagne; parametre avecLog pour gerer les affichages
    public Pion.COULEUR checkWin(boolean avecLog) {
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
            if(avecLog) System.out.println("Joueur noir a gagné : " + pionsNoirs + " à " + pionsBlancs );
            return Pion.COULEUR.noir;
        } else if (pionsBlancs > pionsNoirs) {
            if(avecLog) System.out.println("Joueur blanc a gagné : " + pionsBlancs + " à " + pionsNoirs );
            return Pion.COULEUR.blanc;
        } else {
            if(avecLog) System.out.println("Match nul : " + pionsNoirs + " Partout !" );
            return Pion.COULEUR.neutre;
        }
    }

}