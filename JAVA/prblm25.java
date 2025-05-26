import java.util.HashMap;
import java.util.Scanner;

public class prblm25 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<Integer, String> students = new HashMap<>();

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine(); // consume newline

        for (int i = 0; i < n; i++) {
            System.out.print("Enter student ID: ");
            int id = sc.nextInt();
            sc.nextLine(); // consume newline
            System.out.print("Enter student name: ");
            String name = sc.nextLine();
            students.put(id, name);
        }

        System.out.print("Enter ID to search: ");
        int searchId = sc.nextInt();
        sc.close();

        String name = students.get(searchId);
        if (name != null) {
            System.out.println("Student Name: " + name);
        } else {
            System.out.println("Student ID not found.");
        }
    }
}
