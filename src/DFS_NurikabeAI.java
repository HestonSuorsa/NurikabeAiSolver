import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class DFS_NurikabeAI {
    // ArrayList<Cell> cells;
    int height, width, numIslands;
    String endBoard;

    public DFS_NurikabeAI(int height, int width, int numIslands) {
        this.height = height;
        this.width = width;
        this.numIslands = numIslands;
        // this.cells = cells;
        // this.islands = islands;
    }

    public void run() {
        // TODO: run logic
        Board start = new Board(height, width);

        DFS(start, 0, 0);
    }

    public boolean DFS(Board b, int curRow, int curCol) {
        // Goal State check (on last index)
        if (isLastIndex(curRow,curCol)) {
            Board lastWater = b.clone().drawWater(b.width-1,b.height-1);
            if (isValid(lastWater,curRow,curCol)) {
                // System.out.println(lastWater.toString(islands));
                endBoard = lastWater.toString(); // Found solution
                return true;
            }
            Board lastLand = b.clone().drawLand(b.width-1,b.height-1);
            if (isValid(lastLand,curRow,curCol)) {
                // System.out.println(lastLand.toString(islands));
                endBoard = lastLand.toString(); // Found solution
                return true;
            }
            // System.out.println("No solution found");
            return false; // No solution found
        }
        if (!isValid(b,curRow,curCol)) { return false; }
        //Causes out of bounds error - we never stop incrememnting
        int nextRow = curRow + 1 % width; //shouldn't this be height?
        int nextCol = curCol;
        if (curRow == width-1) nextCol++;

        boolean ret;
        if(DFS(b.clone().drawWater(curRow, curCol), nextRow, nextCol)) {
            return true;
        }
        else if (DFS(b.clone().drawLand(curRow, curCol), nextRow, nextCol)) {
            return true;
        }
        return false;
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
        //Make sure that there is only one pond in the board
        if(getPonds(b).size() > 1) {
            return false;
        }
        return true;

        //Begin logic for finding at most one black group


    }


    public Boolean checkIslands(Board b, int r, int c) {
        // TODO: Check if islands are no more than their max number
        return false;
    }

    //Commented out bc i wasnt sure what this should be doing since this class no longer holds originIsland array
    // public String islandsToString() {
    //     StringBuilder sb = new StringBuilder();
    //     sb.append("Islands: [size]@(row,col)\n");
    //     for (int r=0; r<height; r++) {
    //         for(int c=0; c<width; c++) {
    //             if(board.getCell(r,c).getIsOrigin()) {
    //                 sb.append((island.toString()+"\n"));
    //             }
    //         }
    //     }
    //     return sb.toString();
    // }




/**
 * Will return a set of set of connected cells. 
 * @param board
 * @return
 */
public Set<Set<Cell>> getPonds(Board board) {
    Set<Cell> cellsWithColor = new HashSet<>();

    for(int r=0; r<height; r++) {
        for(int c=0; c<width; c++) {
            if(board.getCell(r,c).getIsWater()) {
                cellsWithColor.add(board.getCell(r, c));
            }
        }
    }

    final Set<Set<Cell>> result = new HashSet<>();
    while (!cellsWithColor.isEmpty()) {
      final Set<Cell> group = new HashSet<Cell>();
      final Cell startCell = cellsWithColor.iterator().next();
      findConnectedCells(startCell, group, board);
      result.add(group);

      cellsWithColor.removeAll(group);
    }

    return result;
  }

  private void findConnectedCells(Cell cell, Set<Cell> result, Board b) {
    result.add(cell);

    //Add checking for being next to walls
    ArrayList<Cell> neighbors = b.getNeighbors(cell.getRow(), cell.getCol());
    for(Cell neighbor : neighbors) {
        if(neighbor.getIsWater() && cell.getIsWater() && !result.contains(neighbor)) {
            findConnectedCells(neighbor, result, b);
        }
    }
    }
}
