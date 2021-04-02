import java.net.*;
import java.io.*;

public class MyServer {
    private final static int first = 0;
    private final static int second = 2;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("Server start!");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client entry!");
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            String request = reader.readLine();

            int rand = first + (int) (Math.random() * second);
            String str = " " + request.length() + "\n";

            writer.write(rand);
            writer.write(str);
            writer.flush();

            writer.close();
            reader.close();
            clientSocket.close();
        }

    }
}
