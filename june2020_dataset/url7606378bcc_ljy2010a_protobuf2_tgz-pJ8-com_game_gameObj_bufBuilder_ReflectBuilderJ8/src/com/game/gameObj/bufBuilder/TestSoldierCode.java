package com.game.gameObj.bufBuilder;

import java.util.EnumMap;

import com.game.gameObj.Soldier;
import com.game.protobuf.GameProto;
import com.game.protobuf.ModelAttr;
import com.game.protobuf.ModelAttr.SoldierAttr;

public class TestSoldierCode {
	
	public static GameProto.GameAction getGameAction(Soldier arg_Soldier,boolean isSyncCoor ,SoldierAttr... SoldierAttrs){	

		GameProto.GameObject.Builder gameGameObjectBuilder = GameProto.GameObject.newBuilder();
		
		ModelAttr.Soldier.Builder soldierBuilder = ModelAttr.Soldier.newBuilder();
		
		for (int i = 0; i<SoldierAttrs.length ; i++ ){
			if(SoldierAttrs[i] == SoldierAttr.SoldierLevel){
				soldierBuilder.setSoldierLevel(arg_Soldier.getSoldierLevel());
				continue;
			}
			if(SoldierAttrs[i] == SoldierAttr.SoldierType){
				soldierBuilder.setSoldierType(arg_Soldier.getSoldierType());
				continue;
			}
		}	
		
		if (isSyncCoor){
//			gameGameObjectBuilder.setCoord(SoldierBuilder.getGameAction(0, 0, 0));
		}
		
		System.out.println(soldierBuilder.getDescriptor().getFields().get(0).getJavaType());
		
		gameGameObjectBuilder.setGameObjId(0);	
		gameGameObjectBuilder.setSoldier(soldierBuilder);
		GameProto.GameAction.Builder gameActionBuilder = GameProto.GameAction.newBuilder();
		gameActionBuilder.setActionType("");
		gameActionBuilder.setGameObj(gameGameObjectBuilder.build());		
		return gameActionBuilder.build();
	}
	
	public static EnumMap<SoldierAttr ,String>  ParseSolder(){
		EnumMap<SoldierAttr ,Object> ObjAttrMap = new EnumMap<SoldierAttr ,Object>(SoldierAttr.class);
		return null;	
	}
	
	public static void main(String[] args){
		Soldier soldier = new Soldier();
		soldier.SoldierLevel = 1;
		getGameAction(soldier, false, SoldierAttr.SoldierLevel);
		
	}
	
	
	
}
