import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Board {
    // Boolean[][] board;
    Cell[][] board;
    int height, width;
    int numCells;

    public Board(int height, int width) {
        this.height = height;
        this.width = width;
        numCells = height*width;
        this.board = new Cell[height][width];
        for(int r=0; r<height; r++) {
            for(int c=0; c<width; c++) {
                board[r][c] = new Cell(r,c,false);
                board[r][c].setIsWater();
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
        return board[row][col];
    }

    public Board clone() {
        Board newB = new Board(height,width);
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
                sb.append(board[r][c].toString());
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}