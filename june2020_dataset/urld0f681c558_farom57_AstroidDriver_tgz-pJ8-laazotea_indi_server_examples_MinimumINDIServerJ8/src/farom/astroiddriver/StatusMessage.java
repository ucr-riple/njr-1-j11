package farom.astroiddriver;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;

/**
 * hold the status message from the device
 * @author farom
 */
public class StatusMessage{
	public static final int MESSAGE_SIZE = 31;
	protected long time;
	protected int msCount;
	protected int stepHA;
	protected int stepDE;
	protected float uStepHA;
	protected float uStepDE;
	protected float moveSpeedRA;
	protected float moveSpeedDE;
	protected int ticks;
    
	/**
	 * Create the StatusMessage from the buffer
	 * @param buffer
	 */
	public StatusMessage(byte buffer[]){
		time = (new Date()).getTime();
		msCount = ByteBuffer.wrap(buffer,0,4).order(ByteOrder.BIG_ENDIAN).getInt();
        stepHA = ByteBuffer.wrap(buffer,4,4).order(ByteOrder.BIG_ENDIAN).getInt();
        stepDE = ByteBuffer.wrap(buffer,8,4).order(ByteOrder.BIG_ENDIAN).getInt();
        uStepHA = ByteBuffer.wrap(buffer,12,4).order(ByteOrder.BIG_ENDIAN).getFloat();
        uStepDE = ByteBuffer.wrap(buffer,16,4).order(ByteOrder.BIG_ENDIAN).getFloat();
        moveSpeedRA = ByteBuffer.wrap(buffer,20,4).order(ByteOrder.BIG_ENDIAN).getFloat();
        moveSpeedDE = ByteBuffer.wrap(buffer,24,4).order(ByteOrder.BIG_ENDIAN).getFloat();
        ticks = parseInt(buffer[28],buffer[29]);
	}
	
	/**
	 * Form a positive integer from two bytes
	 * @param high most significant byte
	 * @param low least significant byte
	 */
	private int parseInt(byte high, byte low){
		int result;
		if(high>=0){
			result = high * 256;
		}else{
			result = (high+256) * 256;
		}
		if(low>=0){
			result += low;
		}else{
			result += low + 256;
		}
		return result;
	}
	
	/**
	 * Validate the message according to the checksum 
	 * @param buffer
	 * @return true if the checksum is valid
	 */
	public static boolean verify(byte buffer[]){
		byte sum=0;
		for(int i=0; i<MESSAGE_SIZE-1; i++){
			sum+=buffer[i];
		}
		//System.out.println("sum:"+sum+" buffer[BUFFER_SIZE-1]:"+buffer[BUFFER_SIZE-1]);
		return (sum == buffer[MESSAGE_SIZE-1]);		
	}
	
	/**
	 * Empty StatusMessage
	 */
	StatusMessage(){
		time = 0;
		msCount = 0;
        stepHA = 0;
        stepDE = 0;
        uStepHA = 0;
        uStepDE = 0;
        moveSpeedRA=0;
        moveSpeedDE=0;
        ticks = 0;
	}
	
	@Override
	public String toString(){
		return "recieved: "+(new Date(time))+"\nmsCount: "+msCount+"\nstepRA: "+stepHA+"\nstepDE: "+stepDE+"\nuStepRA: "+uStepHA+"\nuStepDE: "+uStepDE+"\nmoveSpeedRA: "+moveSpeedRA+"\nmoveSpeedDE: "+moveSpeedDE+"\nticks:"+ticks+"\n";			
	}

	/**
	 * @return the ticks
	 */
	public int getTicks() {
		return ticks;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @return the msCount
	 */
	public int getMsCount() {
		return msCount;
	}

	/**
	 * @return the stepRA
	 */
	public int getStepHA() {
		return stepHA;
	}

	/**
	 * @return the stepDE
	 */
	public int getStepDE() {
		return stepDE;
	}

	/**
	 * @return the uStepRA
	 */
	public float getuStepRA() {
		return uStepHA;
	}

	/**
	 * @return the uStepDE
	 */
	public float getuStepDE() {
		return uStepDE;
	}

	/**
	 * @return the moveSpeedRA
	 */
	public float getMoveSpeedRA() {
		return moveSpeedRA;
	}

	/**
	 * @return the moveSpeedDE
	 */
	public float getMoveSpeedDE() {
		return moveSpeedDE;
	}
	
	/**
	 * @return stepHA + uStepHA/1024
	 */
	public double getHA() {
		return (double)stepHA + (double)uStepHA/1024.;
	}

	/**
	 * @return stepDE + uStepDE/1024
	 */
	public double getDE() {
		return (double)stepDE + (double)uStepDE/1024.;
	}
	
}