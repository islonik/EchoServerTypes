package org.examples.echo.threads.client.async;

import java.io.IOException;

/**
 * AsyncThreadsClientApplication
 * @author Lipatov Nikita
 */
public class AsyncTCApplication {

    // telnet localhost 8075
    public static void main(String[] args) throws IOException {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8075;
        }

        new AsyncTClient("localhost", port).run();
    }
}
