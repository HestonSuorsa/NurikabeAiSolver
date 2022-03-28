import java.util.ArrayList;

public class MostConstrained_NurikabeAI {
    Board gameBoard;
    int height, width, numIslands;
    ArrayList<Cell> originIslands;
    String endBoard;

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
        if (MCSolver(gameBoard,originIslands.get(0)))
            return endBoard;
        return "No solution found";
    }

    public boolean MCSolver(Board b, Cell origin) {
        return false;
    }

}
