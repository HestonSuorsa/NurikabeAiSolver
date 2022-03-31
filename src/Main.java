import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // TODO: Run AI implementations and time them
        Scanner scan = null;

        long bdfsTotalTime; long bdfsTotalNodes;
        long acTotalTime;   long acTotalNodes;
        long ldfsTotalTime; long ldfsTotalNodes;
        long start;         long end;
        long elapsed;

        // LOAD DATA =======================================================|
        Map<Integer, String[]> allFiles = new HashMap<Integer, String[]>();

        String[] _3x3s = new String[] {
                "3x3_1.txt", "3x3_2.txt", "3x3_3.txt",
                "3x3_4.txt", "3x3_5.txt", "3x3_6.txt"
        };
        String[] _5x5s = new String[] {
                "5x5_1.txt", "5x5_2.txt", "5x5_3.txt",
                "5x5_4.txt", "5x5_5.txt", "5x5_6.txt"
        };
        String[] _7x7s = new String[] {
                "7x7_1.txt", "7x7_2.txt", "7x7_3.txt",
                "7x7_4.txt", "7x7_5.txt", "7x7_6.txt"
        };
        String[] _10x10s = new String[] {
                "10x10_1.txt", "10x10_2.txt"
        };

        allFiles.put(3,_3x3s); allFiles.put(5,_5x5s);
        allFiles.put(7,_7x7s); allFiles.put(10,_10x10s);
        // LOADED DATA =====================================================|

        for (int key : allFiles.keySet()) {
                bdfsTotalTime = 0;
                bdfsTotalNodes = 0;
                acTotalTime = 0;
                acTotalNodes = 0;
                ldfsTotalTime = 0;
                ldfsTotalNodes = 0;

            System.out.println("["+key+"x"+key+"] Boards\n");

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
                        Cell curCell = board.getCell(row, col);
                        curCell.setIsOrigin(true);
                        curCell.setIsLand();
                        curCell.setIslandSize(size);
                    }
                    //Now the board is completely initialized and dfs can happen
                    IslandDFS ldfs = new IslandDFS(board, numIslands);
                    BoardDFS bdfs = new BoardDFS(board, numIslands);
                    AntColony antc = new AntColony(board, numIslands);

                    // RUN ISLAND DFS
                    start = System.currentTimeMillis();
                    System.out.println(ldfs.run());
                    end = System.currentTimeMillis();
                    elapsed = end - start;
                    System.out.println("[Island DFS] Nodes visited: " + ldfs.nodesVisited + " in " + elapsed + " ms\n");
                    ldfsTotalTime += elapsed;
                    ldfsTotalNodes += ldfs.nodesVisited;

                    if (key != 10) {
                        // RUN BOARD DFS
                        System.out.println("B before dfs\n"+board);
                        start = System.currentTimeMillis();
                        System.out.println(bdfs.run());
                        end = System.currentTimeMillis();
                        elapsed = end - start;
                        System.out.println("[Board DFS] Nodes visited: " + bdfs.nodesVisited + " in " + elapsed + " ms\n");
                        bdfsTotalTime += elapsed;
                        bdfsTotalNodes += bdfs.nodesVisited;

                        // RUN ANT COLONY DFS
                        start = System.currentTimeMillis();
                        System.out.println(antc.run());
                        end = System.currentTimeMillis();
                        elapsed = end - start;
                        System.out.println("[Ant Colony] Nodes visited: " + antc.nodesVisited + " in " + elapsed + " ms\n");
                        acTotalTime += elapsed;
                        acTotalNodes += antc.nodesVisited;
                    }
                }
            System.out.println(key + "x" + key + " boards analysis:");
            // Island DFS
            System.out.println("[Island DFS]");
            System.out.println("Average run time: " + ldfsTotalTime / numFiles);
            System.out.println("Average nodes visited: " + ldfsTotalNodes / numFiles);
            System.out.println();
            if (key != 10) {
                // Board DFS
                System.out.println("[Board DFS]");
                System.out.println("Average run time: " + bdfsTotalTime / numFiles);
                System.out.println("Average nodes visited: " + bdfsTotalNodes / numFiles);
                System.out.println();

                // Ant Colony
                System.out.println("[Ant Colony]");
                System.out.println("Average run time: " + acTotalTime / numFiles);
                System.out.println("Average nodes visited: " + acTotalNodes / numFiles);
                System.out.println();
            }
        }
    }
}
