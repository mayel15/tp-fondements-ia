public class Pion {
    String color;

    public Pion(String color) {
        this.color = color;
    }

    void change_color(String new_color)
    {
        color=new_color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return color;
    }
}
