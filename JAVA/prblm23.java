import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class prblm23 {
    public static void main(String[] args) {
        try {
            File file = new File("output.txt");
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
}
