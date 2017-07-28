package com.warm.game.network.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter {
	private final ByteBuf message;

	public TimeClientHandler() {
		byte[] req = "send success".getBytes();
		message = Unpooled.buffer(req.length);
		message.writeBytes(req);
	}

	public void channelActive(ChannelHandlerContext ctx) {
		ctx.writeAndFlush(message);
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
	}
}
