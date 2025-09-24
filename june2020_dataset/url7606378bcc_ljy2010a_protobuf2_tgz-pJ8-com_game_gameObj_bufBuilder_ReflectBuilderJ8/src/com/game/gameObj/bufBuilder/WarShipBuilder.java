package com.game.gameObj.bufBuilder; 

import com.game.gameObj.WarShip;
import com.game.protobuf.GameProto;
import com.game.protobuf.ModelAttr;
import com.game.protobuf.ModelAttr.WarShipAttr;

public class WarShipBuilder{ 

public static GameProto.GameAction getGameAction(String ActionType,WarShip arg_Origin){

		GameProto.GameObject.Builder gameGameObjectBuilder = GameProto.GameObject.newBuilder();

		ModelAttr.WarShip.Builder Builder = ModelAttr.WarShip.newBuilder();

				Builder.setWarShipType(arg_Origin.getWarShipType());
				Builder.setWarShipLevel(arg_Origin.getWarShipLevel());
				Builder.setWarShipSk1(arg_Origin.getWarShipSk1());
				Builder.setWarShipSk2(arg_Origin.getWarShipSk2());
		gameGameObjectBuilder.setCoord(CoordBuilder.buildCoord(arg_Origin.getCoor().getX(),arg_Origin.getCoor().getY(),arg_Origin.getCoor().getZ()));

		gameGameObjectBuilder.setGameObjId(arg_Origin.getGameObjId());

		gameGameObjectBuilder.setGameObjType(arg_Origin.getGameObjType());

		gameGameObjectBuilder.setWarShip(Builder);

		GameProto.GameAction.Builder gameActionBuilder = GameProto.GameAction.newBuilder();

		gameActionBuilder.setActionType(ActionType);

		gameActionBuilder.setGameObj(gameGameObjectBuilder.build());

		return gameActionBuilder.build();

		}

public static GameProto.GameAction getGameAction(WarShip arg_Origin,boolean isSyncCoor ,String ActionType,WarShipAttr... Attrs){

		GameProto.GameObject.Builder gameGameObjectBuilder = GameProto.GameObject.newBuilder();

		ModelAttr.WarShip.Builder Builder = ModelAttr.WarShip.newBuilder();

		for (int i = 0; i<Attrs.length ; i++ ){

			if(Attrs[i] == WarShipAttr.WarShipType){
				Builder.setWarShipType(arg_Origin.getWarShipType());
				continue;
			}
			if(Attrs[i] == WarShipAttr.WarShipLevel){
				Builder.setWarShipLevel(arg_Origin.getWarShipLevel());
				continue;
			}
			if(Attrs[i] == WarShipAttr.WarShipSk1){
				Builder.setWarShipSk1(arg_Origin.getWarShipSk1());
				continue;
			}
			if(Attrs[i] == WarShipAttr.WarShipSk2){
				Builder.setWarShipSk2(arg_Origin.getWarShipSk2());
				continue;
			}
		}

		if (isSyncCoor){

		gameGameObjectBuilder.setCoord(CoordBuilder.buildCoord(arg_Origin.getCoor().getX(),arg_Origin.getCoor().getY(),arg_Origin.getCoor().getZ()));

		}
		gameGameObjectBuilder.setGameObjId(arg_Origin.getGameObjId());

		gameGameObjectBuilder.setGameObjType(arg_Origin.getGameObjType());

		gameGameObjectBuilder.setWarShip(Builder);

		GameProto.GameAction.Builder gameActionBuilder = GameProto.GameAction.newBuilder();

		gameActionBuilder.setActionType(ActionType);

		gameActionBuilder.setGameObj(gameGameObjectBuilder.build());

		return gameActionBuilder.build();

		}

}
