package com.warm.game.network.client;

import com.warm.game.protocol.user.UserMsgProto;
import com.warm.game.user.UserMessageBuilder;
import com.warm.game.user.entity.User;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class TimeClientHandler extends ChannelHandlerAdapter {
	private UserMessageBuilder userMessageBuilder;

	public TimeClientHandler() {
		userMessageBuilder = new UserMessageBuilder();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (1)
		User user = new User();
		user.setPassword(123);
		user.setUserName("zhj");
		ctx.write(userMessageBuilder.createUserMsg(user));
		ctx.flush();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		UserMsgProto.chkLoginMsg req = (UserMsgProto.chkLoginMsg)msg;
		System.out.println("Receive server response:[" + req.toString() + "]");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Client Read complete");
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
}
