package darwInvest.news.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The NewsTestSummarizer takes a series of test results
 * and places them in a readable form
 * @author Kevin Dolan
 */
public class NewsTestSummarizer {

	private static String testName = "KNN-50-VOTE";
	
	private static int[] timeFrames = new int[] {3,7,14,30};
	
	public static void main(String[] args) {
		
		NewsTestResult byYear = new NewsTestResult();
		NewsTestResult byTrainingTime = new NewsTestResult();
		NewsTestResult byLookback = new NewsTestResult();
		NewsTestResult byForecast = new NewsTestResult();
		
		File dir = new File("TestResults/"+testName);
		File[] files = dir.listFiles();
		
		for(File file : files) {
			System.out.println("Processing file: " + file);
			
			int count = 0;
			int numCorrect = 0;
			ArrayList<Double> predicted = new ArrayList<Double>();
			ArrayList<Double> actual = new ArrayList<Double>();
			double sumPredicted = 0;
			double sumActual = 0;
			
			try {
				BufferedReader in = new BufferedReader(new FileReader(file));
				String str;
				while ((str = in.readLine()) != null) {
					String[] split = str.split(",");
					count++;
					if(split[3].equals("true"))
						numCorrect++;
					
					double prediction = Double.parseDouble(split[1]);
					double actualChange = Double.parseDouble(split[2]);
					
					predicted.add(prediction);
					actual.add(actualChange);
					
					sumPredicted += prediction;
					sumActual += actualChange;
				}
				in.close();
			} catch (IOException e) {}
			
			double muX = sumPredicted / count;
			double muY = sumActual / count;
			
			double cov = 0;
			double errX = 0;
			double errY = 0;
			
			for(int i = 0; i < predicted.size(); i++) {
				cov += (predicted.get(i) - muX) * (actual.get(i) - muY);
				errX += (predicted.get(i) - muX) * (predicted.get(i) - muX);
				errY += (actual.get(i) - muY) * (actual.get(i) - muY);
			}
			
			double r = cov / (Math.sqrt(errX) * Math.sqrt(errY));
			double r2 = r * r;
			double accuracy = numCorrect / (double) count;
			
			String filename = file.getName();
			String[] split = filename.split("_");
			
			String ticker = split[0];
			int start = Integer.parseInt(split[1]);
			int year = Integer.parseInt(split[2]);
			int lookBack = timeFrames[Integer.parseInt(split[3])];
			int forecast = timeFrames[Integer.parseInt(split[4].substring(0,1))];
			
			byYear.put(year, ticker, accuracy, r2);
			byTrainingTime.put(year - start, ticker, accuracy, r2);
			byLookback.put(lookBack, ticker, accuracy, r2);
			byForecast.put(forecast, ticker, accuracy, r2);
		}
		
		System.out.println("All files processed, aggregating results.");
		File summDir = new File(dir, "Summary");
		summDir.mkdir();
		
		try {
			File outFile = new File(summDir,"year_acc.csv");
			BufferedWriter out = new BufferedWriter(new FileWriter(outFile));
			out.write(byYear.output("Year", true));
			out.close();
			
			/*outFile = new File(summDir,"year_corr.csv");
			out = new BufferedWriter(new FileWriter(outFile));
			out.write(byYear.output("Year", false));
			out.close();*/
			
			outFile = new File(summDir,"training_acc.csv");
			out = new BufferedWriter(new FileWriter(outFile));
			out.write(byTrainingTime.output("Training Time (years)", true));
			out.close();
			
			/*outFile = new File(summDir,"training_corr.csv");
			out = new BufferedWriter(new FileWriter(outFile));
			out.write(byTrainingTime.output("Training Time (years)", false));
			out.close();*/
			
			outFile = new File(summDir,"lookback_acc.csv");
			out = new BufferedWriter(new FileWriter(outFile));
			out.write(byLookback.output("Lookback (days)", true));
			out.close();
			
			/*outFile = new File(summDir,"lookback_corr.csv");
			out = new BufferedWriter(new FileWriter(outFile));
			out.write(byLookback.output("Lookback (days)", false));
			out.close();*/
			
			outFile = new File(summDir,"forecast_acc.csv");
			out = new BufferedWriter(new FileWriter(outFile));
			out.write(byForecast.output("Forecast (days)", true));
			out.close();
			
			/*outFile = new File(summDir,"forecast_corr.csv");
			out = new BufferedWriter(new FileWriter(outFile));
			out.write(byForecast.output("Forecast (days)", false));
			out.close();*/
		} catch (IOException e) {}
		
		System.out.println("All Operations Complete.");
	}
	
}
