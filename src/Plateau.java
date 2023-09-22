import java.util.ArrayList;
import java.util.List;

public class Plateau {
    ArrayList<ArrayList<Pion>> grid;

    public Plateau(ArrayList<ArrayList<Pion>> grid)
    {
        this.grid = grid;
    }

    @Override
    public String toString() {
        return "Plateau{" +
                "grid=" + grid +
                '}';
    }
}
