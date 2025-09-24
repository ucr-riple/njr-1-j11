package attribute;

import java.util.ArrayList;
import java.util.Stack;

import player.Player;
import units.Unit;
import achievement.AchievementKill;
import achiever.Achiever;

public class AttributeKills extends Attribute<ArrayList<Unit>,Unit> {
    
    private Stack<Unit> recentKilledUnits;
    
    public AttributeKills(Achiever achiever) {
        super(achiever);
        super.setData(new ArrayList<Unit>());
        recentKilledUnits = new Stack<Unit>();
        super.attachAchievement(AchievementKill.getAchievement());
    }
    
    @Override
    public String name() {
        return "AchieverKills";
    }
    
    public int getKillCount() {
        return super.getData().size();
    }
    
  
    public Stack<Unit> getRecentKilledUnits() {
        return recentKilledUnits;
    }

    @Override
    public void augmentDataTemplate(Unit data) {
        super.getData().add(data);
        recentKilledUnits.add(data);        
    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub
        
    }

}
