/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ee.ea.pkg1.types;

/**
 *
 * @author Chins
 */
public abstract class Type {
    protected int nothing; 
    protected double typeModifier;
    protected String name;
    protected Type [] resistantTypes ;
    protected Type [] effectiveTypes ;
    protected Type [] immuneTypes ;
    
    public Type()
    {
        typeModifier = 1.0;
    }
    public void superEffective(Type primary)
    {
        int counter = 0;
        for (int i =0; i< effectiveTypes.length; i++)
        {
            if (effectiveTypes[i].equalsTo(primary.showType()))
            {
                counter +=1;
            }
        }
        typeModifier = typeModifier + (2* counter);
    }
    
    public void superEffective (Type primary, Type secondary)
    {
        int counter =0;
        for (int i =0; i<effectiveTypes.length; i++)
        {
            if(effectiveTypes[i].equalsTo(primary.showType()) || effectiveTypes[i].equalsTo(secondary.showType()))
            {
                counter ++;
            }
        }
        if (counter > 0)
        {
            typeModifier = (double) (2* counter);
        }
        
        
    }
    
   
    
    public void immune (Type primary, Type secondary)
    {
        
        if (immuneTypes.length ==0)
        {
            return;
        }
        for (int i =0; i<resistantTypes.length; i++)
        {
            if(immuneTypes[i].equalsTo(primary.showType()) || immuneTypes[i].equalsTo(secondary.showType()))
            {
                typeModifier = 0.0;
            }
        }
     
        
        
    }
    
    public void resisted(Type primary, Type secondary)
    {
        int counter =0;
        for (int i =0; i<resistantTypes.length; i++)
        {
            if(resistantTypes[i].equalsTo(primary.showType()) || resistantTypes[i].equalsTo(secondary.showType()))
            {
                counter ++;
            }
        }
        if (counter > 0)
        {
            typeModifier = (double) Math.pow(.5, (double) counter);
        }
        
        
    }
    
    //THESE FOLLOWING METHODS ARE DONE TO RETURN THE ACTUAL COUNTER INSTEAD OF MODIFYING TYPEMODIFIER
    
    public int superEffectiveCounter(Type primary) {
        int counter = 0;
        if (effectiveTypes.length == 0 || effectiveTypes == null) {
            return 0;
        }
        for (int i = 0; i < effectiveTypes.length; i++) {
            if (effectiveTypes[i].equalsTo(primary.showType())) {
                counter++;
            }
        }

        return counter;



    }
    
    
    
    public int superEffectiveCounter(Type primary, Type secondary) {
        int counter = 0;
        if (effectiveTypes.length ==0 || effectiveTypes == null)
        {
            return 0;
        }
        for (int i = 0; i < effectiveTypes.length; i++) {
            if (effectiveTypes[i].equalsTo(primary.showType()) || effectiveTypes[i].equalsTo(secondary.showType())) {
                counter++;
            }
        }
        
            return  counter;
        


    }
    
    public int immuneCounter(Type primary, Type secondary) {
        int counter = 0;
        if (immuneTypes == null || immuneTypes.length ==0) {
            return 0;
        }
        
        for (int i = 0; i < immuneTypes.length; i++) {
            if (immuneTypes[i].equalsTo(primary.showType()) || immuneTypes[i].equalsTo(secondary.showType())) {
                counter ++;
            }
        }
        
        return counter;


    }
    
    public int resistedCounter(Type primary, Type secondary) {
        int counter = 0;
        if (resistantTypes == null || resistantTypes.length ==0)
        {
            return 0;
        }
        for (int i = 0; i < resistantTypes.length; i++) {
            if (resistantTypes[i].equalsTo(primary.showType()) || resistantTypes[i].equalsTo(secondary.showType())) {
                counter++;
            }
        }
       
            return counter; 
        


    }
    
    public String showType()
    {
        return name;
    }
        
    public boolean equalsTo(String otherName)
    {
        return name.equals(otherName);
    }
    
    public double getTypeModifier()
    {
        return typeModifier;
    }
}
