package be.demmel.fun.jgwcaconstants.bll.clean;

import be.demmel.fun.jgwcaconstants.bll.GWCAOperations;
import be.demmel.fun.jgwcaconstants.bll.SkillBarSkillSlot;

public class SkillBar {

    private final SkillSlot firstSkillSlot, secondSkillSlot, thirdSkillSlot, fourthSkillSlot, fifthSkillSlot, sixthSkillSlot, seventhSkillSlot, eigthSkillSlot;
    private final GWCAOperations gwcaOperations;

    public SkillBar(GWCAOperations gwcaOperations) {
        this.gwcaOperations = gwcaOperations;
        firstSkillSlot = new SkillSlot(gwcaOperations, SkillBarSkillSlot.ONE);
        secondSkillSlot = new SkillSlot(gwcaOperations, SkillBarSkillSlot.TWO);
        thirdSkillSlot = new SkillSlot(gwcaOperations, SkillBarSkillSlot.THREE);
        fourthSkillSlot = new SkillSlot(gwcaOperations, SkillBarSkillSlot.FOUR);
        fifthSkillSlot = new SkillSlot(gwcaOperations, SkillBarSkillSlot.FIVE);
        sixthSkillSlot = new SkillSlot(gwcaOperations, SkillBarSkillSlot.SIX);
        seventhSkillSlot = new SkillSlot(gwcaOperations, SkillBarSkillSlot.SEVEN);
        eigthSkillSlot = new SkillSlot(gwcaOperations, SkillBarSkillSlot.EIGHT);
    }

    public SkillSlot getEigthSkillSlot() {
        return eigthSkillSlot;
    }

    public SkillSlot getFifthSkillSlot() {
        return fifthSkillSlot;
    }

    public SkillSlot getFirstSkillSlot() {
        return firstSkillSlot;
    }

    public SkillSlot getFourthSkillSlot() {
        return fourthSkillSlot;
    }

    public SkillSlot getSecondSkillSlot() {
        return secondSkillSlot;
    }

    public SkillSlot getSeventhSkillSlot() {
        return seventhSkillSlot;
    }

    public SkillSlot getSixthSkillSlot() {
        return sixthSkillSlot;
    }

    public SkillSlot getThirdSkillSlot() {
        return thirdSkillSlot;
    }
}
