package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import utils.TextIO;

public class Client {

    /**
     * Main method which is responsible for creating a socket and connecting to the
     * server.
     * 
     * @throws IOException when I/O error occurs
     * 
     */
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        try {

            Socket socket = setup();
            DataInputStream datainput = new DataInputStream(socket.getInputStream());
            DataOutputStream dataoutput = new DataOutputStream(socket.getOutputStream());
            while (true) {
                System.out.println(datainput.readUTF());
                String message = scanner.nextLine();
                dataoutput.writeUTF(message);
                String answer = datainput.readUTF();
                System.out.println(answer);
            }

        } catch (EOFException e) {
            System.out.println("Connection closed by pressing x, or the server has unexpectedly quit");
            scanner.close();
        } catch (SocketException e) {
            System.out.println("Server has quit unexpectedly, or you pressed x while in a game"
                    + ", or you probably closed the connection after playing a game");
            scanner.close();
        }
    }

    /**
     * Setup method to create a socket in a user friendly while loop.
     * 
     * @return Socket socket
     */
    public static Socket setup() {
        Scanner scanner = new Scanner(System.in);
        Socket socket = null;
        while (socket == null) {
            System.out.println("Please give the host IP address..");
            String host = scanner.nextLine();
            int port = getInt();
            try {
                System.out.println("Attempting to connect to IP.. " + host);
                socket = new Socket(host, port);
            } catch (Exception e) {
                System.out.println("Could not connect to.. " + host + " with port " + port);
                System.out.println("Would you like to try again?");
                boolean maybe = TextIO.getlnBoolean();
                if (maybe == false) {
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                }
            }
        }
        return socket;
    }

    /**
     * getInt method is accessed by setup(), it asks the user to give an integer in
     * order to setup the port.
     * 
     * @return integer representing port number
     */
    public static int getInt() {
        int port = 0;
        while (port == 0) {
            System.out.println("Please give an integer for the server port");
            port = TextIO.getInt();

        }

        return port;
    }
}
