package com.undi.javascheme;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Set;

import com.undi.util.Reflector;


public class SchemeNatives {
  //Native Procedures:
  
  public static abstract class NativeProc{
    public abstract SchemeObject call(SchemeObject args);
    public int getArity() { return -1; }
  }
  
  //Math:
  public static final SchemeObject add = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      double result = 0;
      while(!args.isEmptyList()){
        result += args.getCar().getNumber();
        args = args.getCdr();
      }
      return SchemeObject.makeNumber(result);
    }    
  });
  public static final SchemeObject sub = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      double result = args.getCar().getNumber();
      args = args.getCdr();
      while(!args.isEmptyList()){
        result -= args.getCar().getNumber();
        args = args.getCdr();
      }
      return SchemeObject.makeNumber(result);
    }
  });
  public static final SchemeObject mult = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      double result = 1;
      while(!args.isEmptyList()){
        result *= args.getCar().getNumber();
        args = args.getCdr();
      }
      return SchemeObject.makeNumber(result);
    }
  });
  public static final SchemeObject quotient = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      long result = (long)args.getCar().getNumber();
      args = args.getCdr();
      while(!args.isEmptyList()){
        result /= args.getCar().getNumber();
        args = args.getCdr();
      }
      return SchemeObject.makeNumber(result);
    }
  });
  
  public static final SchemeObject div = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      double result = args.getCar().getNumber();
      args = args.getCdr();
      while(!args.isEmptyList()){
        result /= args.getCar().getNumber();
        args = args.getCdr();
      }
      return SchemeObject.makeNumber(result);
    }
  });
  
  public static final SchemeObject mod = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      long result = (long)args.getCar().getNumber();
      long remainder = 0;
      args = args.getCdr();
      while(!args.isEmptyList()){
        remainder = (long) (result % args.getCar().getNumber());
        result /= args.getCar().getNumber();
        args = args.getCdr();
      }
      return SchemeObject.makeNumber(remainder);
    }
  });
  public static final SchemeObject sqrt = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public int getArity() { return 1; }
    @Override
    public SchemeObject call(SchemeObject args) {
      double result = Math.sqrt(args.getCar().getNumber());
      return SchemeObject.makeNumber(result);
    }    
  });
  
  //Numbers:
  public static final SchemeObject numEql = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      long firstNum = (long)args.getCar().getNumber();
      args = args.getCdr();
      while(!args.isEmptyList()){
        if(firstNum != (long)args.getCar().getNumber()){
          return SchemeObject.FALSE;
        }
        args = args.getCdr();
      }
      return SchemeObject.TRUE;
    }
  });
  
  public static final SchemeObject greaterThan = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      double curNum = args.getCar().getNumber();
      args = args.getCdr();
      while(!args.isEmptyList()){
        if(curNum <= args.getCar().getNumber()){
          return SchemeObject.FALSE;
        }
        curNum = args.getCar().getNumber();
        args = args.getCdr();
      }
      return SchemeObject.TRUE;
    }
  });
  public static final SchemeObject greaterThanEqual = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      double curNum = args.getCar().getNumber();
      args = args.getCdr();
      while(!args.isEmptyList()){
        if(curNum < args.getCar().getNumber()){
          return SchemeObject.FALSE;
        }
        curNum = args.getCar().getNumber();
        args = args.getCdr();
      }
      return SchemeObject.TRUE;
    }
  });
  
  public static final SchemeObject lessThan = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      double curNum = args.getCar().getNumber();
      args = args.getCdr();
      while(!args.isEmptyList()){
        if(curNum >= args.getCar().getNumber()){
          return SchemeObject.FALSE;
        }
        curNum = args.getCar().getNumber();
        args = args.getCdr();
      }
      return SchemeObject.TRUE;
    }
  });
  public static final SchemeObject lessThanEqual = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      double curNum = args.getCar().getNumber();
      args = args.getCdr();
      while(!args.isEmptyList()){
        if(curNum > args.getCar().getNumber()){
          return SchemeObject.FALSE;
        }
        curNum = args.getCar().getNumber();
        args = args.getCdr();
      }
      return SchemeObject.TRUE;
    }
  });
  
  //List ops
  public static final SchemeObject cons = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return SchemeObject.cons(args.getCar(), SchemeObject.cadr(args));
    }
  });
  public static final SchemeObject car = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return SchemeObject.caar(args);
    }
  });
  public static final SchemeObject cdr = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return SchemeObject.cdar(args);
    }
  });
  public static final SchemeObject length = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return SchemeObject.makeNumber(args.getCar().getListLength());
    }
  });
  public static final SchemeObject setCar = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      args.getCar().setCar(SchemeObject.cadr(args));
      return SchemeObject.OK_SYMBOL;
    }
  });
  public static final SchemeObject setCdr = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      args.getCar().setCdr(SchemeObject.cadr(args));
      return SchemeObject.OK_SYMBOL;
    }
  });
  public static final SchemeObject list = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return SchemeObject.cons(args.getCar(), args.getCdr());
    }
  });
  public static final SchemeObject nullp = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return (args.getCar().isEmptyList()) ? SchemeObject.TRUE : SchemeObject.FALSE;
    }
  });
  public static final SchemeObject booleanp = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return (args.getCar().isBoolean()) ? SchemeObject.TRUE : SchemeObject.FALSE;
    }
  });
  public static final SchemeObject numberp = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return (args.getCar().isNumber()) ? SchemeObject.TRUE : SchemeObject.FALSE;
    }
  });
  public static final SchemeObject symbolp = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return (args.getCar().isSymbol()) ? SchemeObject.TRUE : SchemeObject.FALSE;
    }
  });
  public static final SchemeObject stringp = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return (args.getCar().isString()) ? SchemeObject.TRUE : SchemeObject.FALSE;
    }
  });
  public static final SchemeObject listp = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return (args.getCar().isPair()) ? SchemeObject.TRUE : SchemeObject.FALSE;
    }
  });
  public static final SchemeObject procedurep = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      SchemeObject exp = args.getCar();
      return (exp.isCompoundProc() || exp.isNativeProc()) ? SchemeObject.TRUE : SchemeObject.FALSE;
    }
  });
  public static final SchemeObject characterp = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return (args.getCar().isCharacter()) ? SchemeObject.TRUE : SchemeObject.FALSE;
    }
  });
  public static final SchemeObject numberToString = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return SchemeObject.makeString(String.valueOf(args.getCar().getNumber()));
    }
  });
  public static final SchemeObject stringToNumber = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return SchemeObject.makeNumber(Double.valueOf(new String(args.getCar().getString())));
    }
  });
  public static final SchemeObject stringToSymbol = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return SchemeObject.makeSymbol(new String(args.getCar().getString()));
    }
  });
  public static final SchemeObject symbolToString = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return SchemeObject.makeString(args.getCar().getSymbol());
    }
  });
  public static final SchemeObject numberToCharacter = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return SchemeObject.makeCharacter((short)args.getCar().getNumber());
    }
  });
  public static final SchemeObject characterToNumber = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return SchemeObject.makeNumber(args.getCar().getCharacter());
    }
  });
  public static final SchemeObject eqp = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      SchemeObject a = SchemeObject.car(args);
      SchemeObject b = SchemeObject.cadr(args);
      return (a.valueEqual(b)) ? SchemeObject.TRUE : SchemeObject.FALSE;
    }
  });
  public static final SchemeObject print = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      SchemeObject carObj = args.getCar();
      System.out.print(carObj);
      if(args.getCdr().isEmptyList()){
        System.out.print('\n');
        return SchemeObject.OK_SYMBOL;
      }else{
        return call(args.getCdr());
      }
    }
  });
  public static final SchemeObject concat = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      SchemeObject a = SchemeObject.car(args);
      SchemeObject b = SchemeObject.cadr(args);
      return SchemeObject.concatList(a, b);
    }
  });
  
  public static final SchemeObject vector = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return SchemeObject.makeVector(args);
    }
  });
  public static final SchemeObject vectorp = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return args.getCar().isVector() ? SchemeObject.TRUE : SchemeObject.FALSE;
    }
  });
  
  public static final SchemeObject vectorRef = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      SchemeObject vector = SchemeObject.car(args);
      int index = (int)SchemeObject.cadr(args).getNumber();
      return vector.getVector().get(index);
    }
  });
  
  public static final SchemeObject vectorSet = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      SchemeObject vector = SchemeObject.car(args);
      int index = (int)SchemeObject.cadr(args).getNumber();
      SchemeObject value = SchemeObject.caddr(args);
      vector.getVector().set(index, value);
      return vector;
    }
  });
  public static final SchemeObject vectorAdd = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      SchemeObject vector = SchemeObject.car(args);
      SchemeObject value = SchemeObject.cadr(args);
      vector.getVector().add(value);
      return vector;
    }
  });
  
  public static final SchemeObject vectorFill = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      SchemeObject vector = SchemeObject.car(args);
      SchemeObject value = SchemeObject.cadr(args);
      for(int i = 0; i < vector.getVector().size(); i++){
        vector.getVector().set(i, value);
      }
      return vector;
    }
  });
  
  public static final SchemeObject vectorLength = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      SchemeObject vector = SchemeObject.car(args);
      return SchemeObject.makeNumber(vector.getVector().size());
    }
  });
  
  public static final SchemeObject vectorToList = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      SchemeObject vector = SchemeObject.car(args);
      SchemeObject list = SchemeObject.THE_EMPTY_LIST;
      for(int i = vector.getVector().size() - 1; i >= 0; i--){
        list = SchemeObject.cons(vector.getVector().get(i), list);
      }
      return list;
    }
  });
  
  public static final SchemeObject listToVector = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      SchemeObject list = SchemeObject.car(args);
      SchemeObject vector = SchemeObject.makeVector(SchemeObject.THE_EMPTY_LIST);
      while(!list.isEmptyList()){
        vector.addToVector(list.getCar());
        list = list.getCdr();
      }
      return vector;
    }
  });
  
  public static final SchemeObject makeVector = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      SchemeObject list = SchemeObject.THE_EMPTY_LIST;
      long count = (long)SchemeObject.car(args).getNumber();
      for(int i = 0; i < count; i++){
        list = SchemeObject.cons(SchemeObject.cadr(args), list);
      }
      return SchemeObject.makeVector(list);
    }
  });
  public static final SchemeObject vectorConcat = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      SchemeObject vector = SchemeObject.makeVector(SchemeObject.THE_EMPTY_LIST);
      SchemeObject toAdd;
      while(!args.isEmptyList()){
        toAdd = args.getCar();
        vector.getVector().addAll(toAdd.getVector());
        args = args.getCdr();
      }
      return vector;
    }
  });
  
  public static final SchemeObject makeHashMap = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return SchemeObject.makeHashMap(args);
    }
  });
  public static final SchemeObject hashMapp = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      return args.getCar().isHashMap() ? SchemeObject.TRUE : SchemeObject.FALSE;
    }
  });
  public static final SchemeObject hashMapGet = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      SchemeObject map = args.getCar();
      SchemeObject key = SchemeObject.cadr(args);
      SchemeObject ret = map.getHashMap().get(key);
      if(ret == null){
        return SchemeObject.THE_EMPTY_LIST;
      }else{
        return ret;
      }
    }
  });
  /**
   * Sets the hashmap (<function name> map key value)
   *  Currently works for string, number, symbol, and character key values
   */
  public static final SchemeObject hashMapSet = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      SchemeObject map = args.getCar();
      SchemeObject key = SchemeObject.cadr(args);
      SchemeObject value = SchemeObject.caddr(args);
      map.getHashMap().remove(key);
      map.getHashMap().put(key, value);
      return map;
    }
  });
  public static final SchemeObject hashMapDel = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      SchemeObject map = args.getCar();
      SchemeObject key = SchemeObject.cadr(args);
      map.getHashMap().remove(key);
      return map;
    }
  });
  public static final SchemeObject hashMapKeys = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      SchemeObject map = args.getCar();
      Set<SchemeObject> keys = map.getHashMap().keySet();
      SchemeObject keyVector = SchemeObject.makeVector(SchemeObject.THE_EMPTY_LIST);
      
      for(SchemeObject key : keys){
        keyVector.addToVector(key);
      }
      
      return keyVector;
    }
  });
  public static final SchemeObject hashMapVals = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      SchemeObject map = args.getCar();
      Collection<SchemeObject> vals = map.getHashMap().values();
      SchemeObject valVector = SchemeObject.makeVector(SchemeObject.THE_EMPTY_LIST);
      
      for(SchemeObject val : vals){
        valVector.addToVector(val);
      }
      
      return valVector;
    }
  });
  public static final SchemeObject hashMapContainsKey = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      SchemeObject map = args.getCar();
      SchemeObject key = SchemeObject.cadr(args);
      return map.getHashMap().containsKey(key) ? SchemeObject.TRUE : SchemeObject.FALSE;
    }
  });
  public static final SchemeObject hashMapContainsVal = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      SchemeObject map = args.getCar();
      SchemeObject val = SchemeObject.cadr(args);
      return map.getHashMap().containsValue(val) ? SchemeObject.TRUE : SchemeObject.FALSE;
    }
  });
  public static final SchemeObject hashMapMerge = SchemeObject.makeNativeProc(new NativeProc(){
    @Override
    public SchemeObject call(SchemeObject args) {
      SchemeObject map = SchemeObject.makeHashMap(SchemeObject.THE_EMPTY_LIST);
      
      SchemeObject toMerge;
      while(!args.isEmptyList()){
        toMerge = args.getCar();
        
        map.getHashMap().putAll(toMerge.getHashMap());
        
        args = args.getCdr();
      }
      
      return map;
    }
  });
  
  //Java interop
  public static final SchemeObject javaNew = SchemeObject.makeNativeProc(new NativeProc() {
	  @Override
	  public SchemeObject call(SchemeObject args) {
		  SchemeObject classSpec = args.getCar();
		  Class cls = null;
		  if(classSpec.isSymbol()){
			  cls = Reflector.classFromName(classSpec.getSymbol());
		  }else if(classSpec.isJavaObj()){
			  try{
				  cls = (Class)classSpec.getJavaObj();
			  }catch(Exception e){}
		  }
		  if(cls == null){
			  throw new SchemeException("Unable to find class");
		  }
		  Object[] otherArgs = args.prepJavaArgs(args.getCdr());
		  return SchemeObject.makeJavaObj(Reflector.invokeStaticMethod(cls, "new", otherArgs));
	  }
  });
  
  /**
   * (. <instance/class> '<method> <args>...)
   */
  public static final SchemeObject javaDot = SchemeObject.makeNativeProc(new NativeProc() {
	@Override
	public SchemeObject call(SchemeObject args) {
		Object inst = args.getCar().getJavaObj();
		String method = args.getCdr().getCar().getSymbol();
		Object[] methodArgs = args.prepJavaArgs(args.getCdr().getCdr());
		
		Object ret;
		if(inst instanceof Class){
			//static method
			ret = Reflector.invokeStaticMethod((Class)inst, method, methodArgs);
		}else{
			//regular method
			ret = Reflector.invokeInstanceMethod(inst, method, methodArgs);
		}
		return SchemeObject.makeJavaObj(ret);
	}
  });
  
  public static final SchemeObject read = SchemeObject.makeNativeProc(new NativeProc() {
	@Override
	public SchemeObject call(SchemeObject args) {
		String s = new String(args.getCar().getString());
		return SchemeReader.readFromString(s);
	}
  });
}
