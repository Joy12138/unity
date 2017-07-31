package com.warm.game.network;

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

public class NetChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel sc) throws Exception {
		ChannelPipeline pipeline = sc.pipeline();
		/*
		 * 使用官方编解码
		 * */
//		//用于解决半包和粘包问题
//		pipeline.addLast(new ProtobufVarint32FrameDecoder());
//		//反序列化指定的probuf
//		pipeline.addLast(new ProtobufDecoder(UserMsgProto.UserMsg.getDefaultInstance()));
//		//给序列化的字节数组加一个简单的标识字节长度的包头
//		pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
//		//用于对protobuf类型进行序列化
//		pipeline.addLast(new ProtobufEncoder());
		/*
		 * 使用自定义编解码器，解决单一protobuf
		 * */
		pipeline.addLast("decoder",new CustomProtobufDecoder());
		pipeline.addLast("encoder",new CustomProtobufEncoder());
		// 20170727先添加一个线程处理逻辑
		pipeline.addLast(new TimeServerHandler());
	}

}
