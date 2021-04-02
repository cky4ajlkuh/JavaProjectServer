import java.net.*;
import java.io.*;

public class MyServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("Server start!");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client entry!");
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            String request = reader.readLine();

            String response = "length your SMS is " + request.length();
            writer.write(response);
            writer.newLine();
            writer.flush();

            writer.close();
            reader.close();
            clientSocket.close();
        }

    }
}
