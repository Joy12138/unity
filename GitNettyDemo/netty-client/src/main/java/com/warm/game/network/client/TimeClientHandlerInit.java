package com.warm.game.network.client;

import com.warm.game.protocol.user.UserMsgProto;
import com.warm.game.tool.CustomProtobufDecoder;
import com.warm.game.tool.CustomProtobufEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class TimeClientHandlerInit extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		/*
		 * 使用官方编解码
		 * */
//		pipeline.addLast(new ProtobufVarint32FrameDecoder());
//		pipeline.addLast(new ProtobufDecoder(UserMsgProto.UserMsg.getDefaultInstance()));
//		pipeline.addLast(new ProtobufDecoder(UserMsgProto.chkLoginMsg.getDefaultInstance()));
//		pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
//		pipeline.addLast(new ProtobufEncoder());
		/*
		 * 使用自定义编解码器，解决单一protobuf20170730
		 * */
		pipeline.addLast("decoder",new CustomProtobufDecoder());
		pipeline.addLast("encoder",new CustomProtobufEncoder());
		pipeline.addLast(new TimeClientHandler());
	}
}
