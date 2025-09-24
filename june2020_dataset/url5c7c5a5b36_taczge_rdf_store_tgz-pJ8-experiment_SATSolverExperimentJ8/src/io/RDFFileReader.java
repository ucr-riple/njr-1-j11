package io;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import core.Triple;
import core.Triples;

public class RDFFileReader implements Reader {

	@Override
	public Triples read(String filePath) {
		Triples triples = new Triples();
		
		for ( final String line : getLines(filePath) ) {
			String[] tokens = line.split(",");
			
			if ( tokens.length != 3 ) {
				continue;
			}
			
			String s = tokens[0].trim();
			String p = tokens[1].trim();
			String o = tokens[2].trim();
			
			triples.add( new Triple(s, p, o) );
		}

		return triples;
	}
	
	private List<String> getLines(String filePath) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get(filePath), Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}

}
