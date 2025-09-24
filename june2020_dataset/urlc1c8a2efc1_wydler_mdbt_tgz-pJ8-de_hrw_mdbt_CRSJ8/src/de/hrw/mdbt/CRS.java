package de.hrw.mdbt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.db4o.ObjectContainer;

import de.hrw.mdbt.model.Branch;
import de.hrw.mdbt.model.Person;
import de.hrw.mdbt.model.PriceClass;
import de.hrw.mdbt.model.VehicleGroup;


public class CRS {

	private static final String DB_TESTFILE = "Test.db4o";

	private static ObjectContainer db;

	public static void main(String[] args) {
		CRSOperations ops = new CRSOperations(DB_TESTFILE);
		db = ops.getDb();
		if(db.query(Branch.class).size()==0)
			ops.fillDBDefaults();

		Scanner input = new Scanner(System.in);
		String s = "";

		while(!s.equals("q")) {
			System.out.println("Branch auswählen:");
			List<Branch> branches = db.query(Branch.class);
			for(int i=0; i<branches.size(); i++) {
				System.out.println(i+" - "+branches.get(i).getName());
			}
			Branch branch = branches.get(input.nextInt());

			System.out.println("\nKunde auswählen:");
			List<Person> customers = db.query(Person.class);
			for(int i=0; i<customers.size(); i++) {
				System.out.println(i+" - "+customers.get(i).getLastname());
			}
			Person customer = customers.get(input.nextInt());

			System.out.println("\nPreisklasse auswählen:");
			List<PriceClass> priceClasses = db.query(PriceClass.class);
			for(int i=0; i<priceClasses.size(); i++) {
				System.out.println(i+" - "+priceClasses.get(i).getName());
			}
			PriceClass priceClass = priceClasses.get(input.nextInt());

			SimpleDateFormat sdfToDate = new SimpleDateFormat("dd.MM.yyyy");
			Date start = null;
			Date end = null;
			try {
				System.out.println("\nStartdatum eingeben:");
				start = sdfToDate.parse(input.next());
				System.out.println("\nEnddatum eingeben:");
				end = sdfToDate.parse(input.next());
			} catch (ParseException e) {
				e.printStackTrace();
			}

			System.out.println("\nFahrzeuggruppe auswählen:");
			List<VehicleGroup> vehicleGroups = CRSOperations.createOffer(db, branch, start, end, priceClass);
			for(int i=0; i<vehicleGroups.size(); i++) {
				System.out.println(i+" - "+vehicleGroups.get(i).getPower()+" PS");
			}
			VehicleGroup vehicleGroup = vehicleGroups.get(input.nextInt());

			CRSOperations.createReservation(db, customer, branch, vehicleGroup, start, end);

			System.out.println("\n'q' zum Beenden - Beliebige Taste zum fortführen");
			s = input.nextLine();
			s = input.nextLine(); // annoying Scanner
		}

		input.close();
		db.close();
	}
}
