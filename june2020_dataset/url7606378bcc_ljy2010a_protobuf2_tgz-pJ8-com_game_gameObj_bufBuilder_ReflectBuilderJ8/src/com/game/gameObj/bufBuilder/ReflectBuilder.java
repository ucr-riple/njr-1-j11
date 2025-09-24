package com.game.gameObj.bufBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.game.gameObj.Soldier;
import com.game.protobuf.GameProto;
import com.game.protobuf.ModelAttr.SoldierAttr;

public class ReflectBuilder {

    public final static String GameObjTypeField = "GameObjType";

    public final static String GameObjIdField = "GameObjId";

    public final static String ModelAttrPackage = "com.game.protobuf.ModelAttr$";

    public static GameProto.GameAction getGameAction(Object arg_Origin, boolean isSyncCoor, String ActionType, Object... Attrs) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, NoSuchFieldException, InstantiationException {

        GameProto.GameObject.Builder gameGameObjectBuilder = GameProto.GameObject.newBuilder();

        Method getGameObjTypeMethod = arg_Origin.getClass().getMethod("get" + GameObjTypeField);

        String GameObjType = (String) getGameObjTypeMethod.invoke(arg_Origin);

        System.out.println("GameObjType : " + GameObjType);

        Class<?> build = Class.forName(ModelAttrPackage + GameObjType);
        System.out.println(build.getMethod("newBuilder"));

        Object GameObjBuilder = build.getMethod("newBuilder").invoke(build);

        for (int i = 0; i < Attrs.length; i++) {
            Method getEnumNameMethod = Attrs[i].getClass().getMethod("name");

            String EnumName = (String) getEnumNameMethod.invoke(Attrs[0]);

            System.out.println("EnumName : " + EnumName);

            String getMethodName = "get" + EnumName;
            String setMethodName = "set" + EnumName;
            Class<?> Type = null;

            for (int k = 0; k < arg_Origin.getClass().getFields().length; k++) {
                if (arg_Origin.getClass().getFields()[k].getName().equals(EnumName)) {
                    Type = arg_Origin.getClass().getFields()[k].getType();
                }
            }


            for (int j = 0; j < GameObjBuilder.getClass().getMethods().length; j++) {

                String BuilderGetMethodName = GameObjBuilder.getClass().getMethods()[j].getName();

                if (setMethodName.equals(BuilderGetMethodName)) {

                    GameObjBuilder.getClass().getMethod(setMethodName, Type).invoke(
                            GameObjBuilder, arg_Origin.getClass().getMethod(getMethodName).invoke(arg_Origin));
                    System.out.println(GameObjBuilder.getClass().getMethods()[j].getName());

                    break;
                }

            }

        }
        Object CoordinateObj = arg_Origin.getClass().getMethod("getCoor").invoke(arg_Origin);


        gameGameObjectBuilder.setCoord(
                CoordBuilder.buildCoord(
                        (Long) CoordinateObj.getClass().getMethod("getX").invoke(CoordinateObj),
                        (Long) CoordinateObj.getClass().getMethod("getY").invoke(CoordinateObj),
                        (Long) CoordinateObj.getClass().getMethod("getZ").invoke(CoordinateObj))
        );

        gameGameObjectBuilder.setGameObjId((Integer) arg_Origin.getClass().getMethod("get"+GameObjIdField).invoke(arg_Origin));

        gameGameObjectBuilder.setGameObjType((String) arg_Origin.getClass().getMethod("get"+GameObjTypeField).invoke(arg_Origin));

        gameGameObjectBuilder.getClass().getMethod("set" + GameObjType, Class.forName(GameObjBuilder.getClass().getName())).invoke(gameGameObjectBuilder, GameObjBuilder);

        GameProto.GameAction.Builder gameActionBuilder = GameProto.GameAction.newBuilder();

        gameActionBuilder.setActionType(ActionType);

        gameActionBuilder.setGameObj(gameGameObjectBuilder.build());

        return gameActionBuilder.build();

    }

    public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, NoSuchFieldException, InstantiationException {

        Soldier soldier = new Soldier();
        soldier.setGameObjId(12580);
        soldier.setSoldierLevel(99);
        soldier.setSoldierSk1(12);
        soldier.setSoldierType(4545);
        soldier.getCoor().setX(52463);
        soldier.getCoor().setY(52463);


        System.out.println(getGameAction(soldier, true, "update", SoldierAttr.SoldierSk1, SoldierAttr.SoldierLevel).toString());

    }

}
