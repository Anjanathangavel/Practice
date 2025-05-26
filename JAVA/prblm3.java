
import java.util.Scanner;

public class prblm3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an integer: ");
        int number = scanner.nextInt();
        
        String result = (number % 2 == 0) ? "even" : "odd";
        System.out.println(number + " is an " + result + " number.");
        
        scanner.close();
    }
}