package com.game.gameObj.bufBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class BufBuilderMake {

	public static String[] typeNames = {"Soldier","WarShip"};
	
	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public static String FilePath = "src/com/game/gameObj/bufBuilder/";
	
	public static String GameObjId  = "GameObjId";
	
	public static String GameObjType  = "GameObjType";
	
	public static String Coor = "Coor";
	
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		for (int i = 0 ;i<typeNames.length;i++){
			PrintWriter pw = new PrintWriter( new FileWriter( FilePath+ typeNames[i] + "Builder.java" ) );
	        pw.print(makeBuilder(typeNames[i]));
	        pw.close();
		}
	}
	
	public static String makeBuilder(String arg_TypeName) throws ClassNotFoundException{
		String TypeName = arg_TypeName;
		String AttrName = arg_TypeName+"Attr";
		String Head = "";
		
		Head +=  "package com.game.gameObj.bufBuilder; \n\n";
		
		Head +=  "import com.game.gameObj."+TypeName+";\n";
		
		Head +=  "import com.game.protobuf.GameProto;\n";
		
		Head +=  "import com.game.protobuf.ModelAttr;\n";
		
		Head +=  "import com.game.protobuf.ModelAttr."+AttrName+";\n\n";  
		
		Head +=  "public class "+TypeName+"Builder{ \n\n";
		
		Head +=  BuildFunc(TypeName,true);
		
		Head +=  BuildFunc(TypeName,false);
        
        Head +="}\n";
        
		return Head;		
	}
	
	
	public static String BuildFunc(String arg_TypeName,boolean isAllFunc) throws ClassNotFoundException{
		String TypeName = arg_TypeName;
		String AttrName = arg_TypeName+"Attr";
		String Head = "";
				
		if(!isAllFunc){
			Head +=  "public static GameProto.GameAction getGameAction("+TypeName+" arg_Origin,boolean isSyncCoor ,String ActionType,"+AttrName+"... Attrs){\n\n";	
		}else{
			Head +=  "public static GameProto.GameAction getGameAction(String ActionType,"+TypeName+" arg_Origin){\n\n"; 
		}				 

		Head +=  "\t\tGameProto.GameObject.Builder gameGameObjectBuilder = GameProto.GameObject.newBuilder();\n\n";
		
        Head +=  "\t\tModelAttr."+TypeName+".Builder Builder = ModelAttr."+TypeName+".newBuilder();\n\n";
        
        if(!isAllFunc){
        	Head +=  "\t\tfor (int i = 0; i<Attrs.length ; i++ ){\n\n";
        }
        @SuppressWarnings("rawtypes")
		Class c = Class.forName("com.game.gameObj."+TypeName); 
       
        for(int i = 0 ; i < c.getFields().length; i++){
        	//TODO: GameObjId , gameObjType is not use here
        	
//        	System.out.println(c.getFields()[i].getName());
//        	System.out.println(c.getFields()[i].getType().getName());
        	String FieldsName = c.getFields()[i].getName();
        	
        	if(FieldsName == GameObjId || FieldsName == GameObjType || FieldsName == Coor){
        		continue;
        	}
        	
        	String getMethodName = "get"+FieldsName;
        	String setMethodName = "set"+FieldsName;
        	
        	if(!isAllFunc){
        		Head += "\t\t\tif(Attrs[i] == "+AttrName+"."+FieldsName+"){\n";
        	}       	
        	
        	Head += "\t\t\t\tBuilder."+setMethodName+"(arg_Origin."+getMethodName+"());\n";
        	
        	if(!isAllFunc){
        		Head += "\t\t\t\tcontinue;\n";            	
            	Head += "\t\t\t}\n";
        	}        	        	
        }
        if(!isAllFunc){
        	Head +=  "\t\t}\n\n";    
        }
        if(!isAllFunc){
        	Head +=  "\t\tif (isSyncCoor){\n\n"; 
        }
        
        //TODO SetCoord
        Head +=  "\t\tgameGameObjectBuilder.setCoord(CoordBuilder.buildCoord(arg_Origin.getCoor().getX(),arg_Origin.getCoor().getY(),arg_Origin.getCoor().getZ()));\n\n";
        
        if(!isAllFunc){
        	Head +=  "\t\t}\n";
        }
        
        Head +=  "\t\tgameGameObjectBuilder.set"+GameObjId+"(arg_Origin.get"+GameObjId+"());\n\n";
        
        Head +=  "\t\tgameGameObjectBuilder.set"+GameObjType+"(arg_Origin.get"+GameObjType+"());\n\n";
        
        Head +=  "\t\tgameGameObjectBuilder.set"+TypeName+"(Builder);\n\n";
        
        Head +=  "\t\tGameProto.GameAction.Builder gameActionBuilder = GameProto.GameAction.newBuilder();\n\n";
        //TODO: SetActionType
        Head +=  "\t\tgameActionBuilder.setActionType(ActionType);\n\n";
        
        Head +=  "\t\tgameActionBuilder.setGameObj(gameGameObjectBuilder.build());\n\n";
        
        Head +=  "\t\treturn gameActionBuilder.build();\n\n";  
        
        Head +=  "\t\t}\n\n";		
		
		return Head;		
	}

}
