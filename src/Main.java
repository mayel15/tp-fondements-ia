import java.util.ArrayList;

public class Main  {

    public static void main(String[] args)
    {
        ArrayList<ArrayList<Pion>> grid = new ArrayList<>(8);

        for (int i = 0; i < 8; i++) {
            ArrayList<Pion> row = new ArrayList<Pion>(8);
            for (int j = 0; j < 8; j++) {
                row.add(new Pion("sans couleur"));
            }
            grid.add(row);
        }

        grid.get(0).get(0).change_color("rouge");
        grid.get(0).get(1).change_color("Bleu");


        System.out.println(grid.get(7).get(7));

    }
}
