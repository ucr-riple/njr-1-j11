/**
 * @author Xuyang Feng
 * @email hugo.fxy@gmail.com
 */

package hugo.project.hospital;

public abstract class Device implements Comparable<Device>{
	private String type;
	private int amount;
	
	public Device(String type, int initialAmount) {
		this.type = type;
		this.amount = initialAmount;
	}

	public void reduceAmount(int reduceNum){
		if(reduceNum >= 0 && reduceNum <= amount) amount -= reduceNum;
	}

	public void addAmount(int addNum){
		if(addNum >= 0) amount += addNum;
	}
	
	public String toString() {
		return getType();
	}
	
	public String getType() {
		return type;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public boolean equals(Device device) {
		return getType().equals(device.getType());
	}
	
	public int compareTo(Device device) {
		return getType().compareTo(device.getType());
	}
	
}
