package com.usps.data;

import com.usps.evs.vo.ManifestHeader;
import com.usps.evs.vo.ManifestHeaderRawLineVo;
import com.usps.evs.vo.ManifestHeaderRawLineVoV20;

public class ManifestHeaderProcessor {

	 public ManifestHeader parseHeaderLine(String line)
	 {		
		 ManifestHeader header = new ManifestHeader();
			ManifestHeaderRawLineVo headerRawLine = new ManifestHeaderRawLineVo();
			if (headerRawLine.getFileVersion(line).equals("020")) {			
				ManifestHeaderRawLineVoV20 headerRawLineV20 = new ManifestHeaderRawLineVoV20();	
				header = headerRawLineV20.parseLine(line);
			} 	
		return header;
	 }
	 

}
