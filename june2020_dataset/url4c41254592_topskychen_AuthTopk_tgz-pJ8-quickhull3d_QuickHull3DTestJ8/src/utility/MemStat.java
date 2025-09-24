/**
 * 
 */
package utility;

/**
 * @author chenqian
 *
 */
public class MemStat implements Runnable{

	static long mb = 1024 * 1024;
	long mem_size = 0;
	long max_mem = 0;
	boolean isFinish = false;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		final Runtime runtime = Runtime.getRuntime();
		mem_size = runtime.totalMemory() - runtime.freeMemory();
		isFinish = false;
		while(!isFinish){
			mem_size = runtime.totalMemory() - runtime.freeMemory();
			if(mem_size > max_mem){
				max_mem = mem_size;
			}
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}

	public void reSet(){
		max_mem = 0;
		System.gc();
	}
	
	public long getCurrentMemSize(){
		return mem_size;
	}
	
	public long getMaxMemSize(){
		return max_mem;
	}
	
	public long getCurrentMemSizeInMB(){
		return mem_size / mb;
	}
	
	public long getMaxMemSizeInMB(){
		return max_mem / mb;
	}
	
	public void printInfo(){
		System.out.println("Current Memory Usage:\t" + getCurrentMemSizeInMB() + " MB");
		System.out.println("Max Memory Usage:\t" + getMaxMemSizeInMB() + " MB");
	}
	
	public void setFinish(){
		isFinish = false;
	}
}
