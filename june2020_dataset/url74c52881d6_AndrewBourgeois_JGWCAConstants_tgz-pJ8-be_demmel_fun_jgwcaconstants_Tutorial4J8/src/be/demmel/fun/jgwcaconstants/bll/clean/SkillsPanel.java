package be.demmel.fun.jgwcaconstants.bll.clean;

import be.demmel.fun.jgwcaconstants.bll.Attribute;
import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;
import be.demmel.fun.jgwcaconstants.bll.Profession;
import java.io.IOException;

public class SkillsPanel {
    private final GWCAOperations gwcaOperations;
    
    public SkillsPanel(GWCAOperations gwcaOperations) {
        this.gwcaOperations = gwcaOperations;
    }
    
    /**
     *  Remember to use CA_ResetAttributes before starting to set attributes in scripts!
     * @param attribute
     * @param value
     * @throws IOException 
     */
    public void setAttribute(Attribute attribute, int value) throws IOException {
        this.gwcaOperations.setAttribute(attribute, value);
    }
    
    /**
     * Resets the saved attributes, does NOT set your attributes to 0! To be used before a series of CA_SetAttribute.
     * @throws IOException 
     */
    public void resetAttributes() throws IOException {
        this.gwcaOperations.resetAttributes();
    }
    
    /**
     * @param profession
     * @throws IOException 
     */
    public void changeSecondProfession(Profession profession) throws IOException {
       this.gwcaOperations.changeSecondProfession(profession);
    }
}
