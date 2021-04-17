import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class Server1 implements Runnable {
    private static Socket clientDialog;

    public Server1(Socket client) {
        Server1.clientDialog = client;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream());
            DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            while (!clientDialog.isClosed()) {
                System.out.println("Server reading from channel");
                /*char[] array;
                array = in.readUTF().toCharArray();
                for (int i = 0; i < 3; i++) {
                    System.out.println(array[i]);
                }*/
                System.out.println(Arrays.toString(in.readAllBytes()));
                Thread.sleep(0);
            }
            in.close();
            out.close();
            reader.close();
            writer.close();
            clientDialog.close();

            System.out.println("Closing connections & channels - DONE.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}