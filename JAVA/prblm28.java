
import java.util.*;
import java.util.stream.Collectors;

public class prblm28 {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(10, 15, 20, 25, 30, 35);

        List<Integer> evens = numbers.stream()
                                     .filter(n -> n % 2 == 0)
                                     .collect(Collectors.toList());

        System.out.println("Even numbers: " + evens);
    }
}
