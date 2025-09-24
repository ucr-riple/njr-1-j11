/*
 * Created on Apr 4, 2008
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.evs.data.util;

import java.util.Hashtable;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;

import com.usps.evs.vo.PicCodeParserManifestV15;

/**
 * @author x6dxb0
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CommonUtils {
	private static Hashtable lengthPerBc = new Hashtable();
	static {
		lengthPerBc.put("C01",new Integer(PicCodeParserManifestV15.C01PicCodeLength));
		lengthPerBc.put("C02",new Integer(PicCodeParserManifestV15.C02PicCodeLength));
		lengthPerBc.put("C03",new Integer(PicCodeParserManifestV15.C03PicCodeLength));
		lengthPerBc.put("C04",new Integer(PicCodeParserManifestV15.C04PicCodeLength));
		lengthPerBc.put("C05",new Integer(PicCodeParserManifestV15.C05PicCodeLength));
		lengthPerBc.put("C06",new Integer(PicCodeParserManifestV15.C06PicCodeLength));
		lengthPerBc.put("C07",new Integer(PicCodeParserManifestV15.C07PicCodeLength));
		lengthPerBc.put("C08",new Integer(PicCodeParserManifestV15.C08PicCodeLength));
		lengthPerBc.put("C09",new Integer(PicCodeParserManifestV15.C09PicCodeLength));
		lengthPerBc.put("C10",new Integer(PicCodeParserManifestV15.C10PicCodeLength));
		lengthPerBc.put("",new Integer(PicCodeParserManifestV15.C00PicCodeLength));
		lengthPerBc.put("L01",new Integer(PicCodeParserManifestV15.L01PicCodeLength));
		lengthPerBc.put("I01",new Integer(PicCodeParserManifestV15.I01PicCodeLength));
		lengthPerBc.put("G01",new Integer(PicCodeParserManifestV15.G01PicCodeLength));
	};

	public synchronized static String[] split(String text, String separator)
		{

			StringTokenizer tok = new StringTokenizer(text, separator);
			int listSize = tok.countTokens();

			String[] list = new String[listSize];
			int i = 0;
			while (tok.hasMoreTokens())
			{
				list[i] = tok.nextToken().trim();
				i++;
			}
			return list;
	}
	public static final String PATTERN_EVS_MAN_RATE_IND_DISC_SURCH = "evs.man.rate.ind.disc.surc.";

	public synchronized static boolean shouldSurchargeDiscountBeApplied(String evsMailClass, String evsRateInd, Map<String, String> resourceMap, String discountSurcharge) {
		boolean ret = false;
		
		String validateRateIndDiscSurch = null;
		if (resourceMap != null) {
			validateRateIndDiscSurch = resourceMap.get(PATTERN_EVS_MAN_RATE_IND_DISC_SURCH+evsMailClass.trim().toUpperCase());
		}
		if ( (discountSurcharge != null) && 
			  (validateRateIndDiscSurch != null) && ((validateRateIndDiscSurch.indexOf(evsRateInd.trim().toUpperCase())) != -1)
			) {
				ret = true;
		} 
		return ret;
	}

	public synchronized static String padPicCodeV15(String picCode, String barCodeConstruct) {
		String newPicCode = picCode.trim();
		barCodeConstruct = barCodeConstruct.trim();
		
		Integer intObj = (Integer) lengthPerBc.get(barCodeConstruct);
		
		if (intObj == null)
			return newPicCode;  // Do not change when barcode construct is not in one of those declared ones
			
		int length = intObj.intValue();
		
		newPicCode = StringUtils.replace(newPicCode," ","0"); // replacing all blanks to 0
		
		if (newPicCode.length() < length) {
			newPicCode =	StringUtils.rightPad(newPicCode, length, "0"); // padding 0 to right if it is short 
		}
		
		return newPicCode;
	}

	public synchronized static boolean isLengthPicCodeValidPerBarcodeConstruct(String picCode, String barCodeConstruct) {
		String newPicCode = picCode.trim();
		barCodeConstruct = barCodeConstruct.trim();
		boolean ret = true;
		
		Integer intObj = (Integer) lengthPerBc.get(barCodeConstruct);
		
		if (intObj == null)
			return ret; // Return true, there is no point of reference on what barcode to be used to validate the length with 

		int length = intObj.intValue();
		
		if (newPicCode.length()> length)
			ret = false;
			
		return ret; 
	}
	
	/**
	 * 
	 */
	public CommonUtils() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
	}
}
