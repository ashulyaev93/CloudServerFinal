package com.flamexander.netty.servers.serialization;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class CloudServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client connected...");
    }

    //здесь происходит чтение запроса клиента;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg == null) {
                return;
            }
            System.out.println(msg.getClass());
            if (msg instanceof FileMessage) {
                FileMessage fm = (FileMessage) msg;
                String fileName = fm.getFileName();
                boolean down = fm.getPrefix().equals("download");
                System.out.println(down ? "Downloading" : "Uploading" + " " + fileName + "...");
                if(down) {
                    ctx.writeAndFlush(Utils.createFileMessage(fileName, "download"));
                }
                else {
                    Utils.writeFileMessage(fm);
                    ctx.writeAndFlush(new FileMessage(fileName));
                }
            } else {
                System.out.printf("Server received wrong object!");
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
