/**
 * 
 */
package Object_Interfaces;

/**
 * @author benjamin
 * 
 */
public interface Containable extends GameObject {

	/**
	 * Returns the rank/size of this object e.g. coin.getSize() = 0, wallet.getSize() = 1 and bag.getSize() = 2
	 * 
	 * @return
	 */
	public int getSize();

}
