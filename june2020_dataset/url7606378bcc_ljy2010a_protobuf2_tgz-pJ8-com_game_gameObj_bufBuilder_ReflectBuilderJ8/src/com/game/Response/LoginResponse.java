package com.game.Response;

import com.game.protobuf.GameProto;
import com.game.util.UserInfo;

public class LoginResponse {

	public UserInfo UserInfo;
	
	public int actionRslCode; 	
	
	public String actionRslMsg;
	
	public void call(GameProto.GameMsg agr_GameMsg){
		System.out.println("LoginResponse call");		
	}
}
