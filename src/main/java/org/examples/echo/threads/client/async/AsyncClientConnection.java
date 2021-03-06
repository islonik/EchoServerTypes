package org.examples.echo.threads.client.async;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * @author Lipatov Nikita
 */
public class AsyncClientConnection extends Thread {

    private Socket socket = null;
    private BufferedReader inStream = null;
    private PrintWriter outStream = null;

    public AsyncClientConnection(String host, int port) {
        try {
            socket = new Socket(host, port);
            outStream = new PrintWriter(socket.getOutputStream(), true);
            inStream  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception error) {
            System.err.println(error);
        }
    }

    /**
     * Method sends messages / commands to server.
     * @param message Message from user / to server.
     */
    public void flush(String message) {
        try {
            outStream.println(message);
        } catch (Exception error) {
            System.err.println("flush method: " + error);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = inStream.readLine();
                if(message == null) {
                    return;
                }
                System.out.println(message);
            }
        } catch (EOFException error) {
            System.out.println("Connection to the server was lost");
        } catch (SocketException error) {
            System.out.println("The server was shut down");
        } catch (Exception error) {
            System.err.println("Fatal fail in run method because: " + error);
        } finally {
            try {
                kill();
            } catch (Exception error) {
                System.err.println("finally of run method:" + error);
            }
        }
    }

    /**
     * Method kills the object of client connection.
     */
    public void kill() {
        try {

            if(socket != null) {
                if(!socket.isClosed()) {
                    socket.close();
                }
                socket = null;
            }
            this.interrupt();
        } catch (Exception error) {
            System.err.println("Close method:" + error);
        }
    }
}
