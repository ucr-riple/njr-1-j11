package threadlocal;

import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
public static void main(String[] args) {
 new LockTest().lockTest();	
}
private boolean lockTest() {
	ReentrantLock lock=new ReentrantLock();
	lock.lock();
	try {
		System.out.println("Hello");
		if(true) throw new RuntimeException();
		return true;
	}finally{
		lock.unlock();
	}
	
	
}
}
