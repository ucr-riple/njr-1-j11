import absmodJ.ConfidenceInterval;

public class TableHelper {

	public static void printTable(ConfidenceInterval cfDiff21ForCosts,
			ConfidenceInterval cfDiff31ForCosts,
			ConfidenceInterval cfDiff41ForCosts) {
		String breakLine = "--------------------------------------------------------------------------------------------------------------------------------------------------------";

		// Create the table
		ConfidenceInterval[] intervals = new ConfidenceInterval[] {
				cfDiff21ForCosts, cfDiff31ForCosts, cfDiff41ForCosts};	
		
		System.out.println(breakLine);
		System.out.printf("                         "); // First column for run
														// numbers
		System.out
				.printf("       y(n)         ||       S(n)         ||       zeta       ||");
		System.out
				.printf("       CI Min       ||       CI Max       ||       r\n");
		System.out.printf(breakLine);

		
		
		String [] names = {"Diff21","Diff31","Diff41"};
		for (int i =0; i < intervals.length; i++){
			String name = names[i];
			double [] values = {
					intervals[i].getPointEstimate(),
					intervals[i].getStdDev(),
					intervals[i].getZeta(),
					intervals[i].getCfMin(),
					intervals[i].getCfMax(),
					intervals[i].getZeta() / intervals[i].getPointEstimate()
			};
			printTableRow(name, values);
		}

		System.out.println("\n" + breakLine);

	}

	private static void printTableRow(String name, double[] array) {
		System.out.printf("\n" + fixedLengthString(name, 18) + " | ");
		for (int i = 0; i < array.length; i++) {
			double x = array[i];
			System.out.printf("      %9.3f      ", x);
		}
	}

	private static String fixedLengthString(String string, int length) {
		return String.format("%1$" + length + "s", string);
	}

}
