package com.warm.game.network;

public class Bootstrap {
	private static NetServer netServer;

	public static void main(String[] args) throws Exception {
		try {
			netServer = new NetServer();
			netServer.start(8080);
			System.out.println("监听成功");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
