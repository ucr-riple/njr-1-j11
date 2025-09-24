/*
 * PicCode.java
 *
 * Author: Nat Meo
 *
 * Use this to parse out a PIC code into it's various components.
 */

package com.usps.evs.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PicCode
{
    private String zip;
    private String serviceTypeCode;
    private String duns;
    private String packageID;
    private String checkDigit;
	//19.0.0 create a 22 digit feild which is used for reconciliation.
	private String dunsPkgidDzip;
	//REL24.0.0 Support PICS with application ID = 92 & 93.
	private String appicationID;
	
	public PicCode(String picCode)
	{
		this(picCode.trim(), true);
	}
	

	public static void main(String[] args) throws Exception {
		
		String pic24Str 	= "4203209992152188907059100000000451";
		String pic24zip9Str = "4203209922229215218890705910000451";
		String pic23Str 	= "9115218890705910000971            ";
		String pic93str     = "9315212345610000000571            ";
		String picIntl10Str = "8212345724";
		String picIntl13Str = "AA100003071US";
		
		PicCode picIntl = new PicCode(picIntl13Str);
		System.out.println("REL 31: Application ID = "+picIntl.toString());
	/*	
		PicCode pic24 = new PicCode(pic24Str);
		System.out.println("REL 24: Application ID = "+pic24.toString());
		
		PicCode pic24zip9 = new PicCode(pic24zip9Str);
		System.out.println("REL 24: Application ID = "+pic24zip9.toString());
		
		PicCode pic23 = new PicCode(pic23Str);
		System.out.println("REL 23: Application ID = "+pic23.toString());
		
		PicCode pic93 = new PicCode(pic93str);
		System.out.println("REL 93: Application ID = "+pic93.toString());
		System.out.println("REL 93: Application ID :: duns length = "+pic93.getDuns().length());
		
		*/
	}
	
	public String toString(){
		ToStringBuilder.setDefaultStyle(ToStringStyle.MULTI_LINE_STYLE);
		return ToStringBuilder.reflectionToString(this).toString();
    	
	}
	
	public PicCode(String picCode, boolean isCheckedDigit)
    {
        String pic = picCode.trim();
        
		
        boolean isZip9Digit = false;
		//REL24.0.0 Update the logic to handle PIC from Manifest1.5
		if (isCheckedDigit) {
  
			if(pic.startsWith("420") &&  pic.length() >= 33){
				
				String tmpAppID = pic.substring(8,10);

				//Validate AppID
				if("91".equals(tmpAppID) || 
				   "92".equals(tmpAppID) || 
                   "93".equals(tmpAppID)){

					isZip9Digit = false;
					setAppicationID(tmpAppID);
					setDunsPkgidDzip(pic.substring(8));
					setZip(pic.substring(3,8));
					  	
				}else {
					isZip9Digit = true;
					setAppicationID(pic.substring(12,14));
					setDunsPkgidDzip(pic.substring(12));
					setZip(pic.substring(3,12));
				}

	
			} else if(pic.startsWith("420") &&  pic.length() <= 30 && pic.length() >= 24){
				setZip(pic.substring(3, 8));
				setAppicationID(pic.substring(8,10));
				setServiceTypeCode(pic.substring(10, 12));
				setDuns(pic.substring(12, 21));
				setPackageID(pic.substring(21, pic.length() - 1));
				setCheckDigit(pic.substring(pic.length() - 1, pic.length()));
				//19.0.0 Get the substring from postion 8 which should give us a 22 digit number which 
				//starts with 91
				setDunsPkgidDzip(pic.substring(8));

			} 
			//REL31.0.0 international scans
			else if(pic.startsWith("82")||(pic.startsWith("83"))){
				System.out.println("Into international scan parsing:"+pic.toString());
				String appID = pic.substring(0, 2);
				System.out.println("Into international scan parsing:"+appID);
				System.out.println("Into international scan parsing- package id:"+pic.substring(2,9));
				System.out.println("Into international scan parsing - checkdigit:"+pic.substring(9,10));
			
				//G01 barcodes
				if(pic.length()==10){
					setAppicationID(pic.substring(0,2));	
					setPackageID(pic.substring(2, 9));
					setCheckDigit(pic.substring(9, 10));
					setDunsPkgidDzip(pic);
				}

			}
			else if((pic.length()==13)&& (pic.substring(0,2).matches("[a-zA-Z]*"))){
				
				System.out.println("Into international scan I01 parsing:"+pic.toString());
				String appID = pic.substring(0, 2);
				System.out.println("Into international scan parsing:"+appID);
				System.out.println("Into international scan parsing- package id:"+pic.substring(2,10));
				System.out.println("Into international scan parsing - checkdigit:"+pic.substring(10,11));
		
				//I01 barcodes
				setAppicationID(pic.substring(0,2));
				setPackageID(pic.substring(2, 10));
				setCheckDigit(pic.substring(10, 11));
				setDunsPkgidDzip(pic);
				
			}
			else if (pic.length() <= 26 && pic.length() >= 18){
				setAppicationID(pic.substring(0,2));
				setDunsPkgidDzip(pic);
				
			}			
			else	{
				setServiceTypeCode(null);
				setDuns(null);
				setPackageID(null);
				setCheckDigit(null);
				setZip(null);
				setDunsPkgidDzip(null);
			}
			
			if(getDunsPkgidDzip() != null){
				//Process the DunsPKID to get populate the PIC
				if("91".equals(getAppicationID()))
					parseAppID91();
				else if("92".equals(getAppicationID()))
					parseAppID92();
				else if("93".equals(getAppicationID()))
					parseAppID93();		
			}


		} else {
			if(pic.length() <= 30 &&
				pic.length() >= 24)
			{
				setServiceTypeCode(pic.substring(10, 12));
				setDuns(pic.substring(12, 21));
				setPackageID(pic.substring(21, pic.length() - 1));
				setCheckDigit(pic.substring(pic.length() - 1, pic.length()));
				setZip(pic.substring(3, 8));
				setDunsPkgidDzip(pic.substring(8));
			}
			else if(pic.length() <= 22 &&
				pic.length() >= 18)
			{
				setServiceTypeCode(pic.substring(2, 4));
				setDuns(pic.substring(4, 13));
				setPackageID(pic.substring(13, pic.length() - 1));
				setCheckDigit(pic.substring(pic.length() - 1, pic.length()));
				setZip(null);
				setDunsPkgidDzip(pic);
			}
			else if(pic.length() <= 20 && pic.length() >= 16)
			{
				setServiceTypeCode(pic.substring(0, 2));
				setDuns(pic.substring(2, 11));
				if(pic.length() == 20)
				{
					setPackageID(pic.substring(11, 19));
					setCheckDigit(pic.substring(19, 20));
				}
				else
				{
					setPackageID(pic.substring(11, pic.length() - 1));
					setCheckDigit(pic.substring(pic.length() - 1, pic.length()));
				}
				setZip(null);
			}
			setDunsPkgidDzip(pic);
		}	
    }
	
	public boolean parseAppID91(){
		String pic = getDunsPkgidDzip(); 
		setServiceTypeCode(pic.substring(2, 4));
		setDuns(pic.substring(4, 13));
		setPackageID(pic.substring(13, pic.length() - 1));
		setCheckDigit(pic.substring(pic.length() - 1, pic.length()));
		setZip(null);

		return true;
	}

	public boolean parseAppID92(){
		String pic = getDunsPkgidDzip(); 
		 
		setServiceTypeCode(pic.substring(2, 5));
		setDuns(pic.substring(5, 14));
		setPackageID(pic.substring(14, pic.length() - 1));
		setCheckDigit(pic.substring(pic.length() - 1, pic.length()));

		return true;
	}
	
	public boolean parseAppID93(){
		String pic = getDunsPkgidDzip(); 
			 
		setServiceTypeCode(pic.substring(2, 5));
		setDuns(pic.substring(5, 11));
		setPackageID(pic.substring(11, pic.length() - 1));
		setCheckDigit(pic.substring(pic.length() - 1, pic.length()));

		return true;
	}

    public String getZip()
    {
        return zip;
    }

    public void setZip(String zip)
    {
        this.zip = zip;
    }

    public String getServiceTypeCode()
    {
        return serviceTypeCode;
    }

    public void setServiceTypeCode(String serviceTypeCode)
    {
        this.serviceTypeCode = serviceTypeCode;
    }

    public String getDuns()
    {
        return (duns == null? duns : duns.trim());
    }

    public void setDuns(String duns)
    {
        this.duns = duns;
    }

    public String getPackageID()
    {
        return packageID;
    }

    public void setPackageID(String packageID)
    {
        this.packageID = packageID;
    }

    public String getCheckDigit()
    {
        return checkDigit;
    }

    public void setCheckDigit(String checkDigit)
    {
        this.checkDigit = checkDigit;
    }
    
    	/**
	 * @return
	 */
	public String getDunsPkgidDzip() {
		return dunsPkgidDzip;
	}
	
		/**
	 * @param string
	 */
	private void setDunsPkgidDzip(String string) {
		dunsPkgidDzip = string;
	}


	/**
	 * @return
	 */
	public String getAppicationID() {
		return appicationID;
	}

	/**
	 * @param string
	 */
	public void setAppicationID(String string) {
		appicationID = string;
	}

}
