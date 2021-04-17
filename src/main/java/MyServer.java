import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {
    private final static int first = 0;
    private final static int second = 2;

    static ExecutorService executeIt = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws IOException {
        System.out.println("Server start!");
        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            Socket client = serverSocket.accept();
            while (!client.isClosed()) {
                //Socket client = serverSocket.accept();
                executeIt.execute(new Server1(client));
            }
            executeIt.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
