import java.util.*;

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
        boolean badMoveMade;
        while(!valid) {
            //TODO: Check if all origin islands are off that stack
            //Set board to all water again
            b.resetBoard();
            for(Cell currOriginIsland : originIslands) {
                b.drawLand(currOriginIsland.getRow(), currOriginIsland.getCol());
            }
            badMoveMade=false;

            for(Cell currOriginIsland : originIslands) {
//                System.out.println("Doing origin: " + currOriginIsland);
                Set<Cell> exploredCells = new HashSet<Cell>();
                ArrayList<Cell> frontierCells = new ArrayList<>();
                frontierCells.add(currOriginIsland);
                Cell temp = currOriginIsland;
                if(temp == b.getCell(5,6)) {
                    System.out.println("hi");
                }
//            Cell currOriginIsland = originIslandsToIsolate.pop();
//                moves.add(currOriginIsland);
//
//                Cell originNeighbors[] = {
//                        getLeftNeighbor(b, currIslandRow, currIslandCol),
//                        getTopNeighbor(b, currIslandRow, currIslandCol),
//                        getRightNeighbor(b, currIslandRow, currIslandCol),
//                        getBottomNeighbor(b, currIslandRow, currIslandCol)
//                };


                int currIslandSize = 1;
//                System.out.println(currOriginIsland);
                while (currIslandSize < currOriginIsland.getIslandSize())  {
//                    System.out.println("Curr island size " + currIslandSize);


//                    int currIslandRow = currOriginIsland.getRow();
//                    int currIslandCol = currOriginIsland.getCol();

                    //add neighbors to list
//                    ArrayList<Cell> potentialLandNeighbors = new ArrayList<Cell>();
//                    potentialLandNeighbors.add(getLeftNeighbor(b, currIslandRow, currIslandCol));
//                    potentialLandNeighbors.add(getTopNeighbor(b, currIslandRow, currIslandCol));
//                    potentialLandNeighbors.add(getRightNeighbor(b, currIslandRow, currIslandCol));
//                    potentialLandNeighbors.add(getBottomNeighbor(b, currIslandRow, currIslandCol));
//                    System.out.println(potentialLandNeighbors);
//
//
//                    //See if any of these neighbors are cut islands
//                    Set<Cell> rejectSet = new HashSet<>();
//                    for(Cell potentialLandNeighbor : potentialLandNeighbors) {
//                        if(potentialLandNeighbor == null) {
//                            rejectSet.add(potentialLandNeighbor);
//                            continue;
//                        }
////                        if(potentialLandNeighbor.getIsLand()) {
////                            rejectSet.add(potentialLandNeighbor);
////                            continue;
////                        }
//                        ArrayList<Cell> neighborNeighbors = b.getNeighbors(potentialLandNeighbor.getRow(),potentialLandNeighbor.getCol());
//                        for(Cell cell : neighborNeighbors) {
//                            if(cell.getIsLand() && cell != currOriginIsland) {
//                                rejectSet.add(potentialLandNeighbor);
//                                break;
//                            }
//                        }
//                    }
//
//                    //Remove the reject set from potential land
////                    potentialLandNeighbors.removeAll(rejectSet);
//                    for(Cell cell: rejectSet) {
//                        potentialLandNeighbors.remove(cell);
//                    }



                    //can expant potential negihbors to negihbor neighbors!! for more land :)

                    //If possible, select a neighbor to make land now
//                    if(potentialLandNeighbors.size() > 0) {
//                        //Have a heuristic for choosing one?
//                        potentialLandNeighbors.get(0).setIsLand();
//                        System.out.println("I set the land");
//                    }


                    boolean waterFound = false;
                    while(!waterFound) {

                        ArrayList<Cell> potentialLandNeighbors = getPotentialLandNeighbors(b,temp);

//                        for (Cell cell : potentialLandNeighbors) {
////                            System.out.println(cell);
////                            System.out.println(b);
//                            if (cell != null && cell.getIsWater()) {
//                                waterFound = true;
//                                cell.setIsLand();
//                                frontierCells.add(cell);
////                                System.out.println(b);
//                                break;
//                            }
//                        }
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

                        //call method to add neighbor neighbors to potential land and prune again
//                        boolean validCellFound = false;
                        //REdo this
                        int counter = 0;
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
//                            System.out.println("No frontier cells");
                            badMoveMade=true; //escape
                            break;
                        }
//                        while(temp == currOriginIsland) {
//                            randNum = rn.nextInt(frontierCells.size());
//                            temp = frontierCells.get(randNum);
//                        }
//                            while(potentialLandNeighbors.size() > 0 && !validCellFound && counter < potentialLandNeighbors.size()) {
//                                Random rn = new Random();
//                                int randNum = rn.nextInt(potentialLandNeighbors.size());
//                                Cell randomlyChosenCell = potentialLandNeighbors.get(randNum);
//                                counter++;
//                                if(randomlyChosenCell != null && !exploredCells.contains(randomlyChosenCell)) {
//                                    validCellFound = true;
//                                    temp = randomlyChosenCell;
//                                }
//                            }

//                            for(Cell cell : potentialLandNeighbors) {
//                                if(cell != null && !exploredCells.contains(cell)) {
//                                    System.out.println("Reassigning temp\n");
//                                    temp=cell;
//                                    break;
//                                }
//                            }

                    }
                    if(badMoveMade) { break; }
                    Set<Cell> lands = new HashSet<>();
                    findConnectedLand(currOriginIsland, lands, b);
                    currIslandSize = lands.size();

                }
                if(badMoveMade) { break; }
            }
            //Check if it's a valid solution - only if a pond exists
//            System.out.println("Solution found, checking if valid.");
            if(badMoveMade) {continue;}
            if(!pondExists(b) && !lakeExists(b) && checkIslands(b)) {
                valid=true;
            }
            if(!valid) {
//                System.out.println("Solution not valid.\n");
            }
            else {
                System.out.println("Solution valid\n");
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
//        System.out.println(potentialLandNeighbors);


        //See if any of these neighbors are cut islands
        Set<Cell> rejectSet = new HashSet<>();
        for(Cell potentialLandNeighbor : potentialLandNeighbors) {
            if(potentialLandNeighbor == null) {
                rejectSet.add(potentialLandNeighbor);
                continue;
            }
//                        if(potentialLandNeighbor.getIsLand()) {
//                            rejectSet.add(potentialLandNeighbor);
//                            continue;
//                        }
            ArrayList<Cell> neighborNeighbors = b.getNeighbors(potentialLandNeighbor.getRow(),potentialLandNeighbor.getCol());
            for(Cell cell : neighborNeighbors) {
                if(cell.getIsLand() && cell != currOriginIsland) {
                    rejectSet.add(potentialLandNeighbor);
                    break;
                }
            }
        }

        //Remove the reject set from potential land
//                    potentialLandNeighbors.removeAll(rejectSet);
        for(Cell cell: rejectSet) {
            potentialLandNeighbors.remove(cell);
        }

        return potentialLandNeighbors;
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
