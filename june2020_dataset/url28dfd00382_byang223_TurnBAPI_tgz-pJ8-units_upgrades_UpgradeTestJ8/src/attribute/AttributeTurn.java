package attribute;

import java.util.HashMap;

import achiever.Achiever;

public class AttributeTurn extends Attribute<Integer,Integer> {

    
    public AttributeTurn(Achiever achiever) {
        super(achiever);
        super.setData(1);
    }
    

    @Override
    public void refresh() {

    }

    @Override
    public String name() {
        return "Turn";
    }


    @Override
    public void augmentDataTemplate(Integer dataElement) {
        super.setData(super.getData()+dataElement);
    }

}
