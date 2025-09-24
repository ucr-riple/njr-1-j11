package logfilegen;

import java.text.ParseException;

import logfilegen.allanalytics.AnalyticTxtView;
import logfilegen.allmodels.Log;

public class Analytic {
	public String txtAnalizator(String file) throws ParseException{
		Parser parser = new Parser();
		Log pLog = parser.parser(file);
		
		AnalyticTxtView aTxt = new AnalyticTxtView(pLog);
		
		StringBuilder builder = new StringBuilder();
		String line = System.getProperty("line.separator");
		
		
		builder.append("Status frequency:");
		builder.append(line);
		builder.append(aTxt.getStatusFrequency());
		builder.append(line);
		
		builder.append("Status percent:");
		builder.append(line);
		builder.append(aTxt.getStatusPercent());
		builder.append(line);
		
		builder.append("Status frequency over hours:");
		builder.append(line);
		builder.append(aTxt.getStatusFrequencyOverHours());
		builder.append(line);
		
		builder.append("Status frequency over part of day:");
		builder.append(line);
		builder.append(aTxt.getStatusFrequencyOverPartOfDay());
		builder.append(line);
		
		builder.append("Extension frequency:");
		builder.append(line);
		builder.append(aTxt.getExtensionFrequency());
		builder.append(line);
		
		return builder.toString();
		}
}

