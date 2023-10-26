import java.util.ArrayList;


public class Plateau {

    ArrayList<ArrayList<Pion>> grille;
    public Plateau(int dimension)
    {
        // permet d'initialiser la grille qu'avec des pions neutres
        this.grille = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            ArrayList<Pion> ligne = new ArrayList<>();
            for (int j = 0; j < dimension; j++) {
                ligne.add(new Pion(Pion.COULEUR.neutre, i, j));
            }
            grille.add(ligne);
        }

        // positionner les pions de depart
        int centre = (dimension/2)-1;
        this.grille.get(centre).get(centre).setCouleur(Pion.COULEUR.noir);
        this.grille.get(centre).get(centre+1).setCouleur(Pion.COULEUR.blanc);
        this.grille.get(centre+1).get(centre).setCouleur(Pion.COULEUR.blanc);
        this.grille.get(centre+1).get(centre+1).setCouleur(Pion.COULEUR.noir);
    }

    public ArrayList<ArrayList<Pion>> getGrille() {
        return grille;
    }

    /*
        permet de verifier si le plateau est plein ou pas
     */
    public boolean plateauEstPlein() {
        for (int ligne = 0; ligne < grille.size(); ligne++) {
            for (int colonne = 0; colonne < grille.get(0).size(); colonne++) {
                if (grille.get(ligne).get(colonne).couleur == Pion.COULEUR.neutre) {
                    return false;
                }
            }
        }
        return true;
    }

    // permet de placer un pion sur le plateau après avoir checker la validité de la position
    public void placerPion(int ligne, int colonne, Pion.COULEUR couleurPion) {
        if((positionValide(ligne, colonne, couleurPion)) && (changePionAdverse(ligne,colonne,couleurPion))) {
            grille.get(ligne).get(colonne).couleur = couleurPion;

        } else {
            System.out.println("Position invalide !");
        }
    }

    public boolean changePionAdverse(int ligne, int colonne, Pion.COULEUR joueur) {
        Pion pion = grille.get(ligne).get(colonne);
        if (pion.couleur != Pion.COULEUR.neutre) {
            return false;
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

            while (x >= 0 && x < grille.size() && y >= 0 && y < grille.get(0).size()) {
                Pion adjacent = grille.get(x).get(y);

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


    // permet de verifier si une position est valide pour un joueur (ligne, colonne)
    public boolean positionValide(int ligne, int colonne, Pion.COULEUR joueur) {
        // Vérifiez d'abord si la case est déjà occupée.
        if (grille.get(ligne).get(colonne).couleur != Pion.COULEUR.neutre) {
            return false; // La case est déjà occupée, le coup n'est pas valide.
        }

        Pion.COULEUR adversaire = (joueur == Pion.COULEUR.noir) ? Pion.COULEUR.blanc : Pion.COULEUR.noir;

        // 8 directions possibles autour du pion
        int[] directionsLigne = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] directionsColonne = { -1, 0, 1, -1, 1, -1, 0, 1 };

        boolean coupValideDansCetteDirection = false;

        // Vérifiez dans toutes les directions si un coup est valide.
        for (int i = 0; i < directionsLigne.length; i++) {
            int dirLigne = directionsLigne[i];
            int dirColonne = directionsColonne[i];

            int x = ligne + dirLigne;
            int y = colonne + dirColonne;

            boolean pionsAChanger = false;

            while (x >= 0 && x < grille.size() && y >= 0 && y < grille.get(0).size()) {
                Pion adjacent = grille.get(x).get(y);

                if (adjacent.couleur == adversaire) {
                    pionsAChanger = true;
                } else if (adjacent.couleur == joueur) {
                    // Le placement est valide dans cette direction si au moins un pion de l'adversaire est entouré.
                    if (pionsAChanger) {
                        coupValideDansCetteDirection = true;
                    }
                    break;
                } else {
                    break;
                }

                x += dirLigne;
                y += dirColonne;
            }
        }

        return coupValideDansCetteDirection;
    }


    public boolean estPossibleDeJouer(Pion.COULEUR couleur) {
        for (int ligne=0; ligne<grille.size(); ligne++) {
            for (int colonne=0; colonne<grille.get(0).size(); colonne++) {
                if(positionValide(ligne, colonne, couleur)){
                    return true;
                }
            }
        }
        return false;
    }


    // permet de faire le clone d'un plateau
    public Plateau clone() {
        // permet d'initialiser d'abord le clone avec des pions neutres
        Plateau clonePlateau = new Plateau(grille.size());
        // fais la copie
        for (int ligne = 0; ligne < grille.size(); ligne++) {
            for (int colonne = 0; colonne < grille.get(0).size(); colonne++) {
                clonePlateau.getGrille().get(ligne).get(colonne).couleur = grille.get(ligne).get(colonne).couleur;
            }
        }
        return clonePlateau;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (ArrayList<Pion> ligne : grille) {
            for (Pion pion : ligne) {
                builder.append(pion).append("\t");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
