package com.undi.javascheme;

public class SchemeException extends RuntimeException{
		public SchemeException(){
				super();
		}
		public SchemeException(String msg){
				super(msg);
		}
		public SchemeException(String msg, Throwable cause){
				super(msg, cause);
		}
}
