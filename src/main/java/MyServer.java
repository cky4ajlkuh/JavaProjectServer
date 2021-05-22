import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;

import java.net.*;
import java.io.*;
import java.util.LinkedList;

public class MyServer {

    public static final int PORT = 9999;
    public static LinkedList<MyServerRun> serverList = new LinkedList<>();
    public static LinkedList<Element> elements = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        try {
            while (serverList.size() < 2) {
                Socket socket = server.accept();
                try {
                    serverList.add(new MyServerRun(socket));
                } catch (IOException | InterruptedException e) {
                    socket.close();
                }
            }
            play();
        } finally {
            server.close();
        }
    }

    public static void play() throws IOException {
        int random_number = (int) (Math.random() * 2);
        if (random_number == 0) {
            serverList.get(0).sendWho(1);
            serverList.get(1).sendWho(0);
            while (!serverList.get(0).socket.isClosed() && !serverList.get(1).socket.isClosed()) {
                serverList.get(0).run();
                serverList.get(1).send();
                serverList.get(1).run();
                serverList.get(0).send();
            }
        }
        if (random_number == 1) {
            serverList.get(0).sendWho(0);
            serverList.get(1).sendWho(1);
            while (!serverList.get(0).socket.isClosed() & !serverList.get(1).socket.isClosed()) {
                serverList.get(1).run();
                serverList.get(0).send();
                serverList.get(0).run();
                serverList.get(1).send();
            }
        }
    }

    public static void end(String str) {
        serverList.get(0).sendWin(str);
        serverList.get(1).sendWin(str);
    }

    public static void finish() {
        if (elements.size() >= 5) {
            for (int i = 0; i < elements.size(); i++) {
                for (int j = 0; j < elements.size(); j++) {
                    for (Element element : elements) {
                        if (elements.get(i).getNumber() == 0) {
                            if (elements.get(j).getNumber() == 3) {
                                if (element.getNumber() == 6) {
                                    if (elements.get(i).getValue() == 'X' && elements.get(j).getValue() == 'X' && element.getValue() == 'X') {
                                        end("Крестики");
                                    }
                                    if (elements.get(i).getValue() == 'O' && elements.get(j).getValue() == 'O' && element.getValue() == 'O') {
                                        end("Нолики");
                                    }
                                }
                            }
                            if (elements.get(j).getNumber() == 1) {
                                if (element.getNumber() == 2) {
                                    if (elements.get(i).getValue() == 'X' && elements.get(j).getValue() == 'X' && element.getValue() == 'X') {
                                        end("Крестики");
                                    }
                                    if (elements.get(i).getValue() == 'O' && elements.get(j).getValue() == 'O' && element.getValue() == 'O') {
                                        end("Нолики");
                                    }
                                }
                            }
                            if (elements.get(j).getNumber() == 4) {
                                if (element.getNumber() == 8) {
                                    if (elements.get(i).getValue() == 'X' && elements.get(j).getValue() == 'X' && element.getValue() == 'X') {
                                        end("Крестики");
                                    }
                                    if (elements.get(i).getValue() == 'O' && elements.get(j).getValue() == 'O' && element.getValue() == 'O') {
                                        end("Нолики");
                                    }
                                }
                            }
                        }
                        if (elements.get(i).getNumber() == 1) {
                            if (elements.get(j).getNumber() == 4) {
                                if (element.getNumber() == 7) {
                                    if (elements.get(i).getValue() == 'X' && elements.get(j).getValue() == 'X' && element.getValue() == 'X') {
                                        end("Крестики");
                                    }
                                    if (elements.get(i).getValue() == 'O' && elements.get(j).getValue() == 'O' && element.getValue() == 'O') {
                                        end("Нолики");
                                    }
                                }
                            }
                        }
                        if (elements.get(i).getNumber() == 2) {
                            if (elements.get(j).getNumber() == 5) {
                                if (element.getNumber() == 8) {
                                    if (elements.get(i).getValue() == 'X' && elements.get(j).getValue() == 'X' && element.getValue() == 'X') {
                                        end("Крестики");
                                    }
                                    if (elements.get(i).getValue() == 'O' && elements.get(j).getValue() == 'O' && element.getValue() == 'O') {
                                        end("Нолики");
                                    }
                                }
                            }
                        }
                        if (elements.get(i).getNumber() == 5) {
                            if (elements.get(j).getNumber() == 4) {
                                if (element.getNumber() == 3) {
                                    if (elements.get(i).getValue() == 'X' && elements.get(j).getValue() == 'X' && element.getValue() == 'X') {
                                        end("Крестики");
                                    }
                                    if (elements.get(i).getValue() == 'O' && elements.get(j).getValue() == 'O' && element.getValue() == 'O') {
                                        end("Нолики");
                                    }
                                }
                            }
                        }
                        if (elements.get(i).getNumber() == 6) {
                            if (elements.get(j).getNumber() == 7) {
                                if (element.getNumber() == 8) {
                                    if (elements.get(i).getValue() == 'X' && elements.get(j).getValue() == 'X' && element.getValue() == 'X') {
                                        end("Крестики");
                                    }
                                    if (elements.get(i).getValue() == 'O' && elements.get(j).getValue() == 'O' && element.getValue() == 'O') {
                                        end("Нолики");
                                    }
                                }
                            }
                        }
                        if (elements.get(i).getNumber() == 2) {
                            if (elements.get(j).getNumber() == 4) {
                                if (element.getNumber() == 6) {
                                    if (elements.get(i).getValue() == 'X' && elements.get(j).getValue() == 'X' && element.getValue() == 'X') {
                                        end("Крестики");
                                    }
                                    if (elements.get(i).getValue() == 'O' && elements.get(j).getValue() == 'O' && element.getValue() == 'O') {
                                        end("Нолики");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (elements.size() == 9) {
            end("Дружба");
        }
    }
}

class MyServerRun {
    public static Socket socket;
    private final BufferedReader in;
    private final BufferedWriter out;

    MyServerRun(Socket socket) throws IOException, InterruptedException {
        MyServerRun.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void run() {
        try {
            if (!socket.isClosed()) {
                MyServer.finish();
                String str = in.readLine();
                StringReader reader = new StringReader(str);
                ObjectMapper mapper = new ObjectMapper();
                MyServer.elements.add(mapper.readValue(reader, Element.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendWho(int p) throws IOException {
        XStream xmlWriter = new XStream();
        String xmlString = xmlWriter.toXML(p + "");
        out.write(xmlString + '\n');
        out.flush();
    }

    public void send() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            StringWriter stringWriter = new StringWriter();
            mapper.writeValue(stringWriter, MyServer.elements.getLast());
            out.write(String.valueOf(stringWriter) + '\n');
            out.flush();
        } catch (IOException ignored) {
        }
    }

    public void sendWin(String s) {
        try {
            out.write(s + '\n');
            out.flush();
        } catch (IOException ignored) {
        }
    }
}
