package farom.astroiddriver;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class CmdMessage {
	public static final int MESSAGE_SIZE = 11;
	public static final int TICKS_OFF = 0;
	public static final int TICKS_FOCUS = 1;
	public static final int TICKS_EXPOSE = 2;
	public static final int TICKS_EXPOSE_FOCUS = 3;
	protected float speedRA;
	protected float speedDE;
	protected int	ticks;
	
	public CmdMessage(float moveSpeedRA,float moveSpeedDE, int ticksServo) {
		speedRA=moveSpeedRA;
		speedDE=moveSpeedDE;
		ticks=ticksServo;
	}
	
	public CmdMessage() {
		speedRA=0;
		speedDE=0;
		ticks=0;
	}
	
	public byte[] getBytes(){
		ByteBuffer buffer = ByteBuffer.allocate(MESSAGE_SIZE);
		buffer.order(ByteOrder.BIG_ENDIAN);
		buffer.putFloat(0, speedRA);
		buffer.putFloat(4, speedDE);
		buffer.put(8,(byte) ((ticks/256) & 0xFF));
		buffer.put(9,(byte) (ticks & 0xFF));
		
		
		byte[] array = buffer.array();
		int sum = 0;
		for(int i=0; i<MESSAGE_SIZE-1; i++){
			sum+=array[i];
		}
		array[MESSAGE_SIZE-1]=(byte) (sum & 0xFF);
		
//		System.out.print("send: ");
//		for(int i =0; i<MESSAGE_SIZE; i++){
//			System.out.printf("%02X ", array[i]);
//		}
//		System.out.println("");
		return array;
	}

	/**
	 * @return the speedRA
	 */
	public float getSpeedRA() {
		return speedRA;
	}

	/**
	 * @param speedRA the speedRA to set
	 */
	public void setSpeedRA(float speedRA) {
		this.speedRA = speedRA;
	}

	/**
	 * @return the speedDE
	 */
	public float getSpeedDE() {
		return speedDE;
	}

	/**
	 * @param speedDE the speedDE to set
	 */
	public void setSpeedDE(float speedDE) {
		this.speedDE = speedDE;
	}

	/**
	 * @return the ticks
	 */
	public int getTicks() {
		return ticks;
	}

	/**
	 * @param ticks the ticks to set
	 */
	public void setTicks(int ticks) {
		this.ticks = ticks;
	}

}
