import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.fuzzylite.Engine;
import com.fuzzylite.FuzzyLite;
import com.fuzzylite.Op;
import com.fuzzylite.defuzzifier.Centroid;
import com.fuzzylite.imex.FisImporter;
import com.fuzzylite.imex.JavaExporter;
import com.fuzzylite.norm.s.Maximum;
import com.fuzzylite.norm.t.Minimum;
import com.fuzzylite.rule.Rule;
import com.fuzzylite.rule.RuleBlock;
import com.fuzzylite.term.Accumulated;
import com.fuzzylite.term.Term;
import com.fuzzylite.term.Thresholded;
import com.fuzzylite.term.Triangle;
import com.fuzzylite.variable.InputVariable;
import com.fuzzylite.variable.OutputVariable;

public class MyFuzzyLiteClass{

	public static void main(String[] args) throws IOException{
        Engine engine = new Engine("simple-dimmer");

        InputVariable ambient = new InputVariable();
        ambient.setName("Ambient");
        ambient.setRange(0.000, 1.000);
        ambient.addTerm(new Triangle("DARK", 0.000, 0.500));
        ambient.addTerm(new Triangle("MEDIUM", 0.350, 0.750));
        ambient.addTerm(new Triangle("BRIGHT", 0.500, 1.000));
        ambient.setEnabled(false);
        engine.addInputVariable(ambient);

        OutputVariable power = new OutputVariable();
        power.setName("Power");
        power.setRange(0.000, 1.000);
        power.setDefaultValue(Double.NaN);
        power.addTerm(new Triangle("LOW", 0.000, 0.500));
        power.addTerm(new Triangle("MEDIUM", 0.340, 0.750));
        power.addTerm(new Triangle("HIGH", 0.500, 1.000));
        engine.addOutputVariable(power);

        RuleBlock ruleBlock = new RuleBlock();
        ruleBlock.addRule(Rule.parse("if Ambient is DARK then Power is HIGH", engine));
        ruleBlock.addRule(Rule.parse("if Ambient is MEDIUM then Power is MEDIUM", engine));
        ruleBlock.addRule(Rule.parse("if Ambient is BRIGHT then Power is LOW", engine));
        engine.addRuleBlock(ruleBlock);

        //No Conjunction or Disjunction is needed
        engine.configure("", "", "AlgebraicProduct", "AlgebraicSum", "Centroid");

        /*StringBuilder status = new StringBuilder();
        if (!engine.isReady(status))
            throw new RuntimeException("Engine not ready. " +
                    "The following errors were encountered:\n" + status.toString());*/

        for (int i = 1; i < 50; ++i) {
            double light = ambient.getMinimum() + i * (ambient.range() / 50);
            ambient.setInputValue(light);
            engine.process();

            double result;

            /*FuzzyLite.logger().info(String.format(
                    "Ambient.input = %s -> Power.output = %s",
                    Op.str(light), Op.str(power.defuzzify())));*/

            List<Term> listIn = ambient.getTerms();
            List<Term> listOut = power.getTerms();

            System.out.println(ambient.fuzzify(light)); //***************************
            result = power.defuzzify();
            System.out.println(power.fuzzify(result)); //***************************
            System.out.println("Ambient: " + Op.str(light) + ", Power: " + Op.str(result));

            double res;
            for (Term element : listIn) {
                System.out.println(element.toString());
                res = element.membership(light);
                System.out.println(Op.str(light) + "  " +
                        Op.str(res));
            }
            for (Term element : listOut) {
                System.out.println(element.toString());
                res = element.membership(result);
                System.out.println(Op.str(result) + "  " +
                        Op.str(res));
            }
            System.out.println("---------------------------------------");
        }
    }

	static String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}
}
