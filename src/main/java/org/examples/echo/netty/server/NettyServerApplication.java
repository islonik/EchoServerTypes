package org.examples.echo.netty.server;

/**
 * @author Lipatov Nikita
 * Example was took from official documentation.
 */
public class NettyServerApplication {

    // telnet localhost 8078
    public static void main(String[] args) throws Exception {
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8076;
        }

        new NettyServer(port).run();
    }

}
