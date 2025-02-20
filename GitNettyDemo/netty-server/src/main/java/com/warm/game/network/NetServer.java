package com.warm.game.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NetServer {
	private Channel bossChannel;
	private NioEventLoopGroup bossGroup;
	private NioEventLoopGroup workerGroup;
	public void start(int port){
		try{
			this.bossGroup = new NioEventLoopGroup();
			this.workerGroup = new NioEventLoopGroup();
			ServerBootstrap bootstrap = new ServerBootstrap().group(this.bossGroup,this.workerGroup)
															 .channel(NioServerSocketChannel.class)
															 .option(ChannelOption.SO_BACKLOG, 128)
															 .childOption(ChannelOption.SO_KEEPALIVE, true)
															 .childHandler(new NetChannelInitializer());
			//为了安全的退出对这个端口的监听状态
			this.bossChannel = bootstrap.bind(port).sync().channel();
			//shut down your server
			//wait until the server socket is closed
			//阻塞等待关闭
			bossChannel.closeFuture().sync();
		}catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	public void stop(){
		this.bossGroup.shutdownGracefully();
		this.workerGroup.shutdownGracefully();
		this.bossChannel.close().awaitUninterruptibly();
	}
}
