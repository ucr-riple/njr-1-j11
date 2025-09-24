package main;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.ArrayList;
import java.util.Arrays;

import data.structures.HashBitmapPair;
import data.structures.Schema;
import data.structures.SchemaInterface;
import data.structures.Template;
import data.structures.Text;
import cl.niclabs.skandium.Skandium;
import cl.niclabs.skandium.Stream;
import cl.niclabs.skandium.skeletons.Map;
import cl.niclabs.skandium.skeletons.Skeleton;
import concurrent.collision.detection.ExecuteContainsCheck;
import concurrent.collision.detection.MergeCollisions;
import concurrent.hashing.ExecuteHashing;
import concurrent.hashing.MergeHashes;
import concurrent.hashing.SplitSchema;
import concurrent.trie.insertion.ExecuteInsertion;
import concurrent.trie.insertion.MergeNothing;
import concurrent.trie.insertion.SplitHashArray;
import data.structures.HashList;

public class Main {

	private static final Text k = new Text(
			"# # und #, # für # vom 12.11.2011 an den Dekan der Fakultät #, Herrn # # #, # Sie auf die # des Rektorats # der # reagieren. Wir # uns # Ihr Interesse, # über das von Ihnen # # Urteil zu unserer bisherigen # # der #. # erkennen wir # der von Ihnen # # an. # die # nutzen und Ihnen die # # # #.",
			new ArrayList<Template>(Arrays.asList(
					new Template("Sehr geehrte", "Liebe"),
					new Template("Damen", "Kolleginnen"),
					new Template("Herren", "Kollegen"),
					new Template("wir bedanken uns", "ich bedanke mich"),
					new Template("Ihr Schreiben", "Ihre EMail"),
					new Template("Informatik, Mathematik und Naturwissenschaften", "IMN"),
					new Template("Prof.", "Professor"),
					new Template("Dr.", "Doktor"),
					new Template("Martin", "Tobias Martin"),
					new Template("in dem", "indem"),
					new Template("Stellenabbauplänen", "Kürzungspläne"),
					new Template("im Bereich", "bezüglich"),
					new Template("Mathematikausbildung", "Angewandten Mathematik"),
					new Template("freuen", "bedanken"),
					new Template("über", "für"),
					new Template("insbesondere", "besonders"),
					new Template("geäußerte", "genannte"),
					new Template("positive", "gute"),
					new Template("Arbeit", "Anstrengung"),
					new Template("im Bereich", "bezüglich"),
					new Template("Angewandten Mathematik", "Mathematikausbildung"),
					new Template("Darüber hinaus", "Außerdem"),
					new Template("die Mehrheit", "den Großteil"),
					new Template("geäußerten", "genannten"),
					new Template("Argumente", "Kritikpunkte"),
					new Template("Gerne würden wir nun", "Nun würden wir gerne"),
					new Template("Gelegenheit", "Chance"),
					new Template("Situation", "Lage"),
					new Template("von unserer Seite", "aus unserer Sicht"),
					new Template("objektiv", "sachlich"),
					new Template("darzustellen", "darzulegen"))));

	private static final Text k_ = new Text(
			"# # und #, # für # vom 12.11.2011 auf die #. Wir # uns zwar stets über #, ich # # # #, dass das Rektorat # die zu # # # und auf # nur # eingegangen werden kann. Es steht fest, dass # # wird, auch wenn die # des SMWK # abgewiesen werden. Bitte finden Sie sich # mit dem gefassten # ab. # # ich Sie bitten, # die zu # # zu nennen, damit wir dem SMWK unsere konkreten # # können.",
			new ArrayList<Template>(Arrays.asList(
					new Template("Sehr geehrte", "Liebe"),
					new Template("Damen", "Kolleginnen"),
					new Template("Herren", "Kollegen"),
					new Template("wir bedanken uns", "ich bedanke mich"),
					new Template("Ihre Reaktion", "Ihre Antwort"),
					new Template("Stellenabbaupläne", "Kürzungspläne"),
					new Template("freuen", "erfreuen"),
					new Template("Kritik", "neue Ansichten"),
					new Template("möchte", "will"),
					new Template("jedoch", "aber"),
					new Template("wiederholt", "noch einmal"),
					new Template("darauf hinweisen", "anmerken"),
					new Template("allein", "unabhängig"),
					new Template("kürzenden", "streichenden"),
					new Template("Stellen", "Professuren"),
					new Template("bestimmt", "festlegt"),
					new Template("Kritik", "Gegenargumente"),
					new Template("bedingt", "teilweise"),
					new Template("der Bereich der Angewandten Mathematik", "die Mathematikausbildung"),
					new Template("geschlossen", "weggekürzt"),
					new Template("Pläne", "Zukunftspläne"),
					new Template("wider erwarten", "entgegen allen Erwartungen"),
					new Template("endlich", "endgültig"),
					new Template("Beschluss", "Kürzungsplan"),
					new Template("Desweiteren", "Außerdem"),
					new Template("möchte", "will"),
					new Template("selbst", "eigenständig"),
					new Template("kürzenden", "streichenden"),
					new Template("Stellen", "Professuren"),
					new Template("Pläne", "Kürzungspläne"),
					new Template("vorlegen", "abliefern"))));
	
	public static final ConcurrentHashMap<String, HashBitmapPair> hashmap = new ConcurrentHashMap<String, HashBitmapPair>();
	public static Skandium skandium;
	
	public static String byteToString(byte[] bytes) {
		String s = "";
		for (byte b : bytes) {
			s += b + ",";
		}
		return s;
	}
	
	public static HashList hashFromSchema(SchemaInterface schema, int splitParts, int hashPower) throws Exception {
		Skeleton<SchemaInterface, HashList> skeleton = new Map<SchemaInterface, HashList>(
				new SplitSchema(splitParts),
				new ExecuteHashing(hashPower),
				new MergeHashes());
		Stream<SchemaInterface, HashList> stream = Main.skandium.newStream(skeleton);
		
		long init = System.currentTimeMillis();
		Future<HashList> future = stream.input(schema);
		HashList list = future.get();
		long time = System.currentTimeMillis() - init;
		System.out.println("Hashing of Scheme: " + (time) + "[ms]");
		
		return list;
	}
	
	public static HashList findCollisions(HashList list, int splitParts) throws Exception {
		Skeleton<HashList, HashList> skeleton = new Map<HashList, HashList>(
				new concurrent.collision.detection.SplitHashArray(splitParts),
				new ExecuteContainsCheck(),
				new MergeCollisions());
		Stream<HashList, HashList> stream = Main.skandium.newStream(skeleton);
		
		long init = System.currentTimeMillis();
		Future<HashList> future = stream.input(list);
		HashList result = future.get();
		long time = System.currentTimeMillis() - init;
		System.out.println("Find Collisions: " + (time) + "[ms]");
		
		return result;
	}
	
	public static void fillTrieWithHashes(HashList list, int splitParts) throws Exception {
		Skeleton<HashList, Object> skeleton = new Map<HashList, Object>(
				new SplitHashArray(splitParts),
				new ExecuteInsertion(),
				new MergeNothing());
		Stream<HashList, Object> stream = Main.skandium.newStream(skeleton);
		
		long init = System.currentTimeMillis();
		Future<Object> future = stream.input(list);
		future.get();
		long time = System.currentTimeMillis() - init;
		System.out.println("Adding Hashes to Trie:  " + (time) + "[ms]");
	}

	public static void main(String args[]) throws Exception {
		if (args.length > 0 && args.length != 5) {
			System.out.println("Not enough arguments!");
			System.out.println("Start without arguments for default behaviour or start the program as follows:");
			System.out.println("java -jar file.jar NumberOfProcessors NumberOfSHABytes StartBits NumberOfSplitParts ShowResult");
			System.out.println("");
			System.out.println("NumberOfSHABytes defines how many bytes of the hash are used (beginning from the left).");
			System.out.println();
			System.out.println("StartBits is used to try the attack with different sentences, using the same templates etc.");
			System.out.println("StartBits should be set to 0 and incremented by 1 if the attack failed, to try again.");
			System.out.println();
			System.out.println("NumberOfSplitParts defines in how many subproblems the problem will be divided.");
			System.out.println("NumberOfSplitParts should be higher or equal to the number of processors. Defines granularity.");
			System.out.println();
			System.out.println("ShowResult can be set to 0 (false) or 1 (true) to define if the matching sentences should be shown.");
			System.out.println();
			System.out.println("Attacks at hashes larger than 3 bytes consumes a lot of memory, thus add following argument to Java:");
			System.out.println("-Xms2G -Xmx12G (sets the heap size at the beginning and the maximum heap size)");
			System.exit(1);
		}
		
		int numBytes;
		int startBits;
		int splitParts;
		boolean showResult;
		if (args.length == 5) {
			Main.skandium = new Skandium(Integer.parseInt(args[0]));
			numBytes = Integer.parseInt(args[1]);
			startBits = Integer.parseInt(args[2]);
			splitParts = Integer.parseInt(args[3]);
			showResult = Integer.parseInt(args[4]) != 0;
		} else {
			Main.skandium = new Skandium();
			numBytes = 2;
			startBits = 0;
			splitParts = 8;
			showResult = true;
		}
		
		// numBytes is number of bytes of SHA-hashes, we need half as many *bytes*
		// for the birthday attack, i.e. 4 times as many *bits*
		Schema s1 = new Schema(k, numBytes * 4, startBits);
		Schema s2 = new Schema(k_, numBytes * 4, startBits);
		
		HashList l1 = Main.hashFromSchema(s1, splitParts, numBytes);
		HashList l2 = Main.hashFromSchema(s2, splitParts, numBytes);
		Main.fillTrieWithHashes(l1, splitParts);
		HashList result = Main.findCollisions(l2, splitParts);
		System.out.println("Colliding results: " + result.size());
		
		if (result.size() > 0 && showResult) {
			System.out.println();
			System.out.println("Sentence 1 with bits and hash:");
			System.out.println(s1.getSentence(result.get(0).getBitmap()));
			System.out.println(result.get(0).getBitmap());
			System.out.println(Arrays.toString(result.get(0).getHash()));
			
			System.out.println("\nSentence 2 with bits and hash:");
			System.out.println(s2.getSentence(Main.hashmap.get(Main.byteToString(result.get(0).getHash())).getBitmap()));
			System.out.println(Main.hashmap.get(Main.byteToString(result.get(0).getHash())).getBitmap());
			System.out.println(Arrays.toString(result.get(0).getHash()));
		}
		
		System.exit(0);
	}
}
