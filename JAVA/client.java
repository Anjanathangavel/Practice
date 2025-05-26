import java.net.*;
import java.io.*;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        Scanner scanner = new Scanner(System.in);
        String msg;
        while (true) {
            msg = scanner.nextLine();
            out.println(msg);
            if (msg.equalsIgnoreCase("bye")) break;
            System.out.println("Server says: " + in.readLine());
        }

        socket.close();
        scanner.close();
    }
}

