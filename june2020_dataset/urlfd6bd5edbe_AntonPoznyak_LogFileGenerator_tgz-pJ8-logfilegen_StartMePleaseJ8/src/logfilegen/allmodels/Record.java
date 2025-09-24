package logfilegen.allmodels;

import logfilegen.allmodels.record.Date;
import logfilegen.allmodels.record.Ip;
import logfilegen.allmodels.record.Request;
import logfilegen.allmodels.record.Size;
import logfilegen.allmodels.record.Status;

public class Record {
	private Ip userIp;
	private Date userTime;
	private Request request;
	private Status userStatus;
	private Size size;
	
	public Record(){}
	public Record(Ip userIp, Date userTime, Request request, Status userStatus, Size size){
		this.userIp=userIp;
		this.userTime=userTime;
		this.request=request;
		this.userStatus=userStatus;
		this.size=size;
	}
	
	public Ip getIp() {
		return userIp;
	}
	public void setIp(Ip userIp) {
		this.userIp = userIp;
	}
	public Date getTime() {
		return userTime;
	}
	public void setTime(Date userTime) {
		this.userTime = userTime;
	}
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	public Status getStatus() {
		return userStatus;
	}
	public void setStatus(Status userStatus) {
		this.userStatus = userStatus;
	}
	public Size getSize() {
		return size;
	}
	public void setSize(Size size) {
		this.size = size;
	}
	public String toString(){
		return userIp.toString()+" - "+userTime.toString()+ " " + request.toString() +" " + userStatus.toString()+ " "+ size.toString();
	}
}
