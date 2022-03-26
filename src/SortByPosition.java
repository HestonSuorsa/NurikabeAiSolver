import java.util.Comparator;

/**
 * Sorted by descending order by position
 */
public class SortByPosition implements Comparator<Cell> {
    public int compare(Cell o1, Cell o2) {
        int result = o2.getRow() - o1.getRow();
        if (result == 0) result = o2.getCol() - o1.getCol();
        return result;
    }
}