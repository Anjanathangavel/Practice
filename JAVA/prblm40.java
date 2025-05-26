import java.util.concurrent.*;
import java.util.*;

public class prblm40 {
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        List<Callable<String>> tasks = List.of(
            () -> "Task 1 completed",
            () -> "Task 2 completed",
            () -> "Task 3 completed"
        );

        List<Future<String>> results = executor.invokeAll(tasks);

        for (Future<String> f : results) {
            System.out.println(f.get());
        }

        executor.shutdown();
    }
}
