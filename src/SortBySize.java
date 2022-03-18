import java.util.Comparator;

/**
 * Sorted by descending order by size
 */
public class SortBySize implements Comparator<OriginIsland> {
    public int compare(OriginIsland o1, OriginIsland o2) {
        return o2.size - o1.size;
    }
}