import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Board {
    Boolean[][] board;
    int height, width;

    public Board(int height, int width) {
        this.board = new Boolean[height][width];
        // TODO: might need to initialize to all land (true)
        this.height = height;
        this.width = width;
    }

    public Board clone() {
        Board newB = new Board(height,width);
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                newB.board[r][c] = board[r][c];
            }
        }
        return newB;
    }

    public Board drawWater(int row, int col) {
        board[row][col] = false;
        return this;
    }

    public Board drawLand(int row, int col) {
        board[row][col] = true;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (board[r][c]) sb.append(" ");
                else sb.append("#");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}