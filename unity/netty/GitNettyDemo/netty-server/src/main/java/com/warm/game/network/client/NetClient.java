package com.warm.game.network.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NetClient {
	private EventLoopGroup group;
	public void connect(int port, String ip) throws Exception {
		group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
					 .channel(NioSocketChannel.class)
					 .option(ChannelOption.TCP_NODELAY, true)
					 .handler(new TimeClientHandlerInit());
			bootstrap.connect(ip, port).sync().channel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
