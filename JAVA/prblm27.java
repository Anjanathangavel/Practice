import java.util.*;

public class prblm27 {
    public static void main(String[] args) {
        List<String> items = Arrays.asList("Banana", "Apple", "Mango", "Cherry");

        Collections.sort(items, (a, b) -> a.compareToIgnoreCase(b));

        System.out.println("Sorted list:");
        for (String item : items) {
            System.out.println(item);
        }
    }
}
