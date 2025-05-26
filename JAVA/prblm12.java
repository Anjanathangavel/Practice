public class prblm12 {

    public static int add(int a, int b) {
        return a + b;
    }

    public static double add(double a, double b) {
        return a + b;
    }

    public static int add(int a, int b, int c) {
        return a + b + c;
    }

    public static void main(String[] args) {
        System.out.println("Sum of 5 and 10: " + add(5, 10));
        System.out.println("Sum of 2.5 and 3.7: " + add(2.5, 3.7));
        System.out.println("Sum of 1, 2 and 3: " + add(1, 2, 3));
    }
}
