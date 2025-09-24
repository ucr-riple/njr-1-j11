/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame;

import ListenerInputHandler.AbstractInputHandlerClient;
import RealTimeGame.AbstractRealTimeGame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import littlemangame.littlemancommands.LittleManCommander;
import littlemangame.notebookdeveloper.GenericNotebookDeveloper;
import littlemangame.notebookdeveloper.NotebookDeveloper;
import littlemangame.notebookdeveloper.gui.BaseLittleManGui;
import littlemangame.notebookdeveloper.speedcontroller.SpeedController;
import littlemangame.tutorial.TutorialDriver;
import littlemangame.tutorial.gui.TutorialLittleManGui;
import littlemangame.tutorial.tutorialnotebookdeveloper.TutorialNotebookDeveloper;

/**
 *
 * @author brian
 */
public class LittleManGame extends AbstractRealTimeGame<GenericLittleManGui<?, ?, ?>> {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LittleManGame littleMan = new LittleManGame();
        littleMan.startGameLoopThread();
    }

    private static TutorialLittleManGui makeTutorialGamePanel() {
        return new TutorialLittleManGui();
    }

    private static BaseLittleManGui makeGamePanel() {
        return new BaseLittleManGui();
    }

    private final TutorialLittleManGui tutorialLittleManGui;
    private final BaseLittleManGui baseLittleManGui;
    private final TutorialDriver tutorialDriver;
    private final TutorialNotebookDeveloper tutorialNotebookDeveloper;
    private GenericNotebookDeveloper<?> notebookDeveloper;
    private final SpeedController speedController;

    public LittleManGame() {
        super(new AbstractInputHandlerClient(), makeTutorialGamePanel());
        speedController = new SpeedController();
        tutorialLittleManGui = (TutorialLittleManGui) getGameGui();
        tutorialNotebookDeveloper = new TutorialNotebookDeveloper(tutorialLittleManGui.getNotebookDeveloperGui().getOfficeView());
        notebookDeveloper = tutorialNotebookDeveloper;
        tutorialDriver = new TutorialDriver(tutorialNotebookDeveloper, tutorialLittleManGui, speedController, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                endTutorial();
            }

        });

        baseLittleManGui = makeGamePanel();
        init();
    }

    private void init() {
        tutorialLittleManGui.setNotebookDeveloper(tutorialNotebookDeveloper);
        tutorialLittleManGui.registerSpeedController(speedController);
        tutorialDriver.startTutorial();
    }

    private void endTutorial() {
        final NotebookDeveloper baseNotebookDeveloper = new NotebookDeveloper(new LittleManCommander(baseLittleManGui.getNotebookDeveloperGui().getOfficeView()));
        notebookDeveloper = baseNotebookDeveloper;
        baseLittleManGui.setNotebookDeveloper(baseNotebookDeveloper);
        baseLittleManGui.registerSpeedController(speedController);
        tutorialLittleManGui.setVisible(false);
        speedController.pause();
        setGameGui(baseLittleManGui);
    }

    @Override
    protected void doLogic() {
        notebookDeveloper.doFrames(speedController.getCurrentSpeed());
    }

}
