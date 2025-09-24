package attribute;


import java.util.ArrayList;

import units.Unit;
/**
 * Production Attribute.  Currently contains production Range, but will eventually interface with player resources.
 * @author Matthew
 *
 */
public class AttributeProduction extends Attribute<Integer,Integer>{
    
    
    protected int productionRange;
   
    public AttributeProduction(Unit owner) {
        this(owner,0);
    }
    
    public AttributeProduction(Unit owner,int produceRange){
        super(owner);
        productionRange=produceRange;
    }
    
    
    public int getProductionRange(){
        return productionRange;
    }

    @Override
    public void refresh() {
        
    }

    @Override
    public String name() {
        return "Produce";
    }

    @Override
    public void augmentDataTemplate(Integer dataElement) {
        
    }

}