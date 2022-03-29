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
            File f = new File("5x5_1.txt");
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
        Board board1 = new Board(height, width, true);


        //Add Origin islands
        for (int i = 0; i < numIslands; i++) {
            int size = scan.nextInt();
            int row = scan.nextInt();
            int col = scan.nextInt();
            Cell curCell = board1.getCell(row,col);
            curCell.setIsOrigin(true);
            curCell.setIsLand();
            curCell.setIslandSize(size);
        }
        //Clone board before DFS
        Board board2 = board1.clone();

        //Now the board is completely initialized and dfs can happen

        //This breaks bc we aren't done yet lol
        DFS_NurikabeAI dfs = new DFS_NurikabeAI(board1, numIslands);

        System.out.println(dfs.run());
        System.out.println("Nodes visited: " + dfs.nodesVisited);

//        MostConstrained_NurikabeAI mc = new MostConstrained_NurikabeAI(board2, numIslands);
//        mc.run();
//        System.out.println(board2);
    }
}
