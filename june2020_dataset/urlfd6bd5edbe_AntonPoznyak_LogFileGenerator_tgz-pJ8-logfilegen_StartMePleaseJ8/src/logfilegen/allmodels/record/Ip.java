package logfilegen.allmodels.record;

public class Ip {
	private short[] ip = new short[4];
	public Ip(){
		
	}
	public short[] getIp() {
		return ip;
	}
	public void setIp(short[] ip) {
		this.ip = ip;
	}
	
	public Ip(short num0, short num1, short num2, short num3){
		this.ip[0]=num0;
		this.ip[1]=num1;
		this.ip[2]=num2;
		this.ip[3]=num3;
	}
	public Ip(short[] ip){
		this.ip[0]=ip[0];
		this.ip[1]=ip[1];
		this.ip[2]=ip[2];
		this.ip[3]=ip[3];
	}
	
	public String toString(){
		return Short.toString(ip[0]) + "." + Short.toString(ip[1])+ "." + Short.toString(ip[2])+ "." + Short.toString(ip[3]);
	}
}
