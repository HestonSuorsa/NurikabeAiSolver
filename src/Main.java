import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // TODO: Run AI implementations and time them
        Scanner scan = null;
        try {
            File f = new File("10x10_1.txt");
            scan = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            scan.close();
            return;
        }

        int height = scan.nextInt();
        int width = scan.nextInt();
        int numIslands = scan.nextInt();

        //Initialize Board
        Board board = new Board(height, width, true);

        //Add Origin islands
        for (int i = 0; i < numIslands; i++) {
            int size = scan.nextInt();
            int row = scan.nextInt();
            int col = scan.nextInt();
            Cell curCell = board.getCell(row,col);
            curCell.setIsOrigin(true);
            // curCell.setIsLand(); // TODO: removed to try to fix issue
            curCell.setIslandSize(size);
        }

        //Now the board is completely initialized and dfs can happen

        //This breaks bc we aren't done yet lol
        DFS_NurikabeAI dfs = new DFS_NurikabeAI(board, numIslands);

        System.out.println(dfs.run());
        System.out.println("Nodes visited: " + dfs.nodesVisited);
    }
}
