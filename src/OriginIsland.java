import java.util.Comparator;

public class OriginIsland {
    public int size, row, col;

    public OriginIsland(int size, int row, int col) {
        this.size = size;
        this.row = row;
        this.col = col;
    }

    public String toString() {
        return "[" + size + "]@(" + row + "," + col + ")";
    }
}
