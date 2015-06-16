package org.examples.echo.threads.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

/**
 * @author Lipatov Nikita
 */
public class ServerThread extends Thread {
    private static final Logger log = LoggerFactory.getLogger(ServerThread.class);

    private BufferedReader inStream = null;
    private BufferedWriter outStream = null;

    public ServerThread(Socket socket) {
        try {
            this.inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.outStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException ioe) {
            log.error(ioe.getLocalizedMessage(), ioe);
        }
    }

    @Override
    public void run() {
        try {
            // all incoming requests
            while (true) {
                String request = inStream.readLine();

                String response = null;
                boolean close = false;
                if (request.isEmpty()) {
                    response = "Please type something.\r\n";
                } else if ("bye".equals(request.toLowerCase())) {
                    response = "Have a good day!\r\n";
                    close = true;
                } else {
                    response = "Did you say '" + request + "'?\r\n";
                }

                System.out.println("resp = " + response);

                this.outStream.write(response);
                this.outStream.flush();

                if (close) {
                    throw new InterruptedException();
                }
            }
        } catch (IOException ioe) {
            log.error("run method: " + ioe.getLocalizedMessage(), ioe);
        } catch (InterruptedException ie) {
            log.error("Close: " + ie.getLocalizedMessage(), ie);
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException ioe) {
                System.err.println(ioe);
                log.error(ioe.getMessage(), ioe);
            }
        }
    }

}
