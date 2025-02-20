package com.warm.game.network.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NetClient {
	private EventLoopGroup group;
	private Channel bossChannel;
	public void connect(int port, String ip) throws Exception {
		group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
					 .channel(NioSocketChannel.class)
					 .option(ChannelOption.SO_KEEPALIVE, true)
					 .handler(new TimeClientHandlerInit());
			// Start the client
			bossChannel = bootstrap.connect(ip, port).sync().channel();
			//wait until the connection is closed
			bossChannel.closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
