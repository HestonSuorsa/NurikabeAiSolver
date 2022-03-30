import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // TODO: Run AI implementations and time them
        Scanner scan = null;

        long dfsTotalTime;
        long dfsTotalNodes;

        // LOAD DATA =======================================================|
        Map<Integer, String[]> allFiles = new HashMap<Integer, String[]>();

        String[] _3x3s = new String[] { "3x3_1.txt" };
        String[] _5x5s = new String[] {
                "5x5_1.txt", "5x5_2.txt", "5x5_3.txt",
                "5x5_4.txt", "5x5_5.txt", "5x5_6.txt"
        };
        String[] _7x7s = new String[] { "7x7_1.txt", "7x7_2.txt" };

        allFiles.put(3,_3x3s); allFiles.put(5,_5x5s); allFiles.put(7,_7x7s);
        // LOADED DATA =====================================================|

        for (int key : allFiles.keySet()) {
            dfsTotalTime = 0;
            dfsTotalNodes = 0;
            String[] group = allFiles.get(key);
            int numFiles = group.length;
            for (int index = 0; index < numFiles; index++) {
                try {
                    File f = new File(group[index]);
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
                    curCell.setIslandSize(size);
                }
                //Now the board is completely initialized and dfs can happen
                DFS_NurikabeAI dfs = new DFS_NurikabeAI(board, numIslands);
                long start = System.currentTimeMillis();
                System.out.println(dfs.run());
                long end = System.currentTimeMillis();
                long elapsed = end-start;
                System.out.println("Nodes visited: " + dfs.nodesVisited + " in " + elapsed + " ms\n");
                dfsTotalTime += elapsed;
                dfsTotalNodes += dfs.nodesVisited;
            }
            System.out.println(key + "x" + key + " boards analysis:");
            System.out.println("DFS average run time: " + dfsTotalTime/numFiles);
            System.out.println("DFS average nodes visited: " + dfsTotalNodes/numFiles);
        }
    }
}
