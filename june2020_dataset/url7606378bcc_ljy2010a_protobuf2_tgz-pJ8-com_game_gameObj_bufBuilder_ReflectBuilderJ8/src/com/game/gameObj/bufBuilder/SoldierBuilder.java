package com.game.gameObj.bufBuilder; 

import com.game.gameObj.Soldier;
import com.game.protobuf.GameProto;
import com.game.protobuf.ModelAttr;
import com.game.protobuf.ModelAttr.SoldierAttr;

public class SoldierBuilder{ 

public static GameProto.GameAction getGameAction(String ActionType,Soldier arg_Origin){

		GameProto.GameObject.Builder gameGameObjectBuilder = GameProto.GameObject.newBuilder();

		ModelAttr.Soldier.Builder Builder = ModelAttr.Soldier.newBuilder();

				Builder.setSoldierType(arg_Origin.getSoldierType());
				Builder.setSoldierLevel(arg_Origin.getSoldierLevel());
				Builder.setSoldierSk1(arg_Origin.getSoldierSk1());
				Builder.setSoldierSk2(arg_Origin.getSoldierSk2());
		gameGameObjectBuilder.setCoord(CoordBuilder.buildCoord(arg_Origin.getCoor().getX(),arg_Origin.getCoor().getY(),arg_Origin.getCoor().getZ()));

		gameGameObjectBuilder.setGameObjId(arg_Origin.getGameObjId());

		gameGameObjectBuilder.setGameObjType(arg_Origin.getGameObjType());

		gameGameObjectBuilder.setSoldier(Builder);

		GameProto.GameAction.Builder gameActionBuilder = GameProto.GameAction.newBuilder();

		gameActionBuilder.setActionType(ActionType);

		gameActionBuilder.setGameObj(gameGameObjectBuilder.build());

		return gameActionBuilder.build();

		}

public static GameProto.GameAction getGameAction(Soldier arg_Origin,boolean isSyncCoor ,String ActionType,SoldierAttr... Attrs){

		GameProto.GameObject.Builder gameGameObjectBuilder = GameProto.GameObject.newBuilder();

		ModelAttr.Soldier.Builder Builder = ModelAttr.Soldier.newBuilder();

		for (int i = 0; i<Attrs.length ; i++ ){

			if(Attrs[i] == SoldierAttr.SoldierType){
				Builder.setSoldierType(arg_Origin.getSoldierType());
				continue;
			}
			if(Attrs[i] == SoldierAttr.SoldierLevel){
				Builder.setSoldierLevel(arg_Origin.getSoldierLevel());
				continue;
			}
			if(Attrs[i] == SoldierAttr.SoldierSk1){
				Builder.setSoldierSk1(arg_Origin.getSoldierSk1());
				continue;
			}
			if(Attrs[i] == SoldierAttr.SoldierSk2){
				Builder.setSoldierSk2(arg_Origin.getSoldierSk2());
				continue;
			}
		}

		if (isSyncCoor){

		gameGameObjectBuilder.setCoord(CoordBuilder.buildCoord(arg_Origin.getCoor().getX(),arg_Origin.getCoor().getY(),arg_Origin.getCoor().getZ()));

		}
		gameGameObjectBuilder.setGameObjId(arg_Origin.getGameObjId());

		gameGameObjectBuilder.setGameObjType(arg_Origin.getGameObjType());

		gameGameObjectBuilder.setSoldier(Builder);

		GameProto.GameAction.Builder gameActionBuilder = GameProto.GameAction.newBuilder();

		gameActionBuilder.setActionType(ActionType);

		gameActionBuilder.setGameObj(gameGameObjectBuilder.build());

		return gameActionBuilder.build();

		}

}
