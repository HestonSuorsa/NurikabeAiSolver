import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class BoardDFS {
    Board gameBoard;
    int height, width, numIslands;
    ArrayList<Cell> originIslands;
    String endBoard;
    int nodesVisited;

    public BoardDFS(Board board, int numIslands) {
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
        nodesVisited = 0;
    }

    public String run() {
        gameBoard.resetBoard();
        if (DFS(gameBoard, 0, 0)) return endBoard;
        else return "No solution found";
    }

    public boolean DFS(Board b, int curRow, int curCol) {
        Cell curCell = b.getCell(curRow,curCol);
        nodesVisited++;

        // Goal State check (on last index)
        if (isLastIndex(curRow,curCol)) {
            if (isGoal(b,curRow,curCol)) {
                endBoard = b.toString(); // Found solution
                return true;
            }
            if (!curCell.getIsOrigin()) b.drawWater(curRow, curCol);
            return false; // Invalid solution
        }

        // Continue if not on last index

        int nextCol = (curCol+1) % width;
        int nextRow = curRow;
        if (curCol == width-1) nextRow++;

        if (!curCell.getIsOrigin() && isValid(b.drawWater(curRow,curCol), curRow, curCol)) {
            if (DFS(b, nextRow, nextCol)) return true;
        }

        if(isValid(b.drawLand(curRow,curCol), curRow, curCol)) {
            if (DFS(b, nextRow, nextCol)) return true;
        }

        b.drawWater(curRow, curCol);

        // At this point, backtrack
        return false;
    }
    /**
     * Returns true if the current Board does not
     * violate any constraints else false
     * @return Boolean
     */
    public boolean isValid(Board b, int curRow, int curCol) {
        return !lakeExists(b,curRow,curCol) && !pondExists(b) && checkIslands(b);
    }

    /**
     * Does all that isValid does but also make sure there are no
     * straggling islands
     * @param b
     * @param curRow
     * @param curCol
     * @return
     */
    public boolean isValidEndGoal(Board b, int curRow, int curCol) {
        return !lakeExists(b,curRow,curCol) && !pondExists(b) && verifyIslands(b);
    }

    public boolean isLastIndex(int r, int c) {
        return r == height-1 && c == width-1;
    }

    public boolean isGoal(Board b, int r, int c) {
        Cell curCell = b.getCell(r,c);
        if (!curCell.getIsOrigin() && isValidEndGoal(b.drawWater(r,c),r,c)) return true;
        if (isValidEndGoal(b.drawLand(r,c),r,c)) return true;
        return false; // Invalid solution
    }

    public Boolean lakeExists(Board b, int r, int c) {
        if (b.getCell(r,c).getIsLand() || r == 0 || c == 0) return false;
        // Return false if drew land, or if impossible for lake to have been made
        else { // Cell is water (check cells that for 2x2 w/ this as right lower corner)
            return b.getCell(r,c-1).getIsWater() && b.getCell(r-1,c-1).getIsWater()
                    && b.getCell(r-1,c).getIsWater();
        }
    }
    public Boolean pondExists(Board b) {
        //Make sure that there is only one pond in the board
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
        Set<Cell> lands = new HashSet<>();
        for (Cell o : originIslands) {
            findConnectedLand(o, lands, b);
            if (lands.size() > o.getIslandSize() || !onlyOneOrigin(lands)) return false;
            lands.clear();
        }
        return true;
    }

    public boolean onlyOneOrigin(Set<Cell> lands) {
        int count = 0;
        for (Cell land : lands) {
            if (land.getIsOrigin()) count++;
        }
        return count == 1;
    }

public boolean verifyIslands(Board board) {
    int totalLands = 0;

    for(int r=0; r<height; r++) {
        for(int c=0; c<width; c++) {
            if(board.getCell(r,c).getIsLand()) {
                totalLands++;
            }
        }
    }

    int accountedFor = 0;

    Set<Cell> lands = new HashSet<>();
    for (Cell o : originIslands) {
        findConnectedLand(o, lands, board);
        if (lands.size() != o.getIslandSize() || !onlyOneOrigin(lands)) return false;
        accountedFor += lands.size();
        lands.clear();
    }
    return (accountedFor == totalLands);
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
                findConnectedLand(neighbor, result, b);
            }
        }
    }
}
