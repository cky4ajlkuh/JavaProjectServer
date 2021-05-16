import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.*;
import java.io.*;
import java.util.LinkedList;

public class MyServer {

    public static final int PORT = 9999;
    public static LinkedList<MyServerRun> serverList = new LinkedList<>();
    public static int random_number = (int) (Math.random() * 2);
    public static char[] str;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        System.out.println("Рандомный номер получается " + random_number);
        System.out.println("Server Started");
        try {
            while (serverList.size() < 1) {
                Socket socket = server.accept();
                try {
                    serverList.add(new MyServerRun(socket));
                } catch (IOException | InterruptedException e) {
                    socket.close();
                }
            }
            if (random_number == 0) {
                serverList.get(0).sendWho(1);
                // serverList.get(1).sendWho(0);
                while (!server.isClosed()) {
                    serverList.get(0).run();
                    //  serverList.get(1).send(str);
                    //  serverList.get(1).run();
                    serverList.get(0).send(str);
                }
            }
            if (random_number == 1) {
                serverList.get(0).sendWho(0);
                //     serverList.get(1).sendWho(1);
                while (!server.isClosed()) {
                    // serverList.get(1).run();
                    serverList.get(0).run();// этот метод над будет удалить, когда вернусь к 2-м игрокам
                    serverList.get(0).send(str);
                    serverList.get(0).run();
                    //  serverList.get(1).send(str);
                }
            }

        } finally {
            server.close();
        }
    }
}

class MyServerRun {
    private final Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;

    MyServerRun(Socket socket) throws IOException, InterruptedException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

    }

    public void run() {
        try {
            String str = in.readLine();
            StringReader reader = new StringReader(str);
            ObjectMapper mapper = new ObjectMapper();
            Sosiska cat = mapper.readValue(reader, Sosiska.class);
            System.out.println(cat.getNumber() + "  " + cat.getValue());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException ignored) {
        }
    }

    public void sendWho(int p) throws IOException {
        out.write(p);
        out.flush();
    }

    public void send(char[] array) {
        try {
            String str = array[0] + " " + array[2];
            out.write(str + '\n');
            out.flush();
        } catch (IOException ignored) {
        }
    }
}
