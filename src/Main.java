import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // TODO: Run AI implementations and time them
        try {
            File f = new File("5x5_1.txt");
            Scanner sc = new Scanner(f);
            StringBuilder sb = new StringBuilder();
            while (sc.hasNextLine()) {
                sb.append((sc.nextLine() + "\n"));
            }
            String fileContent = sb.toString();
            sc.close();

            Scanner scan = new Scanner(fileContent);
            int height = scan.nextInt();
            int width = scan.nextInt();
            int numIslands = scan.nextInt();
            ArrayList<OriginIsland> islands = new ArrayList<>(numIslands);
            // for (int i = 0; i < numIslands; i++) {
            //     int size = scan.nextInt();
            //     int row = scan.nextInt();
            //     int col = scan.nextInt();
            //     islands.add(new OriginIsland(size, row, col));
            // }

            //Initialize all Cells
            ArrayList<Cell> cells = new ArrayList<Cell>();
            for(int r=0; r<height; r++) {
                for(int c=0; c<width; c++) {
                    cells.add(new Cell((r+c),false));   //start as not origin
                }
            }

            //Add Origin islands
            for (int i = 0; i < numIslands; i++) {
                int size = scan.nextInt();
                int row = scan.nextInt();
                int col = scan.nextInt();
                cells.get(row+col).setIsOrigin(true);
                // islands.add(new OriginIsland(size, row, col));
            }



            // DFS_NurikabeAI dfs = new DFS_NurikabeAI(height, width, numIslands, islands);

            // TODO: TESTED Comparators (THEY WORK!!!!)
            dfs.islands.sort(new SortByPosition());
            System.out.println("\nBy Position:\n" + dfs.islandsToString());

            dfs.islands.sort(new SortBySize());
            System.out.println("\nBy Size:\n" + dfs.islandsToString());
            // TODO: END of comparator test code

            Board board = new Board(height, width);
            System.out.println(dfs.DFS(board, 0, 0));

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}
