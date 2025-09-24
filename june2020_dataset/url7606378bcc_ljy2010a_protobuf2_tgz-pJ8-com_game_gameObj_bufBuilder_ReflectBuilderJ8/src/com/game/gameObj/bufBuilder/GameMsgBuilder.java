package com.game.gameObj.bufBuilder;

import com.game.protobuf.GameProto;


public class GameMsgBuilder {

	public static byte[] buildGameMsg(GameProto.GameAction arg_GameAction,String MsgType) {

		GameProto.GameMsg.Builder builder = GameProto.GameMsg.newBuilder();
		GameProto.Notice.Builder noticeBuilder = GameProto.Notice.newBuilder();

		noticeBuilder.setGameAction(arg_GameAction);

		builder.setMsgLength(0);
		builder.setNotice(noticeBuilder);
		builder.setMsgSN(0);
		builder.setMsgType(MsgType);

		return builder.build().toByteArray();
	}

	public static byte[] buildGameMsg(GameProto.Notice arg_Notice,String MsgType) {
		GameProto.GameMsg.Builder builder = GameProto.GameMsg.newBuilder();

		builder.setMsgLength(0);
		builder.setNotice(arg_Notice);
		builder.setMsgSN(0);
		builder.setMsgType(MsgType);

		return builder.build().toByteArray();
	}

	public static byte[] buildGameMsg(GameProto.Request arg_Request,String MsgType) {
		GameProto.GameMsg.Builder builder = GameProto.GameMsg.newBuilder();

		builder.setMsgLength(0);
		builder.setRequest(arg_Request);
		builder.setMsgSN(0);
		builder.setMsgType(MsgType);

		return builder.build().toByteArray();
	}

	public static byte[] buildGameMsg(GameProto.Response arg_Response,String MsgType) {
		GameProto.GameMsg.Builder builder = GameProto.GameMsg.newBuilder();

		builder.setMsgLength(0);
		builder.setResponse(arg_Response);
		builder.setMsgSN(0);
		builder.setMsgType(MsgType);

		return builder.build().toByteArray();
	}
}
