import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TesterClass {
    public static void main(String[] args) {
        Scanner scan = null;
        try {
            File f = new File("7x7_5.txt");
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
            curCell.setIsLand();
            curCell.setIslandSize(size);
        }

        //Now the board is completely initialized and dfs can happen

        //This breaks bc we aren't done yet lol
        AntColony ac = new AntColony(board, numIslands);
        //AntColony ant = new AntColony(board, numIslands);

        long start = System.currentTimeMillis();
        System.out.println(ac.run());
        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end-start) + " Nodes: " + ac.nodesVisited);
    }
}
