import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Jeu {
    private Plateau plateau;
    private Scanner scanner;
    private Joueur joueurNoir;
    private Joueur joueurBlanc;
    private Joueur joueurActuel;

    public Jeu(Plateau plateau, Joueur joueurNoir, Joueur joueurBlanc) {
        this.plateau = plateau;
        this.scanner = new Scanner(System.in);
        this.joueurNoir = joueurNoir;
        this.joueurBlanc = joueurBlanc;
        this.joueurActuel = joueurNoir; // Le joueur noir commence.
    }

    public void jouer() {
        while (true) {
            jouerTour();

            //System.out.println(possibiliteJouer());
            // Si toutes les cases sont remplies, déterminez le gagnant
            //System.out.println(possibiliteJouer());
            if (plateauEstPlein() || possibiliteJouer()==false) {
                System.out.println(plateau);
                checkWin();
                break;
            }

            // Passez au joueur suivant
            joueurActuel = (joueurActuel == joueurNoir) ? joueurBlanc : joueurNoir;
        }
    }


    public void jouerTour() {
        System.out.println(plateau);
        System.out.println("Tour de " + joueurActuel.getNom() + ". Entrez la position (ligne colonne) pour placer votre pion :");
        int ligne = scanner.nextInt();
        int colonne = scanner.nextInt();

        // Vérifier si le placement est possible
        if (checkPossibilite(ligne, colonne, joueurActuel.getCouleur())) {
            placerPion(ligne, colonne, joueurActuel.getCouleur());
        } else {
            System.out.println("Placement invalide, réessayez.");
            jouerTour(); // Redemander au joueur de jouer
        }
    }

    public boolean checkPossibilite(int ligne, int colonne, Pion.COULEUR joueur) {
        Pion pion = plateau.getGrille().get(ligne).get(colonne);
        if (pion.couleur != Pion.COULEUR.neutre) {
            return false; // La case n'est pas vide, le placement est invalide.
        }

        boolean placementValide = false;

        // Huit directions possibles autour du pion
        int[] directionsLigne = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] directionsColonne = { -1, 0, 1, -1, 1, -1, 0, 1 };

        for (int i = 0; i < directionsLigne.length; i++) {
            int dirLigne = directionsLigne[i];
            int dirColonne = directionsColonne[i];

            int x = ligne + dirLigne;
            int y = colonne + dirColonne;

            boolean pionsAChanger = false;
            ArrayList<Pion> pionsAChangerList = new ArrayList<>(); // Pour stocker les pions adverses à changer

            while (x >= 0 && x < plateau.getGrille().size() && y >= 0 && y < plateau.getGrille().get(0).size()) {
                Pion adjacent = plateau.getGrille().get(x).get(y);

                if (adjacent.couleur == joueur) {
                    if (pionsAChanger) {
                        placementValide = true;

                        // Mettre à jour les pions adverses pour les changer en la couleur du joueur actuel
                        for (Pion pionAChanger : pionsAChangerList) {
                            pionAChanger.couleur = joueur;
                        }
                    }
                    break;
                } else if (adjacent.couleur == Pion.COULEUR.neutre) {
                    break;
                } else {
                    pionsAChanger = true;
                    pionsAChangerList.add(adjacent); // Ajouter les pions adverses à la liste
                }

                x += dirLigne;
                y += dirColonne;
            }
        }

        return placementValide;
    }

    public boolean checkPossibilite2(int ligne, int colonne, Pion.COULEUR joueur) {
        Pion pion = plateau.getGrille().get(ligne).get(colonne);
        if (pion.couleur != Pion.COULEUR.neutre) {
            return false; // La case n'est pas vide, le placement est invalide.
        }

        boolean placementValide = false;

        // Huit directions possibles autour du pion
        int[] directionsLigne = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] directionsColonne = { -1, 0, 1, -1, 1, -1, 0, 1 };

        for (int i = 0; i < directionsLigne.length; i++) {
            int dirLigne = directionsLigne[i];
            int dirColonne = directionsColonne[i];

            int x = ligne + dirLigne;
            int y = colonne + dirColonne;

            boolean pionsAChanger = false;
            ArrayList<Pion> pionsAChangerList = new ArrayList<>(); // Pour stocker les pions adverses à changer

            while (x >= 0 && x < plateau.getGrille().size() && y >= 0 && y < plateau.getGrille().get(0).size()) {
                Pion adjacent = plateau.getGrille().get(x).get(y);

                if (adjacent.couleur == joueur) {
                    if (pionsAChanger) {
                        placementValide = true;
                    }
                    break;
                } else if (adjacent.couleur == Pion.COULEUR.neutre) {
                    break;
                } else {
                    pionsAChanger = true;
                    pionsAChangerList.add(adjacent); // Ajouter les pions adverses à la liste
                }

                x += dirLigne;
                y += dirColonne;
            }
        }

        return placementValide;
    }

    public boolean peutJouer(int ligne, int colonne, Pion.COULEUR joueur) {
        Pion pion = plateau.getGrille().get(ligne).get(colonne);
        if (pion.couleur != Pion.COULEUR.neutre) {
            return false; // La case n'est pas vide, le placement est invalide.
        }

        // Huit directions possibles autour du pion
        int[] directionsLigne = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] directionsColonne = { -1, 0, 1, -1, 1, -1, 0, 1 };

        for (int i = 0; i < directionsLigne.length; i++) {
            int dirLigne = directionsLigne[i];
            int dirColonne = directionsColonne[i];

            int x = ligne + dirLigne;
            int y = colonne + dirColonne;

            boolean pionsAChanger = false;
            ArrayList<Pion> pionsAChangerList = new ArrayList<>(); // Pour stocker les pions adverses à changer

            while (x >= 0 && x < plateau.getGrille().size() && y >= 0 && y < plateau.getGrille().get(0).size()) {
                Pion adjacent = plateau.getGrille().get(x).get(y);

                if (adjacent.couleur == joueur) {
                    if (pionsAChanger) {
                        return true; // Le joueur peut jouer dans cette direction
                    }
                    break;
                } else if (adjacent.couleur == Pion.COULEUR.neutre) {
                    break;
                } else {
                    pionsAChanger = true;
                    pionsAChangerList.add(adjacent); // Ajouter les pions adverses à la liste
                }

                x += dirLigne;
                y += dirColonne;
            }
        }

        return false; // Le joueur ne peut pas jouer dans aucune direction
    }



    public boolean possibiliteJouer() {
        boolean peutEtreJoue = false;
        for (int i = 0; i < plateau.getGrille().size(); i++) {
            for (int j = 0; j < plateau.getGrille().size(); j++) {
                System.out.println(i);
                System.out.println(j);
                if (checkPossibilite2(i, j, joueurActuel.getCouleur())) {
                    /*System.out.println(i);
                    System.out.println(j);*/
                    peutEtreJoue = true;
                    System.out.println(peutEtreJoue);
                    break;
                }
            }
            if(peutEtreJoue) break;
        }
        return peutEtreJoue;
    }

    public void placerPion(int ligne, int colonne, Pion.COULEUR joueur) {
        plateau.getGrille().get(ligne).get(colonne).couleur = joueur;
    }

    public boolean plateauEstPlein() {
        // Vérifier si le plateau est rempli (aucune case vide)
        for (int ligne = 0; ligne < plateau.getGrille().size(); ligne++) {
            for (int colonne = 0; colonne < plateau.getGrille().get(0).size(); colonne++) {
                if (plateau.getGrille().get(ligne).get(colonne).couleur == Pion.COULEUR.neutre) {
                    return false;
                }
            }
        }
        return true;
    }


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