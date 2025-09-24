package com.undi.util;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class Reflector{
  
  public static final Object[] emptyArgs = new Object[0];
  
  public static RuntimeException throwCauseOrElseException(Exception e){
    if(e.getCause() != null){
      throw SneakyThrow.sneakyThrow(e.getCause());
    }
    throw SneakyThrow.sneakyThrow(e);
  }
  
  public static Class<?> classFromName(String className){
    try{
      return Class.forName(className);
    }catch(ClassNotFoundException e){
      throw SneakyThrow.sneakyThrow(e);
    }
  }
  
  public static Field getField(Class<?> c, String name, boolean getStatics){
    Field[] allFields = c.getFields();
    for(Field f : allFields){
      if(name.equals(f.getName())
          && Modifier.isStatic(f.getModifiers()) == getStatics){
        return f;
      }
    }
    return null;
  }
  
  public static Object getStaticField(String className, String fieldName){
    Class<?> c;
    c = classFromName(className);
    return getStaticField(c, fieldName);
  }
  
  public static Object getStaticField(Class<?> c, String fieldName){
    Field f = getField(c, fieldName, true);
    if(f != null){
      try{
        return prepRet(f.getType(), f.get(null));
      }catch(IllegalAccessException e){
        throw SneakyThrow.sneakyThrow(e);
      }
    }
    throw new IllegalArgumentException("No matching field found");
  }
  
  public static Object setStaticField(String className, String fieldName, Object val){
    Class<?> c = classFromName(className);
    return setStaticField(c, fieldName, val);
  }
  
  public static Object setStaticField(Class<?> c, String fieldName, Object val){
    Field f = getField(c, fieldName, true);
    if(f != null){
      try{
        f.set(null, boxArg(f.getType(), val));
      }catch(IllegalAccessException e){
        throw SneakyThrow.sneakyThrow(e);
      }
      return val;
    }
    throw new IllegalArgumentException("No matching field found");
  }
  
  public static Object getInstanceField(Object target, String fieldName){
    Class<?> c = target.getClass();
    Field f = getField(c, fieldName, false);
    if(f != null){
      try{
        return prepRet(f.getType(), f.get(target));
      }catch(IllegalAccessException e){
        throw SneakyThrow.sneakyThrow(e);
      }
    }
    throw new IllegalArgumentException("No matching field found");
  }
  
  public static Object setInstanceField(Object target, String fieldName, Object val){
    Class<?> c = target.getClass();
    Field f = getField(c, fieldName, false);
    if(f != null){
      try{
        f.set(target, boxArg(f.getType(), val));
      }catch(IllegalAccessException e){
        throw SneakyThrow.sneakyThrow(e);
      }
      return val;
    }
    throw new IllegalArgumentException("No matching field found");
  }
  
  public static Object invokeStaticMethodVariadic(String className, String methodName, Object... args){
    return invokeStaticMethod(className, methodName, args);
  }
  
  public static Object invokeStaticMethod(String className, String methodName, Object[] args){
    try {
      Class<?> c = Class.forName(className);
      return invokeStaticMethod(c, methodName, args);
    } catch (Exception e) {
      throw SneakyThrow.sneakyThrow(e);
    }
  }
  
  public static Object invokeStaticMethod(Class<?> c, String methodName, Object[] args){
    if(methodName.equals("new")){
      return invokeConstructor(c, args);
    }
    List<Method> methods = getMethods(c, args.length, methodName, true);
    return invokeMatchingMethod(methodName, methods, null, args);
  }
  
  public static Object invokeConstructor(Class<?> c, Object[] args){
    try{
      Constructor<?>[] allctors = c.getConstructors();
      ArrayList<Constructor<?>> ctors = new ArrayList<Constructor<?>>();
      for(Constructor<?> ctor : allctors){
        if(ctor.getParameterTypes().length == args.length){
          ctors.add(ctor);
        }
      }
      if(ctors.isEmpty()){
        throw new IllegalArgumentException("Not Matching constructor found for: " + c);
      }else if(ctors.size() == 1){
        Constructor<?> ctor = (Constructor<?>) ctors.get(0);
        return ctor.newInstance(boxArgs(ctor.getParameterTypes(), args));
      }else{ //overloaded with same arity
        for(Constructor<?> ctor : ctors){
          Class<?>[] params = ctor.getParameterTypes();
          if(isCongruent(params, args)){
            Object[] boxedArgs = boxArgs(params, args);
            return ctor.newInstance(boxedArgs);
          }
        }
        throw new IllegalArgumentException("Not Matching constructor found for: " + c);
      }
    }catch(Exception e){
      throw SneakyThrow.sneakyThrow(e);
    }
  }
  
  public static Object invokeInstanceMethod(Object target, String methodName, Object[] args){
    Class<?> c = target.getClass();
    List<Method> methods = getMethods(c, args.length, methodName, false);
    return invokeMatchingMethod(methodName, methods, target, args);
  }
  
  static public boolean paramArgTypeMatch(Class<?> paramType, Class<?> argType){
    if(argType == null){
      return !paramType.isPrimitive();
    }
    if(paramType == argType || paramType.isAssignableFrom(argType)){
      return true;
    }
    if(paramType == int.class){
      return argType == Integer.class
      || argType == long.class
      || argType == Long.class
      || argType == short.class
      || argType == byte.class;// || argType == FixNum.class;
    }else if(paramType == float.class){
      return argType == Float.class
      || argType == double.class;
    }else if(paramType == double.class){
      return argType == Double.class
      || argType == float.class;// || argType == DoubleNum.class;
    }else if(paramType == long.class){
      return argType == Long.class
      || argType == int.class
      || argType == short.class
      || argType == byte.class;// || argType == BigNum.class;
    }else if(paramType == char.class){
      return argType == Character.class;
    }else if(paramType == short.class){
      return argType == Short.class;
    }else if(paramType == byte.class){
      return argType == Byte.class;
    }else if(paramType == boolean.class){
      return argType == Boolean.class;
    }
    return false;
  }

  static boolean isCongruent(Class<?>[] params, Object[] args){
    boolean ret = false;
    if(args == null)
      return params.length == 0;
    if(params.length == args.length){
      ret = true;
      for(int i = 0; ret && i < params.length; i++){
        Object arg = args[i];
        Class<?> argType = (arg == null) ? null : arg.getClass();
        Class<?> paramType = params[i];
        ret = paramArgTypeMatch(paramType, argType);
        }
      }
    return ret;
  }

  
  public static Object invokeMatchingMethod(String methodName, List<Method> methods, Object target, Object[] args){
    Method m = null;
    Object[] boxedArgs = null;
    if(methods.isEmpty()){
      throw new IllegalArgumentException("Method: '" + methodName + "' does not exist in: " + target);
    }else if(methods.size() == 1){
      m = (Method) methods.get(0);
      boxedArgs = boxArgs(m.getParameterTypes(), args);
    }else{ //overloaded with same arity
      Method foundm = null;
      for(Method method : methods){
        Class<?>[] params = method.getParameterTypes();
        if(isCongruent(params, args)){
          if(foundm == null){
            foundm = method;
            boxedArgs = boxArgs(params, args);
          }
        }
      }
      m = foundm;
    }
    if(m == null){
      throw new IllegalArgumentException("No matching method found");
    }
    if(!Modifier.isPublic(m.getDeclaringClass().getModifiers())){
      //public method of non-public class...
      //Method oldm = m;
      m = getAsMethodOfPublicBase(m.getDeclaringClass(), m);
      if(m == null){
        throw new IllegalArgumentException("Can't call public method of non-public class");
      }
    }
    try{
      return prepRet(m.getReturnType(), m.invoke(target, boxedArgs));
    }catch(Exception e){
      throw SneakyThrow.sneakyThrow(e);
    }
  }
  
  public static Object prepRet(Class<?> c, Object obj){
    if(!(c.isPrimitive() || c == Boolean.class)){
      return obj;
    }
    if(obj instanceof Boolean){
      return ((Boolean) obj) ? Boolean.TRUE : Boolean.FALSE;
    }
    return obj;
  }
  
  static Object boxArg(Class<?> paramType, Object arg){
    if(!paramType.isPrimitive()){
      return paramType.cast(arg);
    }else if(paramType == boolean.class){
      return Boolean.class.cast(arg);
    }else if(paramType == char.class){
      return Character.class.cast(arg);
    }else if(arg instanceof Number){
      Number n = (Number) arg;
      if(paramType == int.class)
        return n.intValue();
      else if(paramType == float.class)
        return n.floatValue();
      else if(paramType == double.class)
        return n.doubleValue();
      else if(paramType == long.class)
        return n.longValue();
      else if(paramType == short.class)
        return n.shortValue();
      else if(paramType == byte.class)
        return n.byteValue();
    }
    throw new IllegalArgumentException("Unexpected param type, expected: " + paramType +
        ", given: " + arg.getClass().getName());
  }
  
  static Object[] boxArgs(Class<?>[] params, Object[] args){
    if(params.length == 0)
      return null;
    Object[] ret = new Object[params.length];
    for(int i = 0; i < params.length; i++){
      Object arg = args[i];
      Class<?> paramType = params[i];
      ret[i] = boxArg(paramType, arg);
    }
    return ret;
  }
  
  public static List<Method> getMethods(Class<?> c, int arity, String name, boolean getStatics){
    Method[] allMethods = c.getMethods();
    ArrayList<Method> methods = new ArrayList<Method>();
    ArrayList<Method> bridgeMethods = new ArrayList<Method>();
    for(Method method : allMethods){
      if(name.equals(method.getName()) &&
          Modifier.isStatic(method.getModifiers()) == getStatics &&
          method.getParameterTypes().length == arity){
        try{
          if(method.isBridge() &&
          c.getMethod(method.getName(), method.getParameterTypes()).equals(method)){
            bridgeMethods.add(method);
          }else{
            methods.add(method);
          }
        }catch(NoSuchMethodException ex){
          
        }
      }
    }
    if(methods.isEmpty()){
      methods.addAll(bridgeMethods);
    }
    if(!getStatics && c.isInterface()){
      allMethods = Object.class.getMethods();
      for(Method method : allMethods){
        if(name.equals(method.getName())
            && Modifier.isStatic(method.getModifiers()) == getStatics
            && method.getParameterTypes().length == arity){
          methods.add(method);
        }
      }
    }
    return methods;
  }
  
  public static boolean isMatch(Method lhs, Method rhs){
    if(!lhs.getName().equals(rhs.getName())
        || !Modifier.isPublic(lhs.getDeclaringClass().getModifiers())){
      return false;
    }

    Class<?>[] types1 = lhs.getParameterTypes();
    Class<?>[] types2 = rhs.getParameterTypes();
    if(types1.length != types2.length)
      return false;

    boolean match = true;
    for (int i=0; i<types1.length; ++i){
      if(!types1[i].isAssignableFrom(types2[i])){
        match = false;
        break;
      }
    }
    return match;
  }
  
  public static Method getAsMethodOfPublicBase(Class<?> c, Method m){
    for(Class<?> iface : c.getInterfaces()){
      for(Method im : iface.getMethods()){
        if(isMatch(im, m)){
          return im;
        }
      }
    }
    Class<?> sc = c.getSuperclass();
    if(sc == null){
      return null;
    }
    for(Method scm : sc.getMethods()){
      if(isMatch(scm, m)){
        return scm;
      }
    }
    return getAsMethodOfPublicBase(sc, m);
  }
}