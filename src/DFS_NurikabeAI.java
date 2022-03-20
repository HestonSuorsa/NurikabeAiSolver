import java.util.ArrayList;
import java.util.Scanner;

public class DFS_NurikabeAI {
    ArrayList<OriginIsland> islands;
    int height, width, numIslands;

    public DFS_NurikabeAI(int height, int width, int numIslands) {
        this.height = height;
        this.width = width;
        this.numIslands = numIslands;
    }

    public void run() {
        // TODO: run logic
        Board start = new Board(height, width);
        DFS(start, 0, 0);
    }

    public void DFS(Board b, int curRow, int curCol) {
        // Goal State check (on last index)
        if (isLastIndex(curRow,curCol)) {
            Board lastWater = b.clone().drawWater(b.width-1,b.height-1);
            if (isValid(lastWater,curRow,curCol)) {
                System.out.println(lastWater.toString(islands));
                return; // Found solution
            }
            Board lastLand = b.clone().drawLand(b.width-1,b.height-1);
            if (isValid(lastLand,curRow,curCol)) {
                System.out.println(lastLand.toString(islands));
                return; // Found solution
            }
            System.out.println("No solution found");
            return; // No solution found
        }
        if (!isValid(b,curRow,curCol)) { return; }
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
    public Boolean isValid(Board b, int curRow, int curCol) {
        return lakeExists(b,curRow,curCol) && pondExists(b,curRow,curCol) && checkIslands(b,curRow,curCol);
    }
    public Boolean isLastIndex(int r, int c) {
        return r == height && c == width;
    }
    public Boolean lakeExists(Board b, int r, int c) {
        // TODO: Check if any lakes create 2x2
        return false;
    }
    public Boolean pondExists(Board b, int r, int c) {
        // TODO: Make sure water can be connected
        return false;
    }
    public Boolean checkIslands(Board b, int r, int c) {
        // TODO: Check if islands are no more than their max number
        return false;
    }
    public String islandsToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Islands: [size]@(row,col)\n");
        for (OriginIsland island: islands) {
            sb.append((island.toString()+"\n"));
        }
        return sb.toString();
    }
}
