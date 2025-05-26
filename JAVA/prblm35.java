import java.net.*;
import java.io.*;

public class prblm35 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server started, waiting for client...");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected");

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String msg;
        while ((msg = in.readLine()) != null) {
            System.out.println("Client says: " + msg);
            if (msg.equalsIgnoreCase("bye")) break;
            out.println("Echo: " + msg);
        }
        clientSocket.close();
        serverSocket.close();
    }
}

