import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class IslandDFS {
    Board gameBoard;
    int height, width, numIslands;
    ArrayList<Cell> originIslands;
    Set<Cell>[] restricted;
    String endBoard;
    int nodesVisited;

    public IslandDFS(Board board, int numIslands) {
        this.gameBoard = board;
        this.height = board.height;
        this.width = board.width;
        this.numIslands = numIslands;
        this.originIslands = new ArrayList<>(numIslands);
        this.restricted = new HashSet[numIslands];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                Cell cell = board.getCell(r,c);
                if (cell.getIsOrigin())
                    originIslands.add(cell);
            }
        }
        this.endBoard = board.toString();

        originIslands.sort(new SortBySize());

        for (int i = 0; i < numIslands; i++) {
            Set<Cell> restrictedGroup = new HashSet<>();
            Cell o = originIslands.get(i);
            restrictedGroup.add(o);
            for (Cell neighbor : board.getNeighbors(o.getRow(),o.getCol())) {
                restrictedGroup.add(neighbor);
            }
            restricted[i] = restrictedGroup;
        }
        nodesVisited = 0;
    }

    public String run() {
        Cell first = originIslands.get(0);
        if (IHDFS(gameBoard, 0,1, first.getRow(), first.getCol())) return endBoard;
        else return "No solution found";
    }

    public boolean IHDFS(Board b, int i, int curSize, int curRow, int curCol) {
//        System.out.println(b);
//        System.out.println("Current: " + curRow + "," + curCol); // TODO: REMOVE
        nodesVisited++;
        Cell origin = originIslands.get(i);
        Set<Cell> restrictedGroup = restricted[i];

//        System.out.println("Origin Restriciton group: " + i);
//        for (Cell c : restrictedGroup) { // TODO: REMOVE
//            System.out.println("("+c.getRow()+","+c.getCol()+")");
//        }

        if (curSize == origin.getIslandSize()) {
//            System.out.println("Island is of size"); // TODO: REMOVE
            if (i == originIslands.size()-1) {
//                System.out.println("LAST ISLAND!"); // TODO: REMOVE
                if (isValidEndGoal(b)) {
                    endBoard = b.toString();
                    return true;
                }
                return false;
            }
            Cell nextOrigin = originIslands.get(i+1);
//            System.out.println("Going to next island at " + nextOrigin.getRow() +
//                    "," + nextOrigin.getCol()); // TODO: REMOVE
            i++;
//            System.out.println("Next group: "+ i); // TODO: REMOVE
            return IHDFS(b, i, 1, nextOrigin.getRow(), nextOrigin.getCol());
        }
//        System.out.println("Growing island " + curRow + "," + curCol + " to " +
//                " size " + (curSize+1)); // TODO: REMOVE

        for (Cell n : b.getNeighbors(curRow,curCol)) {
            restrictedGroup.add(n);
            if (n.getIsWater() && !isRestricted(n,i)) {
                n.setIsLand();
                ArrayList<Cell> nNeighbors = b.getNeighbors(n.getRow(),n.getCol());
                ArrayList<Cell> newNeighbors = new ArrayList<>();

                for (Cell cell : nNeighbors) {
                    if (!restrictedGroup.contains(cell)) newNeighbors.add(cell);
                }
                restrictedGroup.addAll(newNeighbors);

                curSize++;
                if (IHDFS(b,i,curSize,n.getRow(),n.getCol())) return true;
                n.setIsWater();
                curSize--;
                restrictedGroup.removeAll(newNeighbors);
            }
        }
        return false;
    }

    public boolean isRestricted(Cell cell, int i) {
        //System.out.println("Num island: " + i);
        for (int j = 0; j < numIslands; j++) {
            if (j!=i && restricted[j].contains(cell)) {
                //System.out.println("Restricted: " + cell.getRow() + "," + cell.getCol());
                return true;
            }
        }
        return false;
    }

    public Boolean isValidEndGoal(Board b) {
        return !lakeExists(b) && !pondExists(b)/* && verifyIslands(b)*/;
    }

    public Boolean pondExists(Board b) {
        // TODO: Make sure water can be connected
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

    public boolean onlyOneOrigin(Set<Cell> lands) {
        int count = 0;
        for (Cell land : lands) {
            if (land.getIsOrigin()) count++;
        }
        return count == 1;
    }

    public Boolean lakeExists(Board b) {
        Cell temp;
        for(int r=0; r<b.height; r++) {
            for(int c=0; c<b.width; c++) {
                temp = b.getCell(r,c);
                Cell right = getRightNeighbor(b,r,c);
                if(temp.getIsWater() && right != null && right.getIsWater()) {
                    Cell bottom = getBottomNeighbor(b,r,c);
                    Cell diagonal = getBottomNeighbor(b,right.getRow(),right.getCol());

                    if(bottom != null && diagonal != null && bottom.getIsWater() && diagonal.getIsWater()) {
                        //Pond found!
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Cell getRightNeighbor(Board b, int r, int c) {
        return b.getCell(r,c+1);
    }
    private Cell getBottomNeighbor(Board b, int r, int c) {
        return b.getCell(r-1,c);
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
