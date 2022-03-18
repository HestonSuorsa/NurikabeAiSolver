import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    Boolean[][] board;
    int height, width;

    public Board(int height, int width) {
        this.board = new Boolean[height][width];
        // Fill board to all land
        for (int row = 0; row < height; row++) {
            Arrays.fill(board[row], true);
        }
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

    /**
     * To String without the origin island numbers
     * @return
     */
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
                else if (board[r][c]) sb.append(" ");
                else sb.append("#");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}