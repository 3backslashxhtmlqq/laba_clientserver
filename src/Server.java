import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket socket = new ServerSocket(8080)) {
            System.out.println("Server started");
            while (true) {
                Socket client = socket.accept();
                System.out.println("Client: " + client.getInetAddress());

                new Thread(new ClientListener(client)).start();
            }
    } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static class ClientListener implements Runnable {

        public Socket socket;

        public ClientListener(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (InputStream is = socket.getInputStream();
                 OutputStream os = socket.getOutputStream()) {

                int temp;

                while ((temp = is.read()) != -1) {
                    System.out.println("Accept (client): " + temp);
                    temp++;
                    os.write(temp);
                    os.flush();
                    System.out.println("Send (client): " + temp);

                    if (temp >= 10) {
                        System.out.println("Limit reached.");
                        break;
                    }

                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }
}


