import java.util.Comparator;

/**
 * Sorted by descending order by size
 */
public class SortBySize implements Comparator<Cell> {
    public int compare(Cell o1, Cell o2) {
        return o2.getIslandSize() - o1.getIslandSize();
    }
}