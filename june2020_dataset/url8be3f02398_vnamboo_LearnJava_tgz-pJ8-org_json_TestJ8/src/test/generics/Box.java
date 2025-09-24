package test.generics;

import java.io.Serializable;

/**
 * This version introduces a generic method.
 */
public class Box<T> {

    private T t;          

    public void add(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }

    public <U extends Number & Runnable & Serializable> void inspect(U u){
        System.out.println("T: " + t.getClass().getName());
        System.out.println("U: " + u.getClass().getName());
    }

    public static void main(String[] args) {
        Box<Integer> integerBox = new Box<Integer>();
        integerBox.add(new Integer(10));
        integerBox.inspect(new MyClass());
    }
    
    private static  class MyClass extends Number implements Runnable {

		@Override
		public double doubleValue() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public float floatValue() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public int intValue() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public long longValue() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
    	
    }
}
