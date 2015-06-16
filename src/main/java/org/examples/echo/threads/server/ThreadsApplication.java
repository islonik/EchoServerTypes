package org.examples.echo.threads.server;

/**
 * @author Lipatov Nikita
 */
public class ThreadsApplication {

    // telnet localhost 8075
    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8075;
        }

        new ThreadsServer(port).run();
    }
}
