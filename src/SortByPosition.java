import java.util.Comparator;

/**
 * Sorted by descending order by position
 */
public class SortByPosition implements Comparator<OriginIsland> {
    public int compare(OriginIsland o1, OriginIsland o2) {
        int result = o2.row - o1.row;
        if (result == 0) result = o2.col - o1.col;
        return result;
    }
}