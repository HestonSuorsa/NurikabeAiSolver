import java.io.File;
import java.io.FileNotFoundException;
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

            DFS_NurikabeAI dfs = new DFS_NurikabeAI(fileContent);

            // TODO: TESTED Comparators (THEY WORK!!!!)
            dfs.islands.sort(new SortByPosition());
            System.out.println("\nBy Position:\n" + dfs.islandsToString());

            dfs.islands.sort(new SortBySize());
            System.out.println("\nBy Size:\n" + dfs.islandsToString());
            // TODO: END of comparator test code

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}
