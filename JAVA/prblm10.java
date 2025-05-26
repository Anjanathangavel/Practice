import java.util.Scanner;
import java.util.Random;

public class prblm10 {
    public static void main(String[] args) {
        Random rand = new Random();
        Scanner sc = new Scanner(System.in);
        int numberToGuess = rand.nextInt(100) + 1;
        int guess = 0;

        while (guess != numberToGuess) {
            System.out.print("Guess a number between 1 and 100: ");
            guess = sc.nextInt();

            if (guess < numberToGuess) {
                System.out.println("Too low!");
            } else if (guess > numberToGuess) {
                System.out.println("Too high!");
            } else {
                System.out.println("Correct! You guessed the number.");
            }
        }
        sc.close();
    }
}
