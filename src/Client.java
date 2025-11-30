import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080);
             InputStream inputStream = socket.getInputStream();
             OutputStream outputStream = socket.getOutputStream()) {
            System.out.println("Connected to server " + socket.getInetAddress() + ":" + socket.getPort());
            int response = 1;
            outputStream.write(response);
            outputStream.flush();
            System.out.println("Send (server): " + response);

            while ((response = inputStream.read()) != -1) {
                System.out.println("Accept (server): " + response);
                if (response >= 10) {
                    System.out.println("Limit reached.");
                    break;
                }
                outputStream.write(response++);
                System.out.println("Send (server): " + response);
                outputStream.flush();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
