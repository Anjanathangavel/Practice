import java.util.InputMismatchException;
import java.util.Scanner;

public class prblm2 {
    public static void main(String[] args) {
        // Use try-with-resources to auto-close Scanner
        try (Scanner scanner = new Scanner(System.in)) {
            double num1, num2;
            int operation;
            
            // Get first number with validation
            while (true) {
                try {
                    System.out.print("Enter first number: ");
                    num1 = scanner.nextDouble();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number.");
                    scanner.nextLine(); // Clear invalid input
                }
            }
            
            // Get second number with validation
            while (true) {
                try {
                    System.out.print("Enter second number: ");
                    num2 = scanner.nextDouble();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number.");
                    scanner.nextLine();
                }
            }
            
            // Get operation choice with validation
            while (true) {
                try {
                    System.out.println("Choose operation:");
                    System.out.println("1. Addition (+)");
                    System.out.println("2. Subtraction (-)");
                    System.out.println("3. Multiplication (*)");
                    System.out.println("4. Division (/)");
                    System.out.print("Enter operation number (1-4): ");
                    operation = scanner.nextInt();
                    
                    if (operation < 1 || operation > 4) {
                        System.out.println("Please enter a number between 1 and 4!");
                        continue;
                    }
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter a number (1-4).");
                    scanner.nextLine();
                }
            }
            
            // Perform calculation
            double result;
            String operationSymbol = "";
            
            switch (operation) {
                case 1:
                    result = num1 + num2;
                    operationSymbol = "+";
                    break;
                case 2:
                    result = num1 - num2;
                    operationSymbol = "-";
                    break;
                case 3:
                    result = num1 * num2;
                    operationSymbol = "*";
                    break;
                case 4:
                    if (num2 != 0) {
                        result = num1 / num2;
                        operationSymbol = "/";
                    } else {
                        System.out.println("Error: Cannot divide by zero!");
                        return;
                    }
                    break;
                default:
                    System.out.println("Invalid operation choice!");
                    return;
            }
            
            System.out.printf("Result: %.2f %s %.2f = %.2f%n", 
                            num1, operationSymbol, num2, result);
            
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}