package attribute;

import java.util.ArrayList;

import achiever.Achiever;

public class AttributeAchievementList extends Attribute<ArrayList<String>,String> {
    
    public AttributeAchievementList(Achiever achiever) {
        super(achiever);
        super.setData(new ArrayList<String>());
    }

    @Override
    public String name() {
        return "AchievementList";
    }

    @Override
    public void augmentDataTemplate(String data) {
        super.getData().add(data);
    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub
        
    }

}
