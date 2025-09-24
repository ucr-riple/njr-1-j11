package logfilegen.allconverters.record;

import logfilegen.allmodels.record.Ip;

public class IpConv {
	private Ip ip;
	
	public IpConv(Ip ip){
		this.ip = ip;
	}
	
	public String convertToString(){
		StringBuilder builder = new StringBuilder();
		short[] getIp = ip.getIp();
		builder.append(getIp[0]); builder.append(".");
		builder.append(getIp[1]);
		builder.append(".");
		builder.append(getIp[2]);
		builder.append(".");
		builder.append(getIp[3]);
		builder.append(".");
		
		return builder.toString();
	}
}
