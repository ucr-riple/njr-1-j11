package com.game.handle;

import java.lang.reflect.InvocationTargetException;

import com.game.gameObj.Soldier;
import com.game.gameObj.bufBuilder.GameMsgBuilder;
import com.game.gameObj.bufBuilder.ReflectBuilder;
import com.game.gameObj.bufBuilder.SoldierBuilder;
import com.game.protobuf.GameProto;
import com.game.protobuf.GameProto.GameMsg;
import com.game.protobuf.ModelAttr.SoldierAttr;
import com.google.protobuf.InvalidProtocolBufferException;

public class TestGameMsgHandler {

	/**
	 * @param args
	 * @throws InvalidProtocolBufferException 
	 * @throws InstantiationException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 * @throws NoSuchFieldException 
	 */
	public static void main(String[] args) throws InvalidProtocolBufferException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, ClassNotFoundException, NoSuchFieldException {
		// TODO Auto-generated method stub

		Soldier soldier = new Soldier(); 
		soldier.setGameObjId(12580);
	    soldier.setSoldierLevel(99);
	    soldier.setSoldierSk1(12);
	    soldier.setSoldierType(4545);
	    soldier.getCoor().setX(52463);
	       
		
		byte[] bytes= GameMsgBuilder.buildGameMsg(SoldierBuilder.getGameAction("update", soldier),"Notice.GameAction");
		
		GameMsg builder = GameProto.GameMsg.parseFrom(bytes);
		
		GameMsgHandler.GameMsgHandle(builder);
		
		//test the ReflectBuilder for buildGameAction
        byte[] Rbytes= GameMsgBuilder.buildGameMsg(ReflectBuilder.getGameAction(soldier,true,"update",SoldierAttr.SoldierSk1,SoldierAttr.SoldierLevel),"Notice.GameAction");
		
		GameMsg Rbuilder = GameProto.GameMsg.parseFrom(bytes);
		
		GameMsgHandler.GameMsgHandle(Rbuilder);
		
		
	}

}
