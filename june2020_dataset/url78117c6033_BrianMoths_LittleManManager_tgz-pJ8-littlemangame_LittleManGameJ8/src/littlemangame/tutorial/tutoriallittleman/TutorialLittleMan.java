/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.tutorial.tutoriallittleman;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import littlemangame.littleman.GenericLittleMan;
import littlemangame.littleman.PositionGetterAdapter;
import littlemangame.littleman.littlemanutilities.littlemandata.LittleManWordContainer;
import littlemangame.tutorial.tutorialoffice.TutorialOffice;
import littlemangame.word.BinaryWordOperation;
import littlemangame.word.UnaryWordOperation;

/**
 *
 * @author brian
 */
public class TutorialLittleMan extends GenericLittleMan<TutorialLittleManData> {

    static private void doActions(Collection<? extends ActionListener> actionListeners) {
        Iterable<? extends ActionListener> actionListenersCopy = new ArrayList<>(actionListeners);
        for (ActionListener actionListener : actionListenersCopy) {
            actionListener.actionPerformed(null);
        }
    }

    private final List<ActionListener> dataFromInputPanelActionListeners = new ArrayList<>();
    private final List<ActionListener> printUnsignedToOutputPanelActionListeners = new ArrayList<>();
    private final List<ActionListener> unaryOperationActionListeners = new ArrayList<>();
    private final List<ActionListener> binaryOperationActionListeners = new ArrayList<>();
    private final List<ActionListener> memorizePageActionListeners = new ArrayList<>();
    private final List<ActionListener> memorizeDataActionListeners = new ArrayList<>();
    private final List<ActionListener> haltActionListeners = new ArrayList<>();

    public TutorialLittleMan(TutorialOffice computer) {
        this(computer, new PositionGetterAdapter());
    }

    private TutorialLittleMan(TutorialOffice computer, PositionGetterAdapter positionGetterAdapter) {
        super(new TutorialLittleManData(computer, positionGetterAdapter), positionGetterAdapter);
    }

    public void setIsWorksheetArrowShown(boolean isArrowShown) {
        getLittleManData().setIsWorksheetArrowShown(isArrowShown);
    }

    public void setIsNotebookArrowShown(boolean isArrowShown) {
        getLittleManData().setIsNotebookArrowShown(isArrowShown);
    }

    public void setIsNotebookPageSheetArrowShown(boolean isArrowShown) {
        getLittleManData().setIsNotebookPageSheetArrowShown(isArrowShown);
    }

    public void setIsInputPanelArrowShown(boolean isArrowShown) {
        getLittleManData().setIsInputPanelArrowShown(isArrowShown);
    }

    public void setIsOutputPanelArrowShown(boolean isArrowShown) {
        getLittleManData().setIsOutputPanelArrowShown(isArrowShown);
    }

    public boolean addUnaryOperationActionListener(ActionListener e) {
        return unaryOperationActionListeners.add(e);
    }

    public void removeUnaryOperationActionListener(ActionListener e) {
        unaryOperationActionListeners.remove(e);
    }

    public boolean addBinaryOperationActionListener(ActionListener e) {
        return binaryOperationActionListeners.add(e);
    }

    public void removeBinaryOperationActionListener(ActionListener e) {
        binaryOperationActionListeners.remove(e);
    }

    public boolean addDataFromInputPanelActionListener(ActionListener e) {
        return dataFromInputPanelActionListeners.add(e);
    }

    public void removeDataFromInputPanelActionListener(ActionListener e) {
        dataFromInputPanelActionListeners.remove(e);
    }

    public boolean addMemorizeDataActionListener(ActionListener e) {
        return memorizeDataActionListeners.add(e);
    }

    public boolean removeMemorizeDataActionListener(ActionListener o) {
        return memorizeDataActionListeners.remove(o);
    }

    public boolean addMemorizePageActionListener(ActionListener e) {
        return memorizePageActionListeners.add(e);
    }

    public boolean removeMemorizePageActionListener(ActionListener o) {
        return memorizePageActionListeners.remove(o);
    }

    public boolean addPrintUnsignedToOutputPanelActionListener(ActionListener e) {
        return printUnsignedToOutputPanelActionListeners.add(e);
    }

    public boolean removePrintUnsignedToOutputPanelActionListener(ActionListener o) {
        return printUnsignedToOutputPanelActionListeners.remove(o);
    }

    public boolean addHaltActionListener(ActionListener e) {
        return haltActionListeners.add(e);
    }

    public boolean removeHaltActionListener(ActionListener o) {
        return haltActionListeners.remove(o);
    }

    @Override
    public boolean getDataFromInputPanel() {
        final boolean wasSuccessful = super.getDataFromInputPanel(); //To change body of generated methods, choose Tools | Templates.
        if (wasSuccessful) {
            doActions(dataFromInputPanelActionListeners);
        }
        return wasSuccessful; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean printUnsignedToOutputPanel() {
        final boolean wasSuccessful = super.printUnsignedToOutputPanel(); //To change body of generated methods, choose Tools | Templates.
        if (wasSuccessful) {
            doActions(printUnsignedToOutputPanelActionListeners);
        }
        return wasSuccessful; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean doUnaryOperationOnContainer(LittleManWordContainer littleManWordContainer, UnaryWordOperation unaryWordOperation) {
        final boolean wasSuccessful = super.doUnaryOperationOnContainer(littleManWordContainer, unaryWordOperation); //To change body of generated methods, choose Tools | Templates.
        if (wasSuccessful) {
            doActions(unaryOperationActionListeners);
        }
        return wasSuccessful; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean doBinaryOperationOnContainer(LittleManWordContainer littleManWordContainer, BinaryWordOperation binaryWordOperation) {
        final boolean wasSuccessful = super.doBinaryOperationOnContainer(littleManWordContainer, binaryWordOperation); //To change body of generated methods, choose Tools | Templates.
        if (wasSuccessful) {
            doActions(binaryOperationActionListeners);
        }
        return wasSuccessful; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean memorizePageNumberAtContainer(LittleManWordContainer littleManWordContainer) {
        final boolean wasSuccessful = super.memorizePageNumberAtContainer(littleManWordContainer); //To change body of generated methods, choose Tools | Templates.
        if (wasSuccessful) {
            doActions(memorizePageActionListeners);
        }
        return wasSuccessful; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean memorizeDataAtContainer(LittleManWordContainer littleManWordContainer) {
        final boolean wasSuccessful = super.memorizeDataAtContainer(littleManWordContainer); //To change body of generated methods, choose Tools | Templates.
        if (wasSuccessful) {
            doActions(memorizeDataActionListeners);
        }
        return wasSuccessful; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void halt() {
        super.halt(); //To change body of generated methods, choose Tools | Templates.
        doActions(haltActionListeners);
    }

}
