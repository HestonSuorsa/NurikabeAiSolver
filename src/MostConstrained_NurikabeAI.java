import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class MostConstrained_NurikabeAI {
    Board gameBoard;
    int height, width, numIslands;
    ArrayList<Cell> originIslands;
    String endBoard;
    Stack<Cell> moves = new Stack<Cell>();
    Stack<Cell> originIslandsToIsolate = new Stack<Cell>();

    public MostConstrained_NurikabeAI(Board board, int numIslands) {
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
    }



    public String run() {
        //Add all origin islands to the stack to be done
        for(int i=originIslands.size()-1; i>=0; i--) {
            originIslandsToIsolate.push(originIslands.get(i));
        }

        if (MCSolver(gameBoard))
            return endBoard;
        return "No solution found";
    }

    public boolean MCSolver(Board b) {

        //Start with an entirely unknown board

        //Start with an origin island

        boolean valid = false;
        while(!valid) {
            //TODO: Check if all origin islands are off that stack
            for(Cell currOriginIsland : originIslands) {
//            Cell currOriginIsland = originIslandsToIsolate.pop();
//                moves.add(currOriginIsland);
//
//                Cell originNeighbors[] = {
//                        getLeftNeighbor(b, currIslandRow, currIslandCol),
//                        getTopNeighbor(b, currIslandRow, currIslandCol),
//                        getRightNeighbor(b, currIslandRow, currIslandCol),
//                        getBottomNeighbor(b, currIslandRow, currIslandCol)
//                };


                Set<Cell> lands;
                do  {
                    lands = new HashSet<>();
                    findConnectedLand(currOriginIsland, lands, b);

                    int currIslandRow = currOriginIsland.getRow();
                    int currIslandCol = currOriginIsland.getCol();

                    //add neighbors to list
                    ArrayList<Cell> potentialLandNeighbors = new ArrayList<Cell>();
                    potentialLandNeighbors.add(getLeftNeighbor(b, currIslandRow, currIslandCol));
                    potentialLandNeighbors.add(getTopNeighbor(b, currIslandRow, currIslandCol));
                    potentialLandNeighbors.add(getRightNeighbor(b, currIslandRow, currIslandCol));
                    potentialLandNeighbors.add(getBottomNeighbor(b, currIslandRow, currIslandCol));


                    //See if any of these neighbors are cut islands
                    for(Cell potentialLandNeighbor : potentialLandNeighbors) {
                        if(potentialLandNeighbor == null) {
                            potentialLandNeighbors.remove(potentialLandNeighbor);
                            continue;
                        }
                        if(potentialLandNeighbor.getIsLand()) {
                            potentialLandNeighbors.remove(potentialLandNeighbor);
                            continue;
                        }
                        ArrayList<Cell> neighborNeighbors = b.getNeighbors(potentialLandNeighbor.getRow(),potentialLandNeighbor.getCol());
                        for(Cell cell : neighborNeighbors) {
                            if(cell.getIsLand()) {
                                potentialLandNeighbors.remove(potentialLandNeighbor);
                                break;
                            }
                        }
                    }

                    //If possible, select a neighbor to make land now
                    if(potentialLandNeighbors.size() > 0) {
                        //Have a heuristic for choosing one?
                        potentialLandNeighbors.get(0).setIsLand();
                    }

                }
                while(lands.size() < currOriginIsland.getIslandSize());
            }

            //Check if it's a valid solution - only if a pond exists
            if(!pondExists(b)) {
                valid=true;
            }

        }
        //Set leftmost cell to land - add to stack
        //Surround that island with water - add to stack
        //move to next origin island and do the same

        //if when placing water, place on an already existing land, put the land in another spot next to origin island
        //pop moves off the stack until you reach the origin island, now place land in different spot
        //if after surrounding the island with water you create a pool, backtrack to origin island and place land in different spot
        //if run out of places to put land, backtrack to next origin island
        //when do we check if we isolated water?


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

    // ------ CHECKER METHODS ------
    public Boolean isValidEndGoal(Board b, int curRow, int curCol) {
        return !lakeExists(b,curRow,curCol) && !pondExists(b) && verifyIslands(b);
    }

    public Boolean isLastIndex(int r, int c) {
        return r == height-1 && c == width-1;
    }

    public Boolean isGoal(Board b, int r, int c) {
        Cell curCell = b.getCell(r,c);
        if (isValidEndGoal(b.drawLand(r,c),r,c)) return true;
        if (!curCell.getIsOrigin() && isValidEndGoal(b.drawWater(r,c),r,c)) return true;
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


    public Boolean checkIslands(Board b) {
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
