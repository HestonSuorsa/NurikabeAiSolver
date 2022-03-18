import java.util.ArrayList;

public class DFS_NurikabeAI {
    ArrayList<OriginIsland> islands = new ArrayList<>();
    static int height, width;

    public static void main() {
        // TODO: run logic
        Board start = new Board(height, width);
        DFS(start, 0, 0);
    }

    public DFS_NurikabeAI(String content) {
        // TODO: Parse content to get board dimensions
        // TODO: Parse content to get origin island locations
    }

    public static void DFS(Board b, int curRow, int curCol) {
        if (isLastIndex(curRow,curCol)) {
            Board lastWater = b.clone().drawWater(curRow,curCol);
            if (isGoal(lastWater, curRow, curCol)) {
                System.out.println(lastWater.toString());
                return;
            }
            Board lastLand = b.clone().drawWater(curRow,curCol);
            if (isGoal(lastLand, curRow, curCol)) {
                System.out.println(lastLand.toString());
                return;
            }
            System.out.println("No solution found");
            return;
        }
        if (isGoal(b,curRow,curCol)) {
            System.out.println(b.toString());
            return;
        }
        if (!isValid(b)) { return; }
        int nextRow = curRow + 1 % width;
        int nextCol = curCol;
        if (curRow == width-1) nextCol++;
        DFS(b.clone().drawWater(curRow, curCol), nextRow, nextCol);
        DFS(b.clone().drawLand(curRow, curCol), nextRow, nextCol);
    }
    /**
     * Returns true if the current Board does not
     * violate any constraints else false
     * @return Boolean
     */
    public static Boolean isValid(Board b) {
        return lakeExists(b) && pondExists(b) && checkIslands(b);
    }
    public static Boolean isGoal(Board b, int curRow, int curCol) {
        return isLastIndex(curRow,curCol) && isValid(b);
    }
    public static Boolean isLastIndex(int curRow, int curCol) {
        return curCol == height && curRow == width;
    }
    public static Boolean lakeExists(Board b) {
        // TODO: Check if any lakes create 2x2
        return false;
    }
    public static Boolean pondExists(Board b) {
        // TODO: Make sure water can be connected
        return false;
    }
    public static Boolean checkIslands(Board b) {
        // TODO: Check if islands are no more than their max number
        return false;
    }
}
