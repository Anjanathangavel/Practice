
import java.lang.reflect.Method;

public class prblm39 {
    public void sayHello(String name) {
        System.out.println("Hello, " + name);
    }

    public static void main(String[] args) throws Exception {
        Class<?> cls = Class.forName("ReflectionExample");
        Object obj = cls.getDeclaredConstructor().newInstance();

        for (Method m : cls.getDeclaredMethods()) {
            System.out.println("Method: " + m.getName());
        }

        Method sayHello = cls.getDeclaredMethod("sayHello", String.class);
        sayHello.invoke(obj, "ChatGPT");
    }
}
