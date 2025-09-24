
public class MyTimer {
	private long start;
	private boolean gtod;
	
	public MyTimer(){
		this(true);
	}
	
	public MyTimer(boolean gtod){
		this.gtod = gtod;
		if(gtod){
			this.start = System.currentTimeMillis();
		}else{
			this.start = System.currentTimeMillis(); //should be user time not just time
		}
	}
	
	public double elapsed(){
		long end;
		if(gtod){
			end = System.currentTimeMillis();
		}else {
			end = System.currentTimeMillis();
		}	
		return (end - start)/1000.0;
	}
	
}
