package logfilegen.allconverters;

import logfilegen.allconverters.record.DateConv;
import logfilegen.allconverters.record.IpConv;
import logfilegen.allconverters.record.RequestConv;
import logfilegen.allconverters.record.SizeConv;
import logfilegen.allconverters.record.StatusConv;
import logfilegen.allmodels.Record;

public class RecordConverter {
	private Record record;
	
	public RecordConverter(Record record){
		this.record = record;
	}
	
	public String convertToString(){
		StringBuilder builder = new StringBuilder();
//{IP} - - [{Day}/{Month 3 letter}/{year}:{hours}:{minutes}:{seconds} +{time offset, 4 digits}] {Http Method} {url} {HttpVersion} {Http Status} {Size}
		IpConv ipConv = new IpConv(record.getIp());
		builder.append(ipConv.convertToString());
		builder.append(" - [");
		DateConv dateConv = new DateConv(record.getTime());
		builder.append(dateConv.convertToString());
		builder.append("] \"");
		RequestConv requestConv = new RequestConv(record.getRequest());
		builder.append(requestConv.convertToString());
		builder.append("\" ");
		StatusConv statusConv = new StatusConv(record.getStatus());
		builder.append(statusConv.convertToString());
		builder.append(" ");
		SizeConv sizeConv = new SizeConv(record.getSize());
		builder.append(sizeConv.convertToString());

		return builder.toString();
	}
}
