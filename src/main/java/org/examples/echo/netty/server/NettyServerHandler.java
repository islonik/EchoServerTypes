package org.examples.echo.netty.server;

import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.net.InetAddress;
import java.util.Date;

/**
 * @author Lipatov Nikita
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // Send greeting for a new connection.
        ctx.write("Welcome to " + InetAddress.getLocalHost().getHostName() + "!\r\n");
        ctx.write("It is " + new Date() + " now.\r\n");
        ctx.flush();

        channels.add(ctx.channel());
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String request) throws Exception {
        // Generate and write a response.
        String response;
        boolean close = false;
        if (request.isEmpty()) {
            response = "Please type something.\r\n";
        } else if ("bye".equals(request.toLowerCase())) {
            response = "Have a good day!\r\n";
            close = true;
        } else if("hello".equals(request.toLowerCase())) {
            sendToAll(ctx, "hello everybody!");
            return;
        } else {
            response = "Did you say '" + request + "'?\r\n";
        }
        // We do not need to write a ChannelBuffer here.
        // We know the encoder inserted at TelnetPipelineFactory will do the conversion.
        ChannelFuture future = ctx.writeAndFlush(response);
        // Close the connection after sending 'Have a good day!'
        // if the client has sent 'bye'.
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    public void sendToAll(ChannelHandlerContext ctx, String msg) {
        for (Channel c: channels) {
            if (c != ctx.channel()) {
                c.writeAndFlush("[" + ctx.channel().remoteAddress() + "] " + msg + '\n');
            } else {
                c.writeAndFlush("[you] " + msg + '\n');
            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}

