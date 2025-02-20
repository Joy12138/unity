package com.warm.game.network;

import com.warm.game.protocol.user.UserMsgProto;
import com.warm.game.user.LoginBuilder;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeServerHandler extends ChannelHandlerAdapter {
	private LoginBuilder loginBuilder;

	public TimeServerHandler() {
		loginBuilder = new LoginBuilder();
	}

	public void channelRead(final ChannelHandlerContext ctx, Object msg) {
		UserMsgProto.UserMsg req = (UserMsgProto.UserMsg) msg;
		System.out.println("Service accept client subscribe req:[" +req.toString()+"]");
		boolean flag = false;
		if(req.getUserName().equals("zhj")){
			flag = true;
		}
		ctx.writeAndFlush(loginBuilder.createLoginMsg(flag));
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
