import java.util.ArrayList;
import java.util.List;

public class Plateau {

    ArrayList<ArrayList<Pion>> grille;
    public Plateau(int dimension)
    {
        //ce block est pour initialiser la grille
        this.grille = new ArrayList<>();
        for (int i = 0; i < dimension; i++) {
            ArrayList<Pion> ligne = new ArrayList<>();
            for (int j = 0; j < dimension; j++) {
                ligne.add(new Pion(Pion.COULEUR.neutre, i, j));
            }
            grille.add(ligne);
        }
        //ce block est pour positionner les pions de départ au centre
        int centre = (dimension/2)-1;
        this.grille.get(centre).get(centre).setCouleur(Pion.COULEUR.noir);
        this.grille.get(centre).get(centre+1).setCouleur(Pion.COULEUR.blanc);
        this.grille.get(centre+1).get(centre).setCouleur(Pion.COULEUR.blanc);
        this.grille.get(centre+1).get(centre+1).setCouleur(Pion.COULEUR.noir);
    }



    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (ArrayList<Pion> ligne : grille) {
            for (Pion pion : ligne) {
                builder.append(pion).append("\t"); // Utilisez la méthode toString() de Pion
            }
            builder.append("\n"); // Passage à la ligne pour la prochaine ligne de la grille
        }
        return builder.toString();
    }
}
