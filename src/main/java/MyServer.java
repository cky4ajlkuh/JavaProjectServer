import java.net.*;
import java.io.*;
import java.util.LinkedList;

public class MyServer {

    public static final int PORT = 9999;
    public static LinkedList<MyServerRun> serverList = new LinkedList<>();
    public static final int random_number = (int) (Math.random() * 2);
    private static int x = 0;
    private static int y = 1;
    public static char[] str;

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        System.out.println("Рандомный номер получается " + random_number);
        System.out.println("Server Started");
       /* try {
            for (int i = 0; serverList.size() != 2; i++) {
                Socket socket = server.accept();
                try {
                    serverList.add(new MyServerRun(socket));
                    if (random_number == 0) {
                        serverList.get(i).sendWho(x);
                        x++;
                    }
                    if (random_number == 1) {
                        serverList.get(i).sendWho(y);
                        y--;
                    }
                } catch (IOException | InterruptedException e) {
                    socket.close();
                }
            }

            while (true){

            }
        }*/

        try {
            while (serverList.size() < 2) {
                Socket socket = server.accept();
                try {
                    serverList.add(new MyServerRun(socket));
                } catch (IOException | InterruptedException e) {
                    socket.close();
                }
            }
            if (random_number == 0) {
                serverList.get(0).sendWho(1);
                serverList.get(1).sendWho(0);
                while (true) {
                    serverList.get(0).run();
                    serverList.get(1).send(str);
                    serverList.get(1).run();
                    serverList.get(0).send(str);
                }
            }
            if (random_number == 1) {
                serverList.get(0).sendWho(0);
                serverList.get(1).sendWho(1);
                while (true) {
                    serverList.get(1).run();
                    serverList.get(0).send(str);
                    serverList.get(0).run();
                    serverList.get(1).send(str);
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
    char[] str;

    MyServerRun(Socket socket) throws IOException, InterruptedException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

    }

    public void run() {
        try {
            while (!socket.isClosed()) {
                MyServer.str = in.readLine().toCharArray();
            }
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
            String str = array[0] + " " + array[2] + " " + array[4];
            out.write(str + "\n");
            out.flush();
        } catch (IOException ignored) {
        }
    }
}
