package Avatar;

import Game.AGPmain;
import Sentiens.Clan;
import Shirage.Shire;


@SuppressWarnings("serial")
public class InputConsole extends AbstractInputConsole {

	private static final String CMD_FIND_CLAN = "find clan ";
	private static final String CMD_FIND_SHIRE = "find shire ";
	private static final String CMD_SHOW_RESOURCE = "show resource ";
	private static final String CMD_DEBUG_MARKETS = "debug market ";
	private static final String CMD_HIGHLIGHT_GOOD = "highlight good ";
	private static final String CMD_HIGHLIGHT_ASSETS = "highlight wealth ";
	private static final String CMD_HIGHLIGHT = "highlight ";
	
	@Override
	public void doCommand(String command) {
		
		System.out.println(command);
		
		boolean didSomething =
				(doForCommand(command, CMD_FIND_CLAN, new InputThing() {public void doit(String input) {
					Clan c = AGPmain.TheRealm.getClan(input);
					System.out.println(c);
					if (c != null) AGPmain.mainGUI.GM.loadClan(c);
				}})) ||
				(doForCommand(command, CMD_FIND_SHIRE, new InputThing() {public void doit(String input) {
					Shire s = AGPmain.TheRealm.getShire(input);
					if (s != null) AGPmain.mainGUI.SM.loadShire(s);
				}})) ||
				(doForCommand(command, CMD_DEBUG_MARKETS, new InputThing() {public void doit(String input) {
					Shire s = AGPmain.TheRealm.getShire(input);
					if (s != null) for (int g = 1; g < Defs.Misc.numGoods; g++) {
						System.out.println(s.getMarket(g).getReport());
					}
				}})) ||
				(doForCommand(command, CMD_SHOW_RESOURCE, new InputThing() {public void doit(String input) {
				// TODO highlight resource
				}})) ||
				(doForCommand(command, CMD_HIGHLIGHT_GOOD, new InputThing() {public void doit(String input) {
					input = input.toUpperCase();
					ShireStatsCalcer.calcMarket(input);
					AGPmain.mainGUI.MD.setHighlightStat(ShireStatsCalcer.LAST_PX);
					AGPmain.mainGUI.MD.grayEverything();
				}})) ||
				(doForCommand(command, CMD_HIGHLIGHT_ASSETS, new InputThing() {public void doit(String input) {
					input = input.toUpperCase();
					ShireStatsCalcer.calcWealth(input);
					AGPmain.mainGUI.MD.setHighlightStat(ShireStatsCalcer.NUM_ASSETS);
					AGPmain.mainGUI.MD.grayEverything();
				}})) ||
				(doForCommand(command, CMD_HIGHLIGHT, new InputThing() {public void doit(String input) {
					input = input.toUpperCase();
					
					if (ShireStatsCalcer.PRODUCTIVITY.equals(input)) {ShireStatsCalcer.calcProductivity();}
					if (ShireStatsCalcer.POPULATION.equals(input)) {ShireStatsCalcer.calcPopulation();}
					
					AGPmain.mainGUI.MD.setHighlightStat(input);
					AGPmain.mainGUI.MD.grayEverything();
				}}));
			
		if (didSomething) return;
		
		setText("*NO SUCH COMMAND*");
	}
	
	private boolean doForCommand(String cmd, String prefix, InputThing inputThing) {
		if (cmd.startsWith(prefix)) {
			String suffix = cmd.substring(prefix.length());
			inputThing.doit(suffix);
			return true;
		}
		return false;
	}
	
	interface InputThing {
		void doit(String input);
	}
}
