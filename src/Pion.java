// ALMAJJO Alaaeddin & THIAM Pape

public class Pion {

    // les couleurs des pions
    enum COULEUR{
        noir,
        blanc,
        neutre
    }
    COULEUR couleur;
    int nbLigne;
    int nbColonne;

    public Pion(COULEUR couleur, int posX, int posY) {
        this.couleur = couleur;
        this.nbLigne = posX;
        this.nbColonne = posY;
    }

    void change_color(COULEUR new_color)
    {
        this.couleur=new_color;
    }

    public COULEUR getColor() {
        return couleur;
    }

    public void setCouleur(COULEUR couleur) {
        this.couleur = couleur;
    }

    public int getNbLigne() {
        return nbLigne;
    }

    public void setNbLigne(int nbLigne) {
        this.nbLigne = nbLigne;
    }

    public int getNbColonne() {
        return nbColonne;
    }

    public void setNbColonne(int nbColonne) {
        this.nbColonne = nbColonne;
    }

    @Override
    public String toString() {
        String couleurStr = "";
        switch (couleur) {
            case noir:
                couleurStr = "N";
                break;
            case blanc:
                couleurStr = "B";
                break;
            case neutre:
                couleurStr = " ";
                break;
        }
        return "[" + couleurStr + "]";
    }
}
