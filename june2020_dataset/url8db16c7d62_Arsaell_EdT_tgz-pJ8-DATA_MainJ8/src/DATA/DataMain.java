package DATA;

import GUI.EdTViewerWindow;

public class DataMain	{

	public static void main(String[] args)	{
	
		//System.out.println("\n\n\n\t##########\n\n\n");

		DataStore ds = new DataStore();
		ds.addFixtures();
		Filler filler = new Filler(System.out, ds);
		//System.out.println("Main.main() : Filler built.");
		
		HashMap<Constrainable, Double> constraints = filler.computeConstraints(true);
		
		//System.out.println("Main.main() : Constraints computed.");
		
		//System.out.println("Main.main() : Teachers attributed to groups.");
		
		int errors = filler.fill(constraints, Filler.RETRY);
		
		//classrooms.get(0).getWeekTable().print();
		
		System.out.println("Main.main() : Groups filled : " + errors + " errors.");
		
		//new EdTViewerWindow(ds.getClassrooms().get(0).getWeekTable());
		
		//new WelcomeFrame();
		/*
		
		Time time = new Time();
		for (Field f : ds.getGroups().get(1).getClasses().keySet())
			time = time.add(ds.getGroups().get(1).getClasses().get(f));
		
		for (Group g : ds.getGroups())	{
			Time t = new Time();
			for (Slot s : g.getWeekTable().getSlots())
				if (s instanceof Lesson)
					t = t.add(s.getDuration());
			System.out.println(g + " --> " + t + " / " + time);
			new EdTViewerWindow(g.getWeekTable());
		}
		//*/
	}
}
