/*
 * DataTypes.java
 *
 * Author: Nat Meo
 *
 * Verify and convert various data types used in multiple locations in code.
 *
 * Change Log:
 * 		03/03/2007 Jose: Added S2 Mail Class for Standard Mail Non Profit
 *
 * 		03/19/2007 Ivan:  Add more conditions on several validation for ManifestValidator
 *
 * 		03/21/2007 Ivan:  Add isSpecialServicesValid method
 *
 * 		03/24/2007 Ivan:  Change the input parameter for isSpecialServicesValid
 *                        It is to set the message of the invalid special service
 *
 * 		04/04/2007 Jose: Added PRS_PDA for date and time format to accomodate the filename change needed
 * 						 for sampling files; updated the system date in the PRS sample header record to be
 *                       stored in the database as a timestamp, instead of date only.
 *
 * 		04/24/2007 Jose: Updated code on lines 1006 - 1009 for Bulk Insurance.
 * 		05/03/2007 Jose: Updated isClassOfMail method to no longer support RB for PRS.
 * 		05/04/2007 Jose: Updated for version 1.4 validation.
 *
 * 		02/16/2008 Ivan: PRI15.4.0, Add new PM Rate Indicator (PL/PM)
 *
 * 		04/16/2008 Ivan:  REL 16.1.0 Add D8, D9
 * 
 * 		09/02/2008 Ivan: REL18.0.0.  Add RI for Small Flat Rate Box Priority Mail
 * 
 * 		12/22/2008 Ivan: REL18.1.0 ETR 29056 Change BR to MR
 * 
 * 		01/14/2009 Ivan: REL19.0.0
 */
 
 /*
  * $Workfile:   DataTypes.java  $
  * $Revision:   1.64  $
  * =================================== 
  * $Log:   P:/databases/PostalOne!/archives/PostalOne!/P1_DEV/e-VS/EvsServer/com/usps/evs/util/DataTypes.java-arc  $
 * 
 *    Rev 1.64   Sep 04 2009 20:23:04   sutantod
 * Add a method to convert calendar to date sql
 * 
 *    Rev 1.63   Aug 25 2009 15:13:00   guptap
 * Changes to STCs for Release 22
 * 
 *    Rev 1.62   Jun 12 2009 16:37:58   sutantod
 * Addd new method
 * 
 *    Rev 1.61   Jun 11 2009 16:00:46   huangk
 * Modified isFileVersionNumber to accept 1.4 only.
 * 
 *    Rev 1.60   Jun 10 2009 13:45:56   gandhia
 * STC logic updated for Samples and Manifest processing.
 * 
 *    Rev 1.59   Jun 09 2009 15:06:32   gandhia
 * Updated STC for Express Mail
 * 
 *    Rev 1.58   May 28 2009 11:30:16   sutantod
 * Validation on Express Mail RR followed by IM
  * 
 */

package com.evs.data.util;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class DataTypes {

    public static final int MANIFEST = 1;
    public static final int STATS = 2;
    public static final int PDA = 3;
    public static final int EXTRACT = 4;
    public static final int CEW = 5;
    public static final int CEW_RECEIPT = 6;
    public static final int CEW_MAILING = 7;
    public static final int PRS_PDA = 8;
    public static final int PASS = 9;  
    public static final int CAD = 10;

    public static void main(String[] args) {
    	String code = "9AB";
		System.out.println("Code-->[" + code + "], Return Value = [" + padFedAgencyCostCode("9AB", false, 8) + "]");
		if (isEmptyStr(" L ")) System.out.println("Empty");
	}
	public static String massageProcessingCategory(String processingCategory) {

		if ("".equals(processingCategory)) {
			processingCategory = Constants.PROCESSING_CATEGORY_EVS_PARCEL_FIRSTCLASS;
		}
		return processingCategory;
	}
    public static boolean isEmptyStr(String s) {
    	return (s == null || s.trim().equals("") ? true : false); 
    }
    /**
     * Return a date/time format for a specific file format.
     */
    public static SimpleDateFormat getDateTimeConvert(int fileType)
    {
        if(fileType == MANIFEST)
        {
            return new SimpleDateFormat("yyyyMMddHHmmss");
        }
        else if(fileType == STATS)
        {
            return new SimpleDateFormat("yyyyMMdd");
        }
        else if(fileType == PDA)
        {
            return new SimpleDateFormat("MMddyyyy");
        }
        else if(fileType == PRS_PDA)
        {
			return new SimpleDateFormat("MMddyyyy");
        }
        else if(fileType == EXTRACT)
        {
            return new SimpleDateFormat("yyyyMMddHHmm");
        }
        else if(fileType == CEW_RECEIPT)
        {
            return new SimpleDateFormat("yyyyMMddHHmmss");
        }
        else if(fileType == CEW_MAILING)
        {
            return new SimpleDateFormat("yyyyMMdd");
        }
        else if(fileType == PASS)
        {
        	return new SimpleDateFormat("yyyyMMdd");
        }
        else if(fileType == CAD)
        {
        	return new SimpleDateFormat("MMddyyyy");
        }
        else
        {
            return null;
        }
    }

    /**
     * Return a time format for a specific file format
     */
    public static SimpleDateFormat getTimeCheck(int fileType)
    {
        if(fileType == MANIFEST)
        {
            return new SimpleDateFormat("HHmmss");
        }
        else if(fileType == STATS)
        {
            return new SimpleDateFormat("HHmm");
        }
        else if(fileType == PDA)
        {
            return null;
        }
		else if(fileType == PRS_PDA)
	   	{
		 	return null;
	   	}
        else if(fileType == EXTRACT)
        {
            return new SimpleDateFormat("HHmm");
        }
        else if(fileType == CEW)
        {
            return new SimpleDateFormat("HHmmss");
        }
        else
        {
            return null;
        }
    }

    /**
     * Return a date format for a specific file format.
     */
    public static SimpleDateFormat getDateCheck(int fileType)
    {
        if(fileType == MANIFEST)
        {
            return new SimpleDateFormat("yyyyMMdd");
        }
        else if(fileType == STATS)
        {
            return new SimpleDateFormat("yyyyMMdd");
        }
        else if(fileType == PDA)
        {
            return new SimpleDateFormat("MMddyyyy");
        }
		else if(fileType == PRS_PDA)
		{
			return new SimpleDateFormat("MMddyyyy");
		}
        else if(fileType == EXTRACT)
        {
            return new SimpleDateFormat("yyyyMMdd");
        }
        else if(fileType == CEW)
        {
            return new SimpleDateFormat("yyyyMMdd");
        }
        else
        {
            return null;
        }
    }

    /**
     * Determine if the value passed is a valid file format indicator for
     * a manifest file.
     */
    public static boolean isFileType(String value)
    {
        if(value.equalsIgnoreCase("1") ||
            value.equalsIgnoreCase("2") ||
            value.equalsIgnoreCase("3") ||
            value.equalsIgnoreCase("4") ||
            value.equalsIgnoreCase("5") ||
            value.equalsIgnoreCase("6") ||
            value.equalsIgnoreCase("7") ||
            value.equalsIgnoreCase("8") ||
            value.equalsIgnoreCase("9") ||
            value.equalsIgnoreCase("A") ||
            value.equalsIgnoreCase("B") ||
            value.equalsIgnoreCase("C"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Determine if the value passed is a proper file number format.
     */
    public static boolean isFileNumber(String value)
    {
        if(value.startsWith("91"))
        {
            return true;
        }
        else if(value.startsWith("50") && value.endsWith("  "))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * Determine if the value passed is the proper format for a PIC code.
     */
    public static boolean isPicCode(String value)
    {
        if(value.startsWith("91") || value.startsWith("02") || value.endsWith("  "))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

	public static boolean isEvsPrsPicCode(String value)
	{
		if(value.startsWith("91") && value.trim().length() == 22)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

    public static boolean isZone1To5(String value)
    {
        if(value.equals("01") ||
            value.equals("02") ||
            value.equals("03") ||
            value.equals("04") ||
            value.equals("05"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean isZone1To8(String value)
    {
        if(value.equals("01") ||
            value.equals("02") ||
            value.equals("03") ||
            value.equals("04") ||
            value.equals("05") ||
            value.equals("06") ||
            value.equals("07") ||
            value.equals("08"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean isZone1To5OrLocal(String value)
    {
        if(value.equals("01") ||
            value.equals("02") ||
            value.equals("03") ||
            value.equals("04") ||
            value.equals("05") ||
            value.equals("LC"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static boolean isZone1To8OrLocal(String value)
    {
        if(value.equals("01") ||
            value.equals("02") ||
            value.equals("03") ||
            value.equals("04") ||
            value.equals("05") ||
            value.equals("06") ||
            value.equals("07") ||
            value.equals("08") ||
            value.equals("LC"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Determine if the value passed is a valid ASCII character.
     */
    public static boolean isASCII(String value)
    {
        for(int n = 0; n != value.length(); n++)
        {
            if(Character.isISOControl(value.charAt(n)))
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Determine if the string passed is a proper numeric value.
     */
    public static boolean isNumber(String value)
    {
        for(int n = 0; n != value.length(); n++)
        {
            if(!Character.isDigit(value.charAt(n)))
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Determine if the string passed is a number with the correct
     * number of digits.
     */
    public static boolean isNumberOfLength(String value, int length)
    {
        if(value.trim().length() != length)
        {
            return false;
        }

        return isNumber(value);
    }

    /**
     * Determine if the value passed is a proper date format for the
     * specified file format.
     */
    public static boolean isDate(String value, int fileType)
    {
    	return isDate(value,fileType,true);
    }
    
	public static boolean isDate(String value, int fileType, boolean lenient)
	 {
		 SimpleDateFormat dateCheck = getDateCheck(fileType);
		 dateCheck.setLenient(lenient);

		 if(dateCheck == null)
		 {
			 return false;
		 }

		 try
		 {
			 dateCheck.parse(value);
		 }
		 catch(ParseException e)
		 {
			 return false;
		 }

		 return true;
	 }
 
	public static boolean isTime(String value, int fileType)
	 {
		 return isTime(value,fileType,true);
	 }
    /**
     * Determine if the value passed is a proper time format for the
     * specified file format.
     */
    public static boolean isTime(String value, int fileType, boolean lenient)
    {
        SimpleDateFormat timeCheck = getTimeCheck(fileType);
		timeCheck.setLenient(lenient);
		
        if(timeCheck == null)
        {
            return false;
        }

        try
        {
            timeCheck.parse(value);
        }
        catch(ParseException e)
        {
            return false;
        }

        return true;
    }

    /**
     * Determine if the string passed contains only whitespace characters.
     */
    public static boolean isBlank(String value)
    {
        if(value == null || value.trim().length() == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Determines if the value passed is a valid destination rate indicator
     * for a manifest file.
     */
    public static boolean isDestinationRateIndicator(String value) {

        if ("B".equalsIgnoreCase(value)
        	|| "D".equalsIgnoreCase(value)
            || "S".equalsIgnoreCase(value)
            || "A".equalsIgnoreCase(value)
            || "F".equalsIgnoreCase(value)
            || "N".equalsIgnoreCase(value)) {
            return true;
        }

		return false;
    }

    /**
     * Determine if the value passed is a proper rate indicator
     * for a manifest file.
     */
    public static boolean isRateIndicator(String value)
    {
		if (value.equalsIgnoreCase("1A") ||
			value.equalsIgnoreCase("1E") ||
			value.equalsIgnoreCase("2A") ||
			value.equalsIgnoreCase("2E") ||
			value.equalsIgnoreCase("3A") ||
			value.equalsIgnoreCase("3D") ||
			value.equalsIgnoreCase("3E") ||
			value.equalsIgnoreCase("5D") ||
			value.equalsIgnoreCase("AD") ||
			value.equalsIgnoreCase("B3") ||
			value.equalsIgnoreCase("BA") ||
			value.equalsIgnoreCase("BM") ||
			value.equalsIgnoreCase("BN") ||
//			value.equalsIgnoreCase("CR") ||
			value.equalsIgnoreCase("DN") ||
			value.equalsIgnoreCase("DR") ||
			value.equalsIgnoreCase("FB") ||
			value.equalsIgnoreCase("FE") ||
			value.equalsIgnoreCase("MA") ||
			value.equalsIgnoreCase("MB") ||
			value.equalsIgnoreCase("NP") ||
			value.equalsIgnoreCase("OS") ||
			value.equalsIgnoreCase("PR") ||
			value.equalsIgnoreCase("SP") ||
			// 15.4.0
			value.equalsIgnoreCase("PL") ||
			value.equalsIgnoreCase("PM") ||
			// REL18.0.0
			value.equalsIgnoreCase("FS") ||
			value.equalsIgnoreCase("PA") ||   // REL21.0.0
			value.equalsIgnoreCase("PP") ||   // REL21.0.0
			value.equalsIgnoreCase("E3") ||   // REL21.0.0
			value.equalsIgnoreCase("E4") ||   // REL21.0.0
			value.equalsIgnoreCase("E5") ||   // REL 26.0.0
			value.equalsIgnoreCase("E6") ||   // REL 26.0.0
			value.equalsIgnoreCase("CP") ||     // REL23.0.0	 
			value.equalsIgnoreCase("FA") ||   // REL 26.0.0
			value.equalsIgnoreCase("C6") ||   // REL 26.0.0
			value.equalsIgnoreCase("C7") ||   // REL 26.0.0
			value.equalsIgnoreCase("AL") ||   // REL 26.0.0
			value.equalsIgnoreCase("AF") ||   // REL 26.0.0

			value.equalsIgnoreCase("RG") ||   // REL 26.1.0
			value.equalsIgnoreCase("US") ||   // REL 26.1.0
			value.equalsIgnoreCase("UA") ||   // REL 26.1.0
			value.equalsIgnoreCase("U3") ||   // REL 26.1.0
			value.equalsIgnoreCase("U5") ||   // REL 26.1.0

			value.equalsIgnoreCase("N5") ||   // REL 26.0.0
			value.equalsIgnoreCase("NT") ||   // REL 26.0.0
			value.equalsIgnoreCase("ND") ||   // REL 26.0.0
			value.equalsIgnoreCase("NM") ||   // REL 26.0.0
			value.equalsIgnoreCase("NB") ||   // REL 26.0.0
			value.equalsIgnoreCase("NH") ||   // REL 26.0.0
			value.equalsIgnoreCase("NR") ||   // REL 26.0.0
			
			value.equalsIgnoreCase("CS") ||   // REL 26.0.0
			value.equalsIgnoreCase("CH") ||   // REL 26.0.0
			value.equalsIgnoreCase("CB") ||   // REL 26.0.0
			value.equalsIgnoreCase("FP"))     // REL23.0.0	 
		{
			return true;
		}
		else
		{
			return false;
		}

    }

    /**
     * Determine if the value passed is a simple Y or N character.
     */
    public static boolean isYesNo(String value)
    {
        if(value.equalsIgnoreCase("Y") || value.equalsIgnoreCase("N"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Determine if the value passed is a proper surcharge type used
     * in a manifest file.
     */
    public static boolean isSurchargeType(String value)
    {
        if(value.equalsIgnoreCase("N1") ||
            value.equalsIgnoreCase("N2") ||
            value.equalsIgnoreCase("N3") ||
            value.equalsIgnoreCase("D1") ||
            value.equalsIgnoreCase("D2") ||
            value.equalsIgnoreCase("D3") ||
			value.equalsIgnoreCase("D4") ||
			value.equalsIgnoreCase("D5") ||
			value.equalsIgnoreCase("D6") ||
            value.equalsIgnoreCase("D7") ||
			// REL16.1.0
			value.equalsIgnoreCase("D8") ||
			value.equalsIgnoreCase("D9") ||
			value.equalsIgnoreCase("A1") ||
			value.equalsIgnoreCase("F1") ||
			value.equalsIgnoreCase("P1"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Determine if the value passed is a proper event code used in an
     * RDU extract file.
     */
    public static boolean isEventCode(String value)
    {
        return isNumberOfRange(value, 15, 18, 2);
    }

    /**
     * Determine if the value passed is proper for the "No Weekend & Holiday
     * Delivery" field in a manifest file.
     */
    public static boolean isNoWeekendHolidayDelivery(String value)
    {
        return isNumberOfRange(value, 1, 4, 1);
    }

    /**
     * Determine if the value passed is a proper special service code used
     * within a manifest file.
     */
    public static boolean isSpecialServiceCode(String value)
    {
        return isNumberOfRange(value, 1, 19, 2); //REL21.0
    }

	/**
	 * Determine if the value passed is a proper special service code used
	 * within a manifest file.
	 */
	public static boolean isSpecialServiceCodeV15(String value)
	{
		return isNumberOfRange(value, 1, 999, 3); //REL21.0
	}


    /**
     * Determine if the value passed is a valid method of payment used
     * within a manifest file.
     */
    public static boolean isMethodOfPayment(String value)
    {
        return isNumberOfRange(value, 1, 4, 2);
    }

    /**
     * Determine if the value passed is a proper unit of measure code that
     * is used in manifest files.
     */
    public static boolean isUomCode(String value)
    {
        return isNumberOfRange(value, 0, 3, 1);
    }

    /**
     * Determine if the value passed is a valid processing category used
     * for manifest files.
     */
    public static boolean isProcessingCategory(String value)
    {
        return isProcessingCategory(value,value);
    }

    public static boolean isProcessingCategory(String value, String exception)
    {
    	boolean ret = true;
    	ret = "O".equals(exception);
    	if (! ret)
    		return isNumberOfRange(value, 0, 9, 1);
    	else
    		return ret;
    }

    
    /**
     * Determine if the number passed is between a specific range of values
     * and padded with the proper number of digits.
     */
    public static boolean isNumberOfRange(String value, int low, int high, int pad)
    {
        try
        {
            int intValue = Integer.parseInt(value);

            if(value.trim().length() == pad &&
                intValue >= low && intValue <= high)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }

    /**
     * Determine if the value passed is a proper e-VS service type code.
     */
	public static boolean isEvsSTC(String value)
	{
		if ((value != null) && (value.equals("01") ||
			value.equals("02") ||
			value.equals("05") ||
			value.equals("06") ||
			value.equals("07") ||
			value.equals("08") ||
			value.equals("09") ||
			value.equals("10") ||
			value.equals("13") || //18.0.0 Change
			value.equals("21") ||
			value.equals("22") ||
			value.equals("25") ||
			value.equals("26") ||
			value.equals("29") ||
			value.equals("30") ||
			value.equals("40") ||
			value.equals("41") ||
			value.equals("43") ||
//Removed REL22.0.0
//			value.equals("50") ||
			value.equals("55") ||
			value.equals("56") ||
			value.equals("73") ||
			value.equals("74") ||
			value.equals("75") ||
			value.equals("79")))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	
    /**
     * Determine if the value passed is a proper PRS service type code.
     */
    public static boolean isPrsSTC(String value)
    {
        if((value != null) && (value.equals("57") ||
//Removed REL22.0.0
//			value.equals("13")||
//			value.equals("EW")||  
            value.equals("58")))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

  

    /**
     * Determine if the string passed is a valid floating point number.
     */
    public static boolean isDouble(String value)
    {
        try
        {
            Double.parseDouble(value.trim());
        }
        catch(NumberFormatException e)
        {
            return false;
        }

        return true;
    }

    /**
     * Determine if the value passed is a simple 1 or 0.
     */
    public static boolean isBoolean(String value)
    {
        if(value.equals("0") ||
            value.equals("1"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Determine if the value passed is a simple Y or N.
     */
    public static boolean isBooleanYN(String value)
    {
        if(value.equals("Y"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    
    
    /**
     * Convert a string into a floating point value.
     */
    public static double toDouble(String value)
    {
        try
        {
            return Double.parseDouble(value.trim());
        }
        catch(NumberFormatException e)
        {
            return 0;
        }
    }

    /**
     * Convert a string into a floating point value.
     * Use the decimal value to indicate where the decimal point
     * is supposed to be in the string.
     */
    public static double toDouble(String value, int decimal)
    {
        double result;

        try
        {
            result = Double.parseDouble(value.trim());

            if(decimal > 0)
            {
                result /= Math.pow(10,  decimal);
            }
        }
        catch(NumberFormatException e)
        {
            result = 0;
        }

        return result;
    }

    /**
     * Convert a string into an integer value.
     */
    public static int toInteger(String value)
    {
        try
        {
            return Integer.parseInt(value.trim());
        }
        catch(NumberFormatException e)
        {
            return 0;
        }
    }

    /**
     * Convert a string to a Java calendar object from various file types.
     */
    public static Calendar toCalendar(String value, int fileType)
    {
        SimpleDateFormat dateTimeConvert = getDateTimeConvert(fileType);

        if(dateTimeConvert == null)
        {
            return null;
        }

        Calendar dateTime = Calendar.getInstance();

        try
        {
            dateTime.setTime(dateTimeConvert.parse(value.trim()));
        }
        catch(ParseException e)
        {
        }

        return dateTime;
    }

    /**
     * Determine the week number based on the day of the month.
     */
    public static int toMonthlyWeek(int dayOfMonth)
    {
	int week = ((dayOfMonth - 1) / 7) + 1;

        if(week > 4)
        {
            week = 4;
        }

        return week;
    }

    /**
     * Convert a calendar month to a fiscal month.
     */
    public static String toFiscalMonth(Calendar systemDate)
    {
        int month = systemDate.get(Calendar.MONTH) + 4;
        if(month > 12)
        {
            month -= 12;
        }

        if(month < 10)
        {
            return "0" + Integer.toString(month);
        }
        else
        {
            return Integer.toString(month);
        }
    }

    /**
     * Convert a calendar year to a fiscal year.
     */
    public static String toFiscalYear(Calendar systemDate)
    {
        int month = systemDate.get(Calendar.MONTH) + 4;
        if(month > 12)
        {
            month -= 12;
        }

        int year = systemDate.get(Calendar.YEAR);
        if(month <= 3)
        {
            year++;
        }

        return Integer.toString(year);
    }

    /**
     * Strip out the path from a file name.
     */
    public static String toFilenameOnly(String file)
    {
        return (new File(file)).getName();
    }

  

	public static BigDecimal toBigDecimal(String string, int decimal) {

		BigDecimal value;

		try {
			value = new BigDecimal(string);

            if(decimal > 0) {

            	// MOVE DECIMAL POINT
                BigDecimal factor = new BigDecimal(Math.pow(10,  decimal));
				value = value.divide(factor, decimal, BigDecimal.ROUND_UP);
            }

		} catch (NumberFormatException e) {
			return new BigDecimal(0);
		}
		return value;
	}

	public static BigDecimal format(BigDecimal unformated) {

		if (unformated == null)
		{
			return new BigDecimal(0);
		}
		
		return format(unformated, 3);
	}

	public static BigDecimal format(BigDecimal unformated, int places) {

		return unformated.setScale(places, BigDecimal.ROUND_HALF_UP);
	}

	public static boolean isBlankOrAscii(String string, int maxLength) {

		boolean validText = (DataTypes.isBlank(string) || DataTypes.isASCII(string));
		boolean correctLength = string.length() <= maxLength;

		return (validText && correctLength);
	}

	/**This function takes an array of keys for a given hashtable
	 * and checks to see if any of them are null.
	 * @return the key # that is null (index + 1) if one is found
	 * @return 0 if no null is found
	 */
	public static int checkKeysForNull(String[] keys){
		for(int i =0 ; i < keys.length ; i++)
		{
			if(keys[i] == null)
				return i + 1;
		}

		return 0;
	}

	public static boolean isFileVersionNumber(String value) {
		return ((Integer.parseInt(value.trim()) == 14 || Integer.parseInt(value.trim()) == 15 || Integer.parseInt(value.trim()) == 16 || Integer.parseInt(value.trim()) == 17 ) || Integer.parseInt(value.trim()) == 20 );
	}
	
	public static boolean isEntryFacilityType(String value) {
		 if ("B".equalsIgnoreCase(value)
			 || "D".equalsIgnoreCase(value)
			 || "S".equalsIgnoreCase(value)
			 || "A".equalsIgnoreCase(value)
			 || "F".equalsIgnoreCase(value)) {
			 return true;
		 }
		 return false;
	 }
	
	 public static boolean isShipmentFeeCode(String value) {
		if (isBlank(value) ||value.equals("PKF") || value.equals("RHF")) 
			return true;
		return false;
	 }

	public static boolean isParcelBarcode(String value) {
		return (value.equals("0") || value.equals("1") || value.equals("2"));
	}

	
	
	public static boolean isTransactionID (String transactionID) {
		boolean isValid = false;
		if (transactionID != null ) {
			if (transactionID.length() == 12) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					sdf.setLenient(false);
					sdf.parse(transactionID.substring(0,8));
					isValid = true;
				} catch (Exception e){
					return isValid;
				}

				isValid = DataTypes.isNumber(transactionID.substring(8,12));
			}
		}
		return isValid;
	}
	
	/**
	 * 
	 * @param Federal Agency Cost Code
	 * @return true if Not Empty else False;
	 */
	public static boolean isValidChargeBackCode(String code) {
		if (isEmptyStr(code) || code.length() < 6) return false;
		
		return true;
	}
	
	/**
	 * 
	 * @param Federal Agency Cost Code, Minimum Length of String 
	 * @return Padded String with Zeros if Code is less than 5 characters in length
	 */
	public static String padFedAgencyCostCode(String code, boolean paddingType, int n) {
		
		return code.length() < n ? paddingString(code, n, '0', paddingType): code;
	}
	 /**
	  ** pad a string S with a size of N with char C 
	  ** on the left (True) or on the right(flase)
	  **/
	  public static String paddingString (String s, int n, char c , boolean paddingLeft) {
	    StringBuffer str = new StringBuffer(s);
	    int strLength  = str.length();
	    if ( n > 0 && n > strLength ) {
	      for ( int i = 0; i <= n ; i ++ ) {
	            if ( paddingLeft ) {
	              if ( i < n - strLength ) str.insert( 0, c );
	            }
	            else {
	              if ( i > strLength ) str.append( c );
	            }
	      }
	    }
	    return str.toString();
	  }
	
	/**
	 * REL21.0
	 * 
	 * this method only checks  if originalvalue is blank or null, then change it to whatever is declared in replace parameter
	 * 
	 * @param originalValue
	 * @param replace
	 * @return replace value
	 */
	public static  String isBlankReplace(String originalValue, String replace) {
		String replaceValue = replace;
		if (originalValue == null)
			return replaceValue;
		else if (originalValue.trim().equals(""))
			return replaceValue;
		else
			return originalValue;	
	}
	
	public synchronized static Date convertToDateSql(Calendar cal) {
		// create new object so that it will not change the original cal
		Calendar calendar = Calendar.getInstance();
		cal.setTime(cal.getTime());
		// normalize the object
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		java.sql.Date javaSqlDate = new java.sql.Date(calendar.getTime().getTime());
		
		return javaSqlDate;
	}

	public static boolean isZone0To8OrLocal(String value)
	{
		return (isZone1To8OrLocal(value) || value.equalsIgnoreCase("00"));
	}
	
	public static boolean isZoneValidPerMailClass(String mailClass, String zone) { // REL 26.1.0 Change
		boolean ret = false;
		
		if (mailClass == null)
			return ret;
		
		if (isZone0To8OrLocal(zone)) {
			if ("PM,EX,PS".indexOf(mailClass) != -1)
				ret = true;
		}
		
		return ret;		
	}

	
	
	public synchronized static double roundUpToTwoDecimal(double number) {
		long longNumber = 0;
		longNumber = (long) Math.round(number * 100);
		double roundUpDecimal = (double) (longNumber/ (double) 100.0);
		return roundUpDecimal;
 	}
 	
 	
//	public static SpecialServiceVO createDelconServiceNew(SpecialServiceVO service, CommonAttributes detail)
//	{
//		EvsResourceModel evsResourceModel = EvsResourceModel.getEvsResourceModel();
//		Map resource = evsResourceModel.getResourceMap();
//		String rateSC[];
//		
//		String code = service.getServiceCode();
//		SpecialServiceVO rateService = new SpecialServiceVO();
//
//		String value = (String) resource.get("SC."+code);
//		if (value != null) {
//			rateSC= StringUtils.split(value,",");
//			rateService.setServiceCode(rateSC[0]);
//			rateService.setServiceSubCode(rateSC[1]);
//			if (! "NA".equals(rateSC[2])) {
//				if ("codamount".equalsIgnoreCase(rateSC[2])) {
//					rateService.setValue(detail.getCodAmountDueSender());
//				} else if ("articlevalue".equalsIgnoreCase(rateSC[2])) {
//					rateService.setValue(detail.getArticleValue());
//				} else {
//					double articleVal = DataTypes.toDouble((rateSC[2] == null?"":rateSC[2]));
//					rateService.setValue(articleVal);
//				}
//			}
//		}
//		return rateService;
//	}

	public static boolean isValidDiscountType(String discountType) {
		if (discountType == null)
			return false;
	/* This will be replaced by discount type table as specified in SRS REL24.0, V1.5 (POS 470-471) */
		if (discountType.trim().startsWith("D") || discountType.trim().equals(""))  
			return true; 
		else
			return false;
	}
	
	public static boolean isValidSurchargeType(String surchargeType) {
		if (surchargeType == null)
			return false;
	/* This will be replaced by surcharge table as specified in SRS REL24.0, V1.5 (POS 470-471) */
		if (surchargeType.trim().startsWith("N") || surchargeType.trim().equals("") ||
				surchargeType.trim().equals("A1") ||
				surchargeType.trim().equals("F1") ||
				surchargeType.trim().equals("P1"))  
			return true; 
		else
			return false;
	}
	
	
	

	   /**
     * Determine if the value passed is a proper weekend/holiday delivery indicator
     * for a manifest file.
     */
    public static boolean isWeekendHolidayDeliveryIndicator(String value)
    {
		if (value.equalsIgnoreCase("1") ||
			value.equalsIgnoreCase("2") ||
			value.equalsIgnoreCase("3") ||
			value.equalsIgnoreCase("4") ||
			value.equalsIgnoreCase("5") ||
			value.equalsIgnoreCase("6") ||
			value.equalsIgnoreCase("7") ||
			value.equalsIgnoreCase("8") ||
			value.equalsIgnoreCase("9") ||
			value.equalsIgnoreCase("E") ||
			value.equalsIgnoreCase("F") ||
			value.equalsIgnoreCase("G") ||
			value.equalsIgnoreCase("P") )     // REL26.0.0	 
		{
			return true;
		}
		else
		{
			return false;
		}

    }
    
	/* REL26.0 -- Req 4.2.4
	 * Rounding Down to nearest 1/4 with 2 decimal point.
	 * Example:
	 * 		0.26 --> 0.25
	 * 		0.49 --> 0.25
	 * 		0.56 --> 0.50
	 * 		0.74 --> 0.50 
	 * 		0.99 --> 0.75
	 */
	public static BigDecimal roundDownToNearestQuater(double d) {
		BigDecimal bi = new BigDecimal(Double.toString(d));
		return roundDownToNearestQuater(bi);
	}

	/* REL26.0 -- Req 4.2.4
	 * Rounding Down to nearest 1/4 with 2 decimal point.
	 * Example:
	 * 		0.26 --> 0.25
	 * 		0.49 --> 0.25
	 * 		0.56 --> 0.50
	 * 		0.74 --> 0.50 
	 * 		0.99 --> 0.75
	 */
	public static BigDecimal roundDownToNearestQuater(BigDecimal d) {
		int dComp = d.compareTo(new BigDecimal("0.25"));
		if ((dComp < 0) || (dComp == 0)) {
			return d; /* Return whatever number is */
			//return (new BigDecimal("0.25"));  /* return 0.25 for a number that less than 0.25 */ 
		}
		BigDecimal newBi = d.divide(new BigDecimal("0.25"), 0, BigDecimal.ROUND_FLOOR);
		BigDecimal nearestQuater = newBi.divide(new BigDecimal("4.0"), 2, BigDecimal.ROUND_DOWN);
		return nearestQuater;
	}
	
    public static double roundToNearestHalf(double round)
    {
    	round = round*2;
    	if( (int)round < round) 
    		round = (int)(round+1);
    	return (double)round/2;  	
    }
    
	public static boolean isBarcodeInternational(String barcodeConstruct) {
		return ("G01".equals(barcodeConstruct.trim()) || "I01".equals(barcodeConstruct.trim()));
	}
	
  
}
