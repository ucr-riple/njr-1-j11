package learn.dp;

public abstract class AbstractTemplate {

	public void doTask(){
		try {
			before();
			doMethod();
			after();
		}finally{
			
		}
		
	}
	protected abstract void doMethod();
	
	private void before(){
		System.out.println("Performing before....");	
	}
	
	private void after() {
		System.out.println("Performing after....");
	}
}
