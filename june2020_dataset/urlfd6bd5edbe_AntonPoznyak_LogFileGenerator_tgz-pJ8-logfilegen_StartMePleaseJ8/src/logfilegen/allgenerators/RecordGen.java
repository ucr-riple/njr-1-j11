package logfilegen.allgenerators;

import logfilegen.allgenerators.record.DateGen;
import logfilegen.allgenerators.record.IpGen;
import logfilegen.allgenerators.record.RequestGen;
import logfilegen.allgenerators.record.SizeGen;
import logfilegen.allgenerators.record.StatusGen;
import logfilegen.allmodels.Record;
import logfilegen.allmodels.record.Date;
import logfilegen.allmodels.record.Ip;
import logfilegen.allmodels.record.Request;
import logfilegen.allmodels.record.Size;
import logfilegen.allmodels.record.Status;

public class RecordGen {
	public Record generate(){
		Date date = new DateGen().generate();
		Ip ip = new IpGen().generate();
		Request request = new RequestGen().generate();
		Size size = new SizeGen().generate();
		Status status = new StatusGen().generate();
		
		return new Record (ip,date,request,status,size);
	}
}
