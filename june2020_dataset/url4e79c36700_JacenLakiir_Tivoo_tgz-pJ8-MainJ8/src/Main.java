import tivoo.TivooSystem;


public class Main
{
    public static void main (String[] args) throws Exception
    {
        TivooSystem s = new TivooSystem();

        System.out.println("Loading XML input...");
        s.loadFile("data/tv.xml");

        System.out.println("Filtering events...");
        s.filterByKeyword("Hollywood", true);
        s.sortByTitle(false);

        System.out.println("Creating HTML output...");
        s.outputVerticalView("output/vert.html");
        s.outputHorizontalView("output/horiz.html");
        s.outputSortedView("output/sorted.html");
        s.outputConflictView("output/conflict.html");
        s.outputCalendarView("output/calendar.html");

        System.out.println("Done!");
    }
}
