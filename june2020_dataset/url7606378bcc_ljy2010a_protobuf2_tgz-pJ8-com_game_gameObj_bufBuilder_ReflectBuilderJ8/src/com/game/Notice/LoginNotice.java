package com.game.Notice;

import com.game.protobuf.GameProto;

public class LoginNotice {
	
	public void call(GameProto.GameMsg agr_GameMsg){
		System.out.println("LoginNotice call");
	}
	
}
