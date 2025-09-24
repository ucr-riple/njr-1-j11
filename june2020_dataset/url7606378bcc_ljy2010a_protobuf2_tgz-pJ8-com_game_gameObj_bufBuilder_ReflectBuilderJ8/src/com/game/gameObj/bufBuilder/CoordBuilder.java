package com.game.gameObj.bufBuilder;

import com.game.protobuf.GameProto;
import com.game.protobuf.GameProto.Coordinate;

public class CoordBuilder {

	public static GameProto.Coordinate buildCoord(long x,long y,long z){
		GameProto.Coordinate.Builder builder = GameProto.Coordinate.newBuilder();
		builder.setX(x);
		builder.setY(y);
		builder.setZ(z);
		return builder.build();		
	}

	public static Coordinate CoordBuild(long x, long y, long z) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
