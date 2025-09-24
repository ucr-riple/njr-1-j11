package com.game.Notice;

import com.game.protobuf.GameProto;

/**
 * Created with IntelliJ IDEA.
 * User: liangjunyu
 * Date: 13-12-2
 * Time: ÉĎÎç9:57
 * To change this template use File | Settings | File Templates.
 */
public class Soldier {

 	public void append(GameProto.GameMsg agr_GameMsg){
		System.out.println("Soldier append");
	}

	public void delete(GameProto.GameMsg agr_GameMsg){
		System.out.println("Soldier delete");
	}

	public void update(GameProto.GameMsg agr_GameMsg){
		System.out.println("Soldier update");
	}

    public void get(GameProto.GameMsg agr_GameMsg){
        System.out.println("Soldier get");
    }
}
