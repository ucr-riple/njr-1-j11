package com.scb.cleanCode;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AcceptOnlyOnceFilter extends AbstractFileFilter {

	@Override
	public boolean accept(File file) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date dt=sdf.parse("21-Jun-2010 07:32:21");
		System.out.println(dt.toString());
	}

}
