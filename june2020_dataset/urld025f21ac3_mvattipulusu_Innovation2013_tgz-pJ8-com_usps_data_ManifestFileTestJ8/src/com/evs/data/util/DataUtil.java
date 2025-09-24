package com.evs.data.util;

import java.util.Vector;

import com.usps.evs.vo.ManifestDetail;
import com.usps.evs.vo.ManifestDetailRawLineV20Vo;
import com.usps.evs.vo.ManifestHeader;
import com.usps.evs.vo.ManifestHeaderRawLineVo;
import com.usps.evs.vo.ManifestHeaderRawLineVoV20;
import com.usps.evs.vo.PicCodeVO;
import com.usps.evs.vo.SpecialServiceVO;

public class DataUtil {

	public static String getFileVersion (String line) {
		String fileVersion = "0";
    	if (line.indexOf("|") != -1)
    	{
    		int tokenCount = 0;
    		PipeParser pp = new PipeParser(line);
    		while (true) {
    			fileVersion = pp.nextToken();
    			tokenCount++;
    			if (tokenCount == 13) {
    				return fileVersion;
    			}
    			if (tokenCount == 14) {
    				return "0";
    			}
    		}    		
    	}
    	
		if (checkMinimumLength(line,77)) { 
			return line.substring(74, 77).trim();
		} else {
			return "0";
		}
    }

	
	
	private static boolean checkMinimumLength(String line, int i) {
		if (line.length() < i) {
		
			return false;
		}
		return true;
	}
}
