import java.util.Comparator;

/**
 * Sorted by ascending order by size
 */
public class SortBySize implements Comparator<Cell> {
    public int compare(Cell o1, Cell o2) {
        return o1.getIslandSize() - o2.getIslandSize();
    }
}