package daily;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class FoodBuilder {
	private int meatAmount;
	private int saltAmout;
	
	FoodBuilder setSaltAmout(int saltAmount){
		this.saltAmout=saltAmount;
		return this;
	}
	
	FoodBuilder setMeatAmout(int meatAmount){
		this.meatAmount=meatAmount;
		return this;
	}
	
	Food build(){
		return new Food(this.saltAmout,this.meatAmount);
	}
	
	public static void main(String[] args) {
		Food food=new FoodBuilder().setMeatAmout(1).setSaltAmout(2).build();
		ReferenceQueue<Food> queue=new ReferenceQueue<Food>();
		PhantomReference<Food> ref=new PhantomReference<Food>(food, queue);
		WeakReference<Food> weakRef=new WeakReference<Food>(food);
		System.out.println(ref.get());
		System.out.println(weakRef.get().getMeatAmount());
	}
}
