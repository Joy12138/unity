package com.warm.game.user;

import com.warm.game.protocol.user.UserMsgProto;

public class LoginBuilder {
	public UserMsgProto.chkLoginMsg createLoginMsg(boolean flag) {
		UserMsgProto.chkLoginMsg.Builder build = UserMsgProto.chkLoginMsg.newBuilder();
		build.setIsLogin(flag);
		return build.build();
	}
}
