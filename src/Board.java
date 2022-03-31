// Verifiers modeled after this code
// https://github.com/dbathon/nurikabe-java/blob/dfd9f9da33a3d67dc0e2153e433ad2b0be057f9e/src/main/java/dbathon/nurikabe/board/Board.java#L182

import java.util.ArrayList;

public class Board {
    Cell[][] board;
    int height, width;
    int numCells;

    public Board(int height, int width, boolean setAllWater) {
        this.height = height;
        this.width = width;
        numCells = height*width;
        this.board = new Cell[height][width];
        for(int r=0; r<height; r++) {
            for(int c=0; c<width; c++) {
                board[r][c] = new Cell(r,c,false);
                if(setAllWater) {
                    board[r][c].setIsWater();
                }
            }
        }
    }

    public ArrayList<Cell> getNeighbors(int row, int col) {
        ArrayList<Cell> neighbors = new ArrayList<>();

        //Add left neighbor
        if(col >= 1) {
            neighbors.add(board[row][col-1]);
        }

        //Add top neighbor
        if(row >= 1) {
            neighbors.add(board[row-1][col]);
        }

        //Add right neighbor
        if(col < width - 1) {
            neighbors.add(board[row][col+1]);
        }

        //Add bottom neighbor
        if(row < height - 1) {
            neighbors.add(board[row+1][col]);
        }

        return neighbors;
    }

    public Cell getCell(int row, int col) {
        Cell cell;
        try{
            cell = board[row][col];
        }
        catch(Exception e) {
            cell = null;
        }
        return cell;
    }

    public Board clone() {
        Board newB = new Board(height,width,false);
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                newB.board[r][c] = board[r][c].clone();
            }
        }
        return newB;
    }

    public Board drawWater(int row, int col) {
        board[row][col].setIsWater();
        return this;
    }

    public Board drawLand(int row, int col) {
        board[row][col].setIsLand();
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                int size = board[r][c].getIslandSize();
                switch (size) {
                    case 10: sb.append("A"); break;
                    case 11: sb.append("B"); break;
                    case 12: sb.append("C"); break;
                    case 13: sb.append("D"); break;
                    case 14: sb.append("E"); break;
                    case 15: sb.append("F"); break;
                    default: sb.append(board[r][c].toString());
                }
            }
            if (r != height-1) sb.append("\n");
        }
        return sb.toString();
    }

    public void resetBoard() {
        for(int r=0; r<height; r++) {
            for(int c=0; c<width; c++) {
                drawWater(r,c);
            }
        }
    }
}