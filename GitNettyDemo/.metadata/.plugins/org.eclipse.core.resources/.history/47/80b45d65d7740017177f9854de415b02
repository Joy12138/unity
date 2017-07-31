package com.warm.game.user;

import com.warm.game.protocol.user.UserMsgProto;
import com.warm.game.user.entity.User;

public class UserMessageBuilder {
	public UserMsgProto.UserMsg createUserMsg(User user){
		UserMsgProto.UserMsg.Builder userBuilder = UserMsgProto.UserMsg.newBuilder();
		userBuilder.setUserName(user.getUserName());
		userBuilder.setPassword(user.getPassword());
		return userBuilder.build();
	}
}
