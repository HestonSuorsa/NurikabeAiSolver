import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    // Boolean[][] board;
    Cell[] board;
    int height, width;
    int numCells;

    public Board(int height, int width) {
        // this.board = new Boolean[height][width];
        numCells = height*width;
        this.board = new Cell[numCells];
        for(int i=0; i<numCells; i++) {
            board[i] = new Cell(i,false);
            board[i].setIsWater(true);
        }
        this.height = height;
        this.width = width;
    }

    public Cell getValue(int row, int col) {
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
        board[row][col].setIsWater(true);
        return this;
    }

    public Board drawLand(int row, int col) {
        board[row][col].setIsLand(true);
        return this;
    }

    /**
     * To String without the origin island numbers
     * @return
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (board[r][c].getIsLand()) sb.append(" ");
                else sb.append("#");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * To String with the origin island numbers
     * @return
     */
    public String toString(ArrayList<OriginIsland> islands) {
        islands.sort(new SortByPosition());
        int i = islands.size()-1; // Start at first island (from top-down/left-right)
        OriginIsland curr = islands.get(i);
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (r == curr.row && c == curr.col) {
                    sb.append(curr.size);
                    curr = islands.get(--i);
                }
                else if (board[r][c].getIsLand()) sb.append(" ");
                else sb.append("#");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}