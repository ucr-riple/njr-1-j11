package yeah.cstriker1407.design_patterns.decorator;

public class DecoratorTest
{
	public static void test()
	{
		SchoolReport sr = new FinalTestReport();

		sr = new HighScoreDecorator(sr);
		sr = new SortDecorator(sr);
		
		sr.report();
	}
}
abstract class SchoolReport
{
	public abstract void report();
	public abstract void sign();
}

class FinalTestReport extends SchoolReport
{
	@Override
	public void report()
	{
		System.out.println("final test report");
	}

	@Override
	public void sign()
	{
		System.out.println("sign here");
	}
}

abstract class DecoratorSchoolReport extends SchoolReport
{
	private SchoolReport report;

	public DecoratorSchoolReport(SchoolReport report)
	{
		super();
		this.report = report;
	}

	@Override
	public void report()
	{
		report.report();
	}

	@Override
	public void sign()
	{
		report.sign();
	}
}
class HighScoreDecorator extends DecoratorSchoolReport
{
	public HighScoreDecorator(SchoolReport report)
	{
		super(report);
	}
	@Override
	public void report()
	{
		System.out.println("show high score before show Final Test result");
		super.report();
	}
}

class SortDecorator extends DecoratorSchoolReport
{
	public SortDecorator(SchoolReport report)
	{
		super(report);
	}
	@Override
	public void report()
	{
		super.report();
		System.out.println("show sort number after show Final Test result");
	}
}