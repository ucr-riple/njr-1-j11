package com.game.handle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.game.protobuf.GameProto;

public class GameMsgHandler {

	public static void GameMsgHandle(GameProto.GameMsg agr_GameMsg)
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException,
			SecurityException, InstantiationException, ClassNotFoundException {
		
		String ActionPath = agr_GameMsg.getMsgType();
		System.out.println("ActionPath : " + ActionPath);
		
// TODO: maybe get more detail value for the method
//		String[] subPaths = ActionPath.split("\\.");
//		for (int i = 0; i < subPaths.length; i++) {
//			System.out.println(subPaths[i]);
//		}

		Class<?> c = Class.forName("com.game." + ActionPath);

		Object invokeTester = c.newInstance();

		Method method = c.getMethod("call",agr_GameMsg.getClass());
		
		Object result = method.invoke(invokeTester, agr_GameMsg);

		System.out.println(result);

// test for the static func
//		Class<?> debugClass = Class.forName("com.game."+ActionPath);
//      debugClass.getMethod("call",agr_GameMsg.getClass()).invoke(debugSwitcherClass,agr_GameMsg);
	}
}

