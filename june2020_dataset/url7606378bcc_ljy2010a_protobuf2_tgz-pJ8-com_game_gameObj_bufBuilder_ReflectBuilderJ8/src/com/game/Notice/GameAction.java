package com.game.Notice;

import com.game.protobuf.GameProto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GameAction {

	public static void call(GameProto.GameMsg agr_GameMsg)throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException,
            SecurityException, InstantiationException, ClassNotFoundException{
		System.out.println("GameAction call");
		//TODO:  reflect to detail gameObj and func
        GameProto.GameAction gameAction= agr_GameMsg.getNotice().getGameAction();
        String actionType = gameAction.getActionType();
        String objType =    gameAction.getGameObj().getGameObjType();


        gameAction.getGameObj().getWarShip().getWarShipLevel();

        Class<?> c = Class.forName("com.game.Notice." + objType);

        Object invokeTester = c.newInstance();

        Method method = c.getMethod(actionType,agr_GameMsg.getClass());

        Object result = method.invoke(invokeTester, agr_GameMsg);

        System.out.println(result);

	}
	
}
