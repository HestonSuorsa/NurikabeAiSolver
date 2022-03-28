import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TesterClass {
    public static void main(String[] args) {
        Scanner scan = null;
        try {
            File f = new File("test.txt");
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
        Board board = new Board(height, width);

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
        board.getCell(0,0).setIsLand();
        board.getCell(1,0).setIsLand();
        board.getCell(1,1).setIsLand();

        //Now the board is completely initialized and dfs can happen

        //This breaks bc we aren't done yet lol
        DFS_NurikabeAI dfs = new DFS_NurikabeAI(board, numIslands);
        System.out.println(board);
        System.out.println(dfs.checkIslands(board));
    }
}
