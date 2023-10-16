public class Joueur {
    private String nom;
    private Pion.COULEUR couleur;

    public Joueur(String nom, Pion.COULEUR couleur) {
        this.nom = nom;
        this.couleur = couleur;
    }

    public String getNom() {
        return nom;
    }

    public Pion.COULEUR getCouleur() {
        return couleur;
    }
}
