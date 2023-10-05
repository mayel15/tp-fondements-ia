public class Pion {

    enum COULEUR{
        noir,
        blanc,
        neutre
    }
    COULEUR couleur;
    int posX;
    int posY;

    public Pion(COULEUR couleur, int posX, int posY) {
        this.couleur = couleur;
        this.posX = posX;
        this.posY = posY;
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

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
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
