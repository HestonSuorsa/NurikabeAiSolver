// Based off of this pseudocode
// https://www.researchgate.net/publication/333755704_Solving_Nurikabe_with_Ant_Colony_Optimization_Extended_version
import java.util.*;

public class AntColony {
    Board gameBoard;
    int height, width, numIslands;
    ArrayList<Cell> originIslands;
    String endBoard;
    long nodesVisited;

    public AntColony(Board board, int numIslands) {
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
        originIslands.sort(new SortBySize());
        nodesVisited = 0;
    }

    public String run() {

        if (antColonySolver(gameBoard))
            return endBoard;
        return "No solution found";
    }

    public boolean antColonySolver(Board b) {

        boolean valid = false;
        boolean badMoveMade;
        while(!valid) {
            //TODO: Check if all origin islands are off that stack
            b.resetBoard();
            for(Cell currOriginIsland : originIslands) {
                b.drawLand(currOriginIsland.getRow(), currOriginIsland.getCol());
            }
            badMoveMade=false;

            for(Cell currOriginIsland : originIslands) {
                Set<Cell> exploredCells = new HashSet<Cell>();
                ArrayList<Cell> frontierCells = new ArrayList<>();
                frontierCells.add(currOriginIsland);
                Cell temp = currOriginIsland;
                int currIslandSize = 1;
                while (currIslandSize < currOriginIsland.getIslandSize())  {
                    boolean waterFound = false;
                    while(!waterFound) {

                        ArrayList<Cell> potentialLandNeighbors = getPotentialLandNeighbors(b,temp);

                        int randNum=0;
                        if(potentialLandNeighbors.size()>0) {
                            Random rn1 = new Random();
                            randNum = rn1.nextInt(potentialLandNeighbors.size());
                        }
                        for(int i=0; i<potentialLandNeighbors.size(); i++) {

                            Cell cell = potentialLandNeighbors.get((randNum+i) % potentialLandNeighbors.size());
                            if (cell != null && cell.getIsWater()) {
                                waterFound = true;
                                cell.setIsLand();
                                frontierCells.add(cell);
                                break;
                            }
                        }

                        if(!waterFound) {
                            frontierCells.remove(temp);
                            exploredCells.add(temp);
                        }
                        if(frontierCells.size() > 0) {
                            Random rn2 = new Random();
                            randNum = rn2.nextInt(frontierCells.size());
                            temp = frontierCells.get(randNum);
                        }
                        if(frontierCells.size() ==0) {
                            badMoveMade=true;
                            break;
                        }

                    }
                    if(badMoveMade) { break; }
                    Set<Cell> lands = new HashSet<>();
                    findConnectedLand(currOriginIsland, lands, b);
                    currIslandSize = lands.size();
                }
                if(badMoveMade) { break; }
            }
            if(badMoveMade) {
                nodesVisited++;
                continue;
            }
            if(!pondExists(b) && !lakeExists(b) && checkIslands(b)) {
                nodesVisited++;
                valid=true;
            }
        }
        endBoard = b.toString();
        return true;
    }

    private Cell getLeftNeighbor(Board b, int r, int c) {
        return b.getCell(r,c-1);
    }
    private Cell getTopNeighbor(Board b, int r, int c) {
        return b.getCell(r+1,c);
    }
    private Cell getRightNeighbor(Board b, int r, int c) {
        return b.getCell(r,c+1);
    }
    private Cell getBottomNeighbor(Board b, int r, int c) {
        return b.getCell(r-1,c);
    }

    private ArrayList<Cell> getPotentialLandNeighbors(Board b, Cell currOriginIsland) {
        int currIslandRow = currOriginIsland.getRow();
        int currIslandCol = currOriginIsland.getCol();

        ArrayList<Cell> potentialLandNeighbors = new ArrayList<Cell>();
        potentialLandNeighbors.add(getLeftNeighbor(b, currIslandRow, currIslandCol));
        potentialLandNeighbors.add(getTopNeighbor(b, currIslandRow, currIslandCol));
        potentialLandNeighbors.add(getRightNeighbor(b, currIslandRow, currIslandCol));
        potentialLandNeighbors.add(getBottomNeighbor(b, currIslandRow, currIslandCol));

        Set<Cell> rejectSet = new HashSet<>();
        for(Cell potentialLandNeighbor : potentialLandNeighbors) {
            if(potentialLandNeighbor == null) {
                rejectSet.add(potentialLandNeighbor);
                continue;
            }

            ArrayList<Cell> neighborNeighbors = b.getNeighbors(potentialLandNeighbor.getRow(),potentialLandNeighbor.getCol());
            for(Cell cell : neighborNeighbors) {
                if(cell.getIsLand() && cell != currOriginIsland) {
                    rejectSet.add(potentialLandNeighbor);
                    break;
                }
            }
        }

        for(Cell cell: rejectSet) {
            potentialLandNeighbors.remove(cell);
        }

        return potentialLandNeighbors;
    }

    // ------ CHECKER METHODS ------
    public boolean isValidEndGoal(Board b, int curRow, int curCol) {
        return !lakeExists(b,curRow,curCol) && !pondExists(b) && verifyIslands(b);
    }


    public boolean lakeExists(Board b, int r, int c) {
        if (b.getCell(r,c).getIsLand() || r == 0 || c == 0) return false;
            // Return false if drew land, or if impossible for lake to have been made
        else { // Cell is water (check cells that for 2x2 w/ this as right lower corner)
            return b.getCell(r,c-1).getIsWater() && b.getCell(r-1,c-1).getIsWater()
                    && b.getCell(r-1,c).getIsWater();
        }
    }

    public boolean lakeExists(Board b) {
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


    public boolean pondExists(Board b) {
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


    public boolean checkIslands(Board b) {
        Set<Cell> lands = new HashSet<>();
        for (Cell o : originIslands) {
            findConnectedLand(o, lands, b);
            if (lands.size() > o.getIslandSize()) return false;
            lands.clear();
        }
        return true;
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
            if (lands.size() != o.getIslandSize()) return false;
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
