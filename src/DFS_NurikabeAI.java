import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DFS_NurikabeAI {
    Board gameBoard;
    int height, width, numIslands;
    ArrayList<Cell> originIslands;
    String endBoard;

    public DFS_NurikabeAI(Board board, int numIslands) {
        this.gameBoard = board;
        this.height = board.height;
        this.width = board.width;
        this.numIslands = numIslands;
        this.originIslands = new ArrayList<>(numIslands);
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (board.getCell(r,c).getIsOrigin())
                    originIslands.add(board.getCell(r,c));
            }
        }
        this.endBoard = board.toString();
        originIslands.sort(new SortByPosition());
    }

    public String run() {
        if (DFS(gameBoard, 0, 0)) return endBoard;
        else return "No solution found";
    }

    public boolean DFS(Board b, int curRow, int curCol) {
        Cell curCell = b.getCell(curRow,curCol);

        // Goal State check (on last index)
        if (isLastIndex(curRow,curCol)) {
            if (!curCell.getIsOrigin() && isValid(b.drawWater(b.width-1,b.height-1),curRow,curCol)) {
                endBoard = b.toString(); // Found solution
                return true;
            }
            if (isValid(b.drawLand(b.width-1,b.height-1),curRow,curCol)) {
                endBoard = b.toString(); // Found solution
                return true;
            }
            return false; // No solution found
        }
        // Continue if not on last index

        int nextCol = (curCol+1) % width;
        int nextRow = curRow;
        if (curCol == width-1) nextRow++;
        Cell nextCell = b.getCell(nextRow,nextCol);

        System.out.println("Before validation:\n" + b); // <=== TODO: Test tool

        if (!isValid(b,curRow,curCol)) {
            // Reset to water no matter what
            if (!curCell.getIsOrigin()) curCell.setIsWater();
            System.out.println("After validation:\n" + b); // <=== TODO: Test tool
            return false;
        }


        // TODO: Could this work too? v
        // return DFS(b.drawWater(curRow, curCol), nextRow, nextCol) || DFS(b.drawLand(curRow, curCol), nextRow, nextCol)
        if(!curCell.getIsOrigin() && DFS(b.drawWater(curRow, curCol), nextRow, nextCol)) {
            return true;
        }
        else if (DFS(b.drawLand(curRow, curCol), nextRow, nextCol)) {
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
        return !lakeExists(b,curRow,curCol) && !pondExists(b) && checkIslands(b);
    }

    public Boolean isLastIndex(int r, int c) {
        return r == height-1 && c == width-1;
    }

    public Boolean lakeExists(Board b, int r, int c) {
        if (b.getCell(r,c).getIsLand() || r == 0 || c == 0) return false;
        // Return false if drew land, or if impossible for lake to have been made
        else { // Cell is water (check cells that for 2x2 w/ this as right lower corner)
            return b.getCell(r,c-1).getIsWater() && b.getCell(r-1,c-1).getIsWater()
                    && b.getCell(r-1,c-1).getIsWater();
        }
    }
    public Boolean pondExists(Board b) {
        // TODO: Make sure water can be connected
        //Make sure that there is only one pond in the board
//        if(getPonds(b).size() > 1) {
//            return false;
//        }
//        return true;

        Set<Cell> cellsWithWater = new HashSet<>();

        for(int r=0; r<height; r++) {
            for(int c=0; c<width; c++) {
                if(b.getCell(r,c).getIsWater()) {
                    cellsWithWater.add(b.getCell(r, c));
                }
            }
        }

        final Set<Cell> group = new HashSet<>();
        final Cell startCell = cellsWithWater.iterator().next();
        findConnectedWater(startCell, group, b);
        return cellsWithWater.size() > group.size();
    }


    public Boolean checkIslands(Board b) {
        // TODO: Check if islands are no more than their max number
        Set<Cell> lands = new HashSet<>();
        for (Cell o : originIslands) {
            findConnectedLand(o, lands, b);
            if (lands.size() > o.getIslandSize())
                return false;
            lands.clear();
        }
        return true;
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
      findConnectedWater(startCell, group, board);
      result.add(group);

      cellsWithColor.removeAll(group);
    }

    return result;
  }

  private void findConnectedWater(Cell cell, Set<Cell> result, Board b) {
    result.add(cell);

    //Add checking for being next to walls
    ArrayList<Cell> neighbors = b.getNeighbors(cell.getRow(), cell.getCol());
    for(Cell neighbor : neighbors) {
        if(neighbor.getIsWater() && cell.getIsWater() && !result.contains(neighbor)) {
            findConnectedWater(neighbor, result, b);
        }
    }
    }

    private void findConnectedLand(Cell cell, Set<Cell> result, Board b) {
        result.add(cell);

        //Add checking for being next to walls
        ArrayList<Cell> neighbors = b.getNeighbors(cell.getRow(), cell.getCol());
        for(Cell neighbor : neighbors) {
            if(neighbor.getIsLand() && cell.getIsLand() && !result.contains(neighbor)) {
                findConnectedWater(neighbor, result, b);
            }
        }
    }
}
