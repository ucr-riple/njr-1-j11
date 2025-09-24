package Game;


/** unlocked in the quest for knowledge */
public class Discoveries {
	
	private static final int STAGE1 = 5;
	private static final int STAGE2 = STAGE1 + 5;
	private static final int STAGE3 = STAGE2 + 10;
	private static final int STAGE4 = STAGE3 + 20;
	private static final int STAGE5 = STAGE4 + 20;
	private static final int STAGE6 = STAGE5 + 20;
	private static final int STAGE7 = STAGE6 + 20;
	private static final int STAGE8 = STAGE7 + 20;
	private static final int STAGE9 = STAGE8 + 20;
	private static final int STAGE10 = STAGE9 + 20;
	private static final int STAGE11 = STAGE10 + 20;
	private static final int STAGE12 = STAGE11 + 20;
	private static final int STAGE13 = STAGE12 + 20;
	private static final int STAGE14 = STAGE13 + 20;
	
	private void display(String S) {
		System.out.println(S); //goblog
	}
	
	public void discover(int progress) {
		switch (progress) {
		case STAGE1 :	display("You learn about nature and the ancestors."); break;
		case STAGE2 :	display("You learn the layout of the heavens."); break;
		case STAGE3 :	display("You learn abstract words."); break;
		case STAGE4 :	display("You learn basic arithmetic."); break;
		case STAGE5 :	display("You learn writing."); break;
		case STAGE6 :	display("You find evidence of foreign people from mysterious lands."); break;
		case STAGE7 :	display("You learn geometry."); break;
		case STAGE8 :	display("You theorize about the nature of consciousness."); break;
		case STAGE9 :	display("You learn trigonometry."); break;
		case STAGE10 :	display("You discover gravity."); break;
		case STAGE11 :	display("You formulize some basic properties of the universe."); break;
		case STAGE12 :	display("You discover an ancient undecypherable tome."); break;
		case STAGE13 :	display("You hypothesize about the origins of creation."); break;
		case STAGE14 :	display("You attain some gist of basic genetics."); break;
		//at some point some kind of frontier encounter is required to move on
		
		}
	}
	
	

}
