public class prblm38 {
    private int value = 10;

    public int getValue() {
        return value;
    }

    public void setValue(int newValue) {
        this.value = newValue;
    }

    public static void main(String[] args) {
    prblm38 demo = new prblm38();
        demo.setValue(42);
        System.out.println("Value: " + demo.getValue());
    }
}
