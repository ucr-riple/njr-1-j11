/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.tutorial;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import littlemangame.notebookdeveloper.speedcontroller.SpeedController;
import littlemangame.tutorial.gui.SubmissionControllerTutorialGui;
import littlemangame.tutorial.gui.TutorialLittleManGui;
import littlemangame.tutorial.gui.TutorialNotebookEditorPanel;
import littlemangame.tutorial.tutorialnotebookdeveloper.TutorialNotebookDeveloper;
import littlemangame.word.Word;

/**
 *
 *
 * @author brian
 */
public class TutorialDriver {
    
    static private final String INTRO_TEXT = "Welcome to your new job. We have recently hired a little man because he is very good at the work we need him to do, but we have quickly found that he is difficult to communicate with. He speaks a different language than we do. The words of his language are numbers from 0 to 99. That is where you come in. Your job will be to take our description of what we want the little man to do, and translate it into his strange language. You will write the commands of his language in a notebook. After you have finished the notebook, we will review it to confirm that the little man does what we wanted.\\n\\n You are now looking at the little man's office. Let me explain what is all in the little man's office.";
    static private final String NOTEBOOK_DESCRIPTION = "This is the little man's notebook. It has 100 pages, labelled 00 to 99 so that each word can be used to refer to a page. Each page can only fit one word on it. You will write all the instructions for the little man on this notebook. Once the little man starts his work for the day, he will begin reading the notebook from page 00 and do the instructions written in it. Another use for the notebook is as a place for the little man to write down words he needs to remember. Since the little man has a very bad memory, he can't just keep everything in his head.";
    static private final String NOTEBOOK_PAGE_SHEET_DESCRIPTION = "This is the notebook page sheet. This is where the little man keeps track of what notebook page he is on. It starts out at zero because that is the first page.";
    static private final String WORKSHEET_DESCRIPTION = "This is the worksheet. This is where the little man does all his calculations such as adding numbers.";
    static private final String OUTPUT_PANEL_DESCRIPTION = "This is the output panel. The little man will write his results here.";
    static private final String INPUT_PANEL_DESCRIPTION = "This is the input panel. Here the little man can ask you to tell him a word in the middle of his work. This would be useful, for example, if you wanted the little man to add two numbers, but you wanted him to ask you which two numbers to add.";
    static private final String CLICK_EDIT_NOTEBOOK_TEXT = "Let's make a notebook for a simple problem. The problem is to add ten to a number. First we will have the little man ask for a number, then he will output that number, then he will output ten, then he will output the sum. To begin editing the notebook, click on the edit notebook button.";
    static private final String PROBLEM_DESCRIPTION_TEXT = "This is the where you will edit the notebook. The text here would usually describe the problem you are trying to solve. To the left, you can pick the word to be written on each page of the notebook. Below is a dictionary that translates a command in our language to the little man's word for the command.\\n\\nRemember that the little man is to ask us for a number, output this number, output ten and then output their sum. So first let's have the little man ask us for a number. This is considered input. Under the input section of the dictionary, we can see the word for input is 25. So set the word on page 00 of the notebook to 25.";
    static private final String OUTPUT_ARGUMENT_TEXT = "We have now instructed the little man to ask for input. Next he is to output the number we gave him. To do this, we just need to write the output instruction on the next page. The output instruction is 20, so set the word on page 01 to 20.";
    static private final String STORE_ARGUMENT_TEXT = "Now we have instructed the little man to output the number we gave it. Next the little man is to output ten. Since the little man can only output the word in his worksheet, we must tell the little man to write 10 in the worksheet. However, if he does that immediately, he will loose track of the word we gave him. So first we must tell him to write the word we gave him in the notebook. \n\nThe instruction for the little man to copy the word on the worksheet to the notebook is 36. This instruction requires additional information. We must say which page of the notebook we want the little man to copy to. We do this by writing the notebook page on the next page after where we wrote 36. When an instruction requires more than a single word to specify it, the additional words are called operands. In this case we had a page number operand, since the operand specified a page number. So set the word on page 02 to 36, and the word on page 03 to 50.";
    static private final String LOAD_IMMEDIATE_TEXT = "Remember the next thing we want the little man to do is to output 10. Since the little man can only output the word on the worksheet, we must have the little man write 10 on the worksheet. The instruction for the little man to write a word on the worksheet is 30. This instruction takes an operand saying which word should be written on the page. This type of operand is called a data operand. This is the only other kind of operand besides a page number operand. So set the word on page 04 to 30 and set the word on page 05 to 10.";
    static private final String OUTPUT_TEN_TEXT = "Now that the little man has written 10 on the worksheet we are ready to use the output instruction. Set the word on page 06 of the notebook to 20.";
    static private final String DO_ADDITION_TEXT = "The last thing the little man must do is to output the sum of ten with the number we gave him. The way we will do it is to add ten to the notebook page where we wrote the number we gave him, then move this result to the worksheet and output it. (We are doing it this way to show how to use an instruction that uses both types of operands. There is a faster way of doing it. See if you can figure out what it is.) \n\nAnyway, the first step is to add ten to the notebook page where we wrote the number we gave him. To do this, we will use the arithmetic instruction 82. This instruction has both a data operand and a page number operand. The data operand says how much to add and the page number operand says what page the number should be added to. We want to add 10 so the data operand will be 10 and we want to add to page 50, since that is the page we wrote our number on, so the address operand will be 50. When an instruction has both kinds of operands, the data operand goes first. Therefore, set the word on page 07 to 82, the word on page 08 to 10, and the word on page 09 to 50.";
    static private final String LOAD_SUM_TEXT = "Now that we have the sum written in the notebook, we must move this sum to the worksheet so that we can output it. The instruction for moving from the notebook to the worksheet is 31. It takes an address operand which in our case is 50. So set the word on page 10 to 31 and the word on page 11 to 50.";
    static private final String OUTPUT_SUM_TEXT = "Now that the word is on the worksheet we may output it with instruction 20. Set the word on page 12 to 20";
    static private final String HALT_TEXT = "At this point the little man is done with his task, but he has no way of knowing it. He will keep reading through the notebook trying to do the instructions written there. Even if he got to the end, he would just start again at the beginning. To tell the little man he is done, there is a special instruction called halt. It is instruction 09. So set the word on page 13 to 09";
    static private final String DONE_EDITING_TEXT = "We are now done writing the notebook. Press the save button to keep this notebook.";
    static private final String START_TEST_TEXT = "Now that we have saved the notebook we are ready to test the little man to make sure he does what we expect. Click the test notebook button.";
    static private final String BEGINNING_OF_TEST_TEXT = "We are now looking at the little man's office again. The little man is about to carry out the instructions we have written in the notebook. I will guide you through the little man's actions. His first step will be to go to the notebook page sheet and read what page his next instruction is written on.";
    static private final String READ_INSTRUCTION_TEXT = "The little man has now read that the next instruction is on page 00. He will now remember this page number and go to the notebook to read the word that is written on that page.";
    static private final String INCREMENT_NOTEBOOK_PAGE_SHEET_TEXT = "The little man now sees that his first instruction is 25, which means that he should get input. Since the little man has read this instruction he will now go back to the notebook page sheet and increase it by one so that it refers to a new instruction.";
    static private final String GO_TO_INPUT_PANEL_TEXT = "Now that the little man has updated the notebook page sheet, he will start to carry out his instruction. The first step is to go to the input panel and ask for input. When the little man gets to the input panel, select a number and hit ok.";
    static private final String DO_INPUT_TEXT = "Now that you have given the little man a word, and he will write this word on his worksheet.";
    static private final String READ_SECOND_INSTRUCTION_TEXT = "The little man has now completed his first instruction. He will now work on doing the second instruction. As before he will read the page of the instruction from the notebook page sheet, read the instruction, and then increase the word on the notebook page sheet.";
    static private final String FIRST_OUTPUT_TEXT = "The little man read 20, the output instruction, from the notebook. He will read the word written on the worksheet and write it on the output panel.";
    static private final String READ_THIRD_INSTRUCTION_TEXT = "Now the little man has completed the second instruction. He will read the third instruction now and increment the notebook page sheet. ";
    static private final String READ_ADDRESS_OPERAND_TEXT = "The third instruction was to move the word on the worksheet into the notebook. This instruction has an operand, which is the page that the word on the worksheet should be moved to. The little man will now read the next word in the notebook, which says what page the little man must write to.";
    static private final String DO_FIRST_MOVE_TEXT = "The little man has now read that he is supposed to write to page 50. The little man will now carry out the instruction. He will go to the worksheet, memorize the word written there, go to page 50 of the notebook, and copy down the word he memorized.";
    static private final String READ_FOURTH_INSTRUCTION_TEXT = "Next the little will read his next instruction, which involves writing to the worksheet.";
    static private final String READ_DATA_OPERAND_TEXT = "The little man has read the instruction, but this instruction has a data operand, which the little man will now read off of the next page in the notebook. ";
    static private final String DO_SECOND_MOVE_TEXT = "The little man has memorized the data operand, which is 10. The little man will now write this word to the worksheet.";
    static private final String DO_SECOND_OUTPUT_TEXT = "The next instruction is an output instruction. The little man will output what is written on the worksheet exactly as he did before.";
    static private final String READ_ADD_INSTRUCTION_TEXT = "Now the little man will read the next instruction, which is an add instruction.";
    static private final String GET_ADD_DATA_OPERAND_TEXT = "The little man has read the add instruction, which has two operands. Now the little man must read the two operands from the notebook. These operands are on the next two pages, with the data operand first. The data operand specifies what number to add.";
    static private final String GET_ADD_PAGE_NUMBER_OPERAND_TEXT = "The little man has read the data operand, 10. Now he will read the page number operand to tell him what page he will add 10 to.";
    static private final String DO_ADDITION_TEST_TEXT = "The little man has read that the data operand is 10 and the page number operand is 50. So the little man will ad 10 to the number on page 50 of the notebook.";
    static private final String FINAL_MOVE_TEXT = "Now the little man will read the next instruction, which has a address operand. The instruction is to copy the word on page 50 of the notebook to the worksheet.";
    static private final String THIRD_OUTPUT_TEXT = "Now the little man will do the next instruction, which is another output instruction.";
    static private final String HALT_TEST_TEXT = "Finally the little man must do the last instruction, which is simply to halt.";
    static private final String FINISH_TEST_TEXT = "The little man has done everything we wanted him to. We will now end the test.";
    static private final String SUBMIT_TEXT = "Now that we have tested the notebook and found that it works correctly, you are ready to submit the notebook. Hit the submit button.";
    static private final String SUBMISSION_RESULT_TEXT = "Congratulations! the notebook works as it should. You have solved the problem and complete the tutorial. Click OK to begin the game. You will be able to view your current job by pressing the edit notebook button.";

    //<editor-fold defaultstate="collapsed" desc="PageEditorItemListener">
    private class PageEditorItemListener implements ItemListener {
        
        private final int pageNumber, targetWordValue;
        private final String newProblemDescription;
        private final boolean isProblemDescriptionChanged;
        private final ItemListener nextPageEditorItemListener;
        private final boolean isNextPageEditorItemListenerUsed;
        
        PageEditorItemListener(int pageNumber, int targetWordValue) {
            this.pageNumber = pageNumber;
            this.targetWordValue = targetWordValue;
            isNextPageEditorItemListenerUsed = false;
            isProblemDescriptionChanged = false;
            newProblemDescription = null;
            nextPageEditorItemListener = null;
        }
        
        PageEditorItemListener(int pageNumber, int targetWordValue, ItemListener nextPageEditorItemListener) {
            this.pageNumber = pageNumber;
            this.targetWordValue = targetWordValue;
            isNextPageEditorItemListenerUsed = true;
            this.nextPageEditorItemListener = nextPageEditorItemListener;
            isProblemDescriptionChanged = false;
            newProblemDescription = null;
        }
        
        PageEditorItemListener(int pageNumber, int targetWordValue, String newProblemDescription) {
            this.pageNumber = pageNumber;
            this.targetWordValue = targetWordValue;
            this.isProblemDescriptionChanged = true;
            this.newProblemDescription = newProblemDescription;
            isNextPageEditorItemListenerUsed = false;
            this.nextPageEditorItemListener = null;
        }
        
        PageEditorItemListener(int pageNumber, int targetWordValue, String newProblemDescription, ItemListener nextPageEditorItemListener) {
            this.pageNumber = pageNumber;
            this.targetWordValue = targetWordValue;
            this.isProblemDescriptionChanged = true;
            this.newProblemDescription = newProblemDescription;
            isNextPageEditorItemListenerUsed = true;
            this.nextPageEditorItemListener = nextPageEditorItemListener;
        }
        
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED && Word.valueOfLastDigitsOfInteger(targetWordValue).equals(itemEvent.getItem())) {
                final TutorialNotebookEditorPanel notebookEditorPanel = tutorialLittleManGui.getNotebookEditorPanel();
                if (isProblemDescriptionChanged) {
                    notebookEditorPanel.setProblemDescription(newProblemDescription);
                }
                notebookEditorPanel.setPageEditorIsEnabled(pageNumber, false);
                notebookEditorPanel.setPageEditorIsEnabled(pageNumber + 1, true);
                notebookEditorPanel.removePageEditorItemListener(pageNumber, this);
                if (isNextPageEditorItemListenerUsed) {
                    notebookEditorPanel.addPageEditorItemListener(pageNumber + 1, nextPageEditorItemListener);
                }
            }
        }
        
    }
    //</editor-fold>

    private final TutorialNotebookDeveloper tutorialNotebookDeveloper;
    private final TutorialLittleManGui tutorialLittleManGui;
    private final SpeedController speedController;
    private final ActionListener endTutorialActionListener;
    private final ActionListener endIntroBeginNotebookNotebookListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            tutorialNotebookDeveloper.setIsNotebookArrowShown(true);
            printDialogue(NOTEBOOK_DESCRIPTION);
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.addDialogueActionListener(endNotebookBeginNotebookPageSheetListener);
            submissionControllerTutorialGui.removeDialogueActionListener(this);
        }
        
    };
    private final ActionListener endNotebookBeginNotebookPageSheetListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            tutorialNotebookDeveloper.setIsNotebookArrowShown(false);
            tutorialNotebookDeveloper.setIsNotebookPageSheetArrowShown(true);
            printDialogue(NOTEBOOK_PAGE_SHEET_DESCRIPTION);
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.addDialogueActionListener(endNotebookPageSheetListenerBeginWorksheet);
            submissionControllerTutorialGui.removeDialogueActionListener(this);
        }
        
    };
    private final ActionListener endNotebookPageSheetListenerBeginWorksheet = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            tutorialNotebookDeveloper.setIsNotebookPageSheetArrowShown(false);
            tutorialNotebookDeveloper.setIsWorksheetArrowShown(true);
            printDialogue(WORKSHEET_DESCRIPTION);
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.addDialogueActionListener(endWorksheetBeginOutputPanel);
            submissionControllerTutorialGui.removeDialogueActionListener(this);
        }
        
    };
    private final ActionListener endWorksheetBeginOutputPanel = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            tutorialNotebookDeveloper.setIsWorksheetArrowShown(false);
            tutorialNotebookDeveloper.setIsOutputPanelArrowShown(true);
            printDialogue(OUTPUT_PANEL_DESCRIPTION);
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.addDialogueActionListener(endOutputPanelBeginInputPanel);
            submissionControllerTutorialGui.removeDialogueActionListener(this);
        }
        
    };
    private final ActionListener endOutputPanelBeginInputPanel = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            tutorialNotebookDeveloper.setIsOutputPanelArrowShown(false);
            tutorialNotebookDeveloper.setIsInputPanelArrowShown(true);
            printDialogue(INPUT_PANEL_DESCRIPTION);
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.addDialogueActionListener(endInputPanel);
            submissionControllerTutorialGui.removeDialogueActionListener(this);
        }
        
    };
    private final ActionListener endInputPanel = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            tutorialNotebookDeveloper.setIsInputPanelArrowShown(false);
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.showSubmissionPanel();
            submissionControllerTutorialGui.setSubmitButtonEnabled(false);
            submissionControllerTutorialGui.setTestButtonEnabled(false);
            submissionControllerTutorialGui.printResultMessage(CLICK_EDIT_NOTEBOOK_TEXT);
            submissionControllerTutorialGui.removeDialogueActionListener(this);
            submissionControllerTutorialGui.setEditMemoryAction(promptEditMemory);
        }
        
    };
    private final ActionListener promptEditMemory = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final TutorialNotebookEditorPanel notebookEditorPanel = tutorialLittleManGui.getNotebookEditorPanel();
            notebookEditorPanel.setProblemDescription(PROBLEM_DESCRIPTION_TEXT);
            for (int page = 1; page < Word.NUM_WORDS; page++) {
                notebookEditorPanel.setPageEditorIsEnabled(page, false);
            }
            notebookEditorPanel.setSaveButtonIsEnabled(false);
            tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui().removeEditMemoryAction(this);
            notebookEditorPanel.addPageEditorItemListener(0, pageZeroItemListener);
        }
        
    };
    private final ItemListener pageThirteenItemListener = new ItemListener() {
        
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED && Word.valueOfLastDigitsOfInteger(9).equals(itemEvent.getItem())) {
                final TutorialNotebookEditorPanel notebookEditorPanel = tutorialLittleManGui.getNotebookEditorPanel();
                notebookEditorPanel.setProblemDescription(DONE_EDITING_TEXT);
                notebookEditorPanel.setPageEditorIsEnabled(13, false);
                notebookEditorPanel.removePageEditorItemListener(13, this);
                notebookEditorPanel.addSaveButtonActionListener(saveNotebookActionListener);
                notebookEditorPanel.setSaveButtonIsEnabled(true);
            }
        }
        
    };

//    private final ItemListener pageThirteenItemListener = new PageEditorItemListener(13, 9, STORE_ARGUMENT_TEXT);
    private final ItemListener pageTwelveItemListener = new PageEditorItemListener(12, 20, HALT_TEXT, pageThirteenItemListener);
    private final ItemListener pageElevenItemListener = new PageEditorItemListener(11, 50, OUTPUT_SUM_TEXT, pageTwelveItemListener);
    private final ItemListener pageTenItemListener = new PageEditorItemListener(10, 31, pageElevenItemListener);
    private final ItemListener pageNineItemListener = new PageEditorItemListener(9, 50, LOAD_SUM_TEXT, pageTenItemListener);
    private final ItemListener pageEightItemListener = new PageEditorItemListener(8, 10, pageNineItemListener);
    private final ItemListener pageSevenItemListener = new PageEditorItemListener(7, 82, pageEightItemListener);
    private final ItemListener pageSixItemListener = new PageEditorItemListener(6, 20, DO_ADDITION_TEXT, pageSevenItemListener);
    private final ItemListener pageFiveItemListener = new PageEditorItemListener(5, 10, OUTPUT_TEN_TEXT, pageSixItemListener);
    private final ItemListener pageFourItemListener = new PageEditorItemListener(4, 30, pageFiveItemListener);
    private final ItemListener pageThreeItemListener = new PageEditorItemListener(3, 50, LOAD_IMMEDIATE_TEXT, pageFourItemListener);
    private final ItemListener pageTwoItemListener = new PageEditorItemListener(2, 36, pageThreeItemListener);
    private final ItemListener pageOneItemListener = new PageEditorItemListener(1, 20, STORE_ARGUMENT_TEXT, pageTwoItemListener);
    private final ItemListener pageZeroItemListener = new PageEditorItemListener(0, 25, OUTPUT_ARGUMENT_TEXT, pageOneItemListener);
    
    private final ActionListener saveNotebookActionListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final TutorialNotebookEditorPanel notebookEditorPanel = tutorialLittleManGui.getNotebookEditorPanel();
            for (int page = 1; page < Word.NUM_WORDS; page++) {
                notebookEditorPanel.setPageEditorIsEnabled(page, true);
            }
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.setEditMemoryEnabled(false);
            submissionControllerTutorialGui.setTestButtonEnabled(true);
            submissionControllerTutorialGui.printResultMessage(START_TEST_TEXT);
            notebookEditorPanel.removeSaveButtonActionListener(this);
            submissionControllerTutorialGui.addTestButtonActionListener(startTestActionListener);
        }
        
    };
    
    private final ActionListener startTestActionListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(BEGINNING_OF_TEST_TEXT);
            submissionControllerTutorialGui.showDialoguePanel();
            pauseToDisplayText();
            submissionControllerTutorialGui.addDialogueActionListener(resumeOnDialogueActionListener);
            tutorialNotebookDeveloper.getLittleMan().addMemorizePageActionListener(getFirstInstructionActionListener);
            submissionControllerTutorialGui.removeTestButtonActionListener(this);
        }
        
    };
    
    private final ActionListener resumeOnDialogueActionListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            speedController.resume();
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.setDialogueButtonIsEnabled(false);
        }
        
    };
    private final ActionListener getFirstInstructionActionListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(READ_INSTRUCTION_TEXT);
            pauseToDisplayText();
            tutorialNotebookDeveloper.getLittleMan().addMemorizeDataActionListener(incrementNotebookPageSheetActionListener);
            tutorialNotebookDeveloper.getLittleMan().removeMemorizePageActionListener(getFirstInstructionActionListener);
        }
        
    };
    private final ActionListener incrementNotebookPageSheetActionListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(INCREMENT_NOTEBOOK_PAGE_SHEET_TEXT);
            pauseToDisplayText();
            tutorialNotebookDeveloper.getLittleMan().addUnaryOperationActionListener(goToInputPanelActionListener);
            tutorialNotebookDeveloper.getLittleMan().removeMemorizeDataActionListener(this);
        }
        
    };
    private final ActionListener goToInputPanelActionListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(GO_TO_INPUT_PANEL_TEXT);
            pauseToDisplayText();
            tutorialNotebookDeveloper.getLittleMan().addDataFromInputPanelActionListener(doInputActionListener);
            tutorialNotebookDeveloper.getLittleMan().removeUnaryOperationActionListener(this);
        }
        
    };
    private final ActionListener doInputActionListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(DO_INPUT_TEXT);
            pauseToDisplayText();
            tutorialNotebookDeveloper.getLittleMan().addBinaryOperationActionListener(getSecondInstructionActionListener);
            tutorialNotebookDeveloper.getLittleMan().removeDataFromInputPanelActionListener(this);
        }
        
    };
    private final ActionListener getSecondInstructionActionListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(READ_SECOND_INSTRUCTION_TEXT);
            pauseToDisplayText();
            tutorialNotebookDeveloper.getLittleMan().addUnaryOperationActionListener(firstOutputActionListener);
            tutorialNotebookDeveloper.getLittleMan().removeBinaryOperationActionListener(this);
        }
        
    };
    private final ActionListener firstOutputActionListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(FIRST_OUTPUT_TEXT);
            pauseToDisplayText();
            tutorialNotebookDeveloper.getLittleMan().addPrintUnsignedToOutputPanelActionListener(readThirdInstructionListener);
            tutorialNotebookDeveloper.getLittleMan().removeUnaryOperationActionListener(this);
        }
        
    };
    private final ActionListener readThirdInstructionListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(READ_THIRD_INSTRUCTION_TEXT);
            pauseToDisplayText();
            tutorialNotebookDeveloper.getLittleMan().addUnaryOperationActionListener(readAddressOperandListener);
            tutorialNotebookDeveloper.getLittleMan().removePrintUnsignedToOutputPanelActionListener(this);
        }
        
    };
    private final ActionListener readAddressOperandListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(READ_ADDRESS_OPERAND_TEXT);
            pauseToDisplayText();
            tutorialNotebookDeveloper.getLittleMan().addUnaryOperationActionListener(doFirstMoveListener);
            tutorialNotebookDeveloper.getLittleMan().removeUnaryOperationActionListener(this);
        }
        
    };
    private final ActionListener doFirstMoveListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(DO_FIRST_MOVE_TEXT);
            pauseToDisplayText();
            tutorialNotebookDeveloper.getLittleMan().addBinaryOperationActionListener(readFourthInstructionListener);
            tutorialNotebookDeveloper.getLittleMan().removeUnaryOperationActionListener(this);
        }
        
    };
    private final ActionListener readFourthInstructionListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(READ_FOURTH_INSTRUCTION_TEXT);
            pauseToDisplayText();
            tutorialNotebookDeveloper.getLittleMan().addUnaryOperationActionListener(readMoveDataOperandListener);
            tutorialNotebookDeveloper.getLittleMan().removeBinaryOperationActionListener(this);
        }
        
    };
    private final ActionListener readMoveDataOperandListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(READ_DATA_OPERAND_TEXT);
            pauseToDisplayText();
            tutorialNotebookDeveloper.getLittleMan().addUnaryOperationActionListener(doSecondMoveListener);
            tutorialNotebookDeveloper.getLittleMan().removeUnaryOperationActionListener(this);
        }
        
    };
    private final ActionListener doSecondMoveListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(DO_SECOND_MOVE_TEXT);
            pauseToDisplayText();
            tutorialNotebookDeveloper.getLittleMan().addBinaryOperationActionListener(doSecondOutputListener);
            tutorialNotebookDeveloper.getLittleMan().removeUnaryOperationActionListener(this);
        }
        
    };
    private final ActionListener doSecondOutputListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(DO_SECOND_OUTPUT_TEXT);
            pauseToDisplayText();
            tutorialNotebookDeveloper.getLittleMan().addPrintUnsignedToOutputPanelActionListener(readAddInstructionListener);
            tutorialNotebookDeveloper.getLittleMan().removeBinaryOperationActionListener(this);
        }
        
    };
    private final ActionListener readAddInstructionListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(READ_ADD_INSTRUCTION_TEXT);
            pauseToDisplayText();
            tutorialNotebookDeveloper.getLittleMan().addUnaryOperationActionListener(getAddDataOperandInstructionListener);
            tutorialNotebookDeveloper.getLittleMan().removePrintUnsignedToOutputPanelActionListener(this);
        }
        
    };
    private final ActionListener getAddDataOperandInstructionListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(GET_ADD_DATA_OPERAND_TEXT);
            pauseToDisplayText();
            tutorialNotebookDeveloper.getLittleMan().addUnaryOperationActionListener(getAddPageNumberOperandInstructionListener);
            tutorialNotebookDeveloper.getLittleMan().removeUnaryOperationActionListener(this);
        }
        
    };
    private final ActionListener getAddPageNumberOperandInstructionListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(GET_ADD_PAGE_NUMBER_OPERAND_TEXT);
            pauseToDisplayText();
            tutorialNotebookDeveloper.getLittleMan().addUnaryOperationActionListener(doAdditionListener);
            tutorialNotebookDeveloper.getLittleMan().removeUnaryOperationActionListener(this);
        }
        
    };
    private final ActionListener doAdditionListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(DO_ADDITION_TEST_TEXT);
            pauseToDisplayText();
            tutorialNotebookDeveloper.getLittleMan().addBinaryOperationActionListener(finalMoveListener);
            tutorialNotebookDeveloper.getLittleMan().removeUnaryOperationActionListener(this);
        }
        
    };
    private final ActionListener finalMoveListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(FINAL_MOVE_TEXT);
            pauseToDisplayText();
            tutorialNotebookDeveloper.getLittleMan().addBinaryOperationActionListener(thirdOutputListener);
            tutorialNotebookDeveloper.getLittleMan().removeBinaryOperationActionListener(this);
        }
        
    };
    private final ActionListener thirdOutputListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(THIRD_OUTPUT_TEXT);
            pauseToDisplayText();
            tutorialNotebookDeveloper.getLittleMan().addPrintUnsignedToOutputPanelActionListener(haltListener);
            tutorialNotebookDeveloper.getLittleMan().removeUnaryOperationActionListener(this);
        }
        
    };
    private final ActionListener haltListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(HALT_TEST_TEXT);
            pauseToDisplayText();
            tutorialNotebookDeveloper.getLittleMan().addHaltActionListener(finishTest);
            tutorialNotebookDeveloper.getLittleMan().removePrintUnsignedToOutputPanelActionListener(this);
        }
        
    };
    private final ActionListener finishTest = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.printDialogue(FINISH_TEST_TEXT);
            pauseToDisplayText();
            submissionControllerTutorialGui.addDialogueActionListener(showSubmissionPanelToSubmit);
            tutorialNotebookDeveloper.getLittleMan().removeHaltActionListener(this);
        }
        
    };
    private final ActionListener showSubmissionPanelToSubmit = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.showSubmissionPanel();
            submissionControllerTutorialGui.setEditMemoryEnabled(false);
            submissionControllerTutorialGui.setTestButtonEnabled(false);
            submissionControllerTutorialGui.setSubmitButtonEnabled(true);
            submissionControllerTutorialGui.printResultMessage(SUBMIT_TEXT);
            submissionControllerTutorialGui.addSubmitButtonActionListener(submitActionListener);
            submissionControllerTutorialGui.removeDialogueActionListener(this);
            submissionControllerTutorialGui.removeDialogueActionListener(resumeOnDialogueActionListener);
        }
        
    };
    private final ActionListener submitActionListener = new ActionListener() {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
            submissionControllerTutorialGui.showDialoguePanel();
            submissionControllerTutorialGui.removeSubmitButtonActionListener(this);
            submissionControllerTutorialGui.addDialogueActionListener(endTutorialActionListener);
            submissionControllerTutorialGui.printDialogue(SUBMISSION_RESULT_TEXT);
            submissionControllerTutorialGui.setDialogueButtonIsEnabled(true);
        }
        
    };
    
    public TutorialDriver(TutorialNotebookDeveloper tutorialNotebookDeveloper, TutorialLittleManGui tutorialLittleManGui, SpeedController speedController, ActionListener endTutorialActionListener) {
        this.tutorialNotebookDeveloper = tutorialNotebookDeveloper;
        this.tutorialLittleManGui = tutorialLittleManGui;
        this.speedController = speedController;
        this.endTutorialActionListener = endTutorialActionListener;
    }
    
    private void pauseToDisplayText() {
        final SubmissionControllerTutorialGui submissionControllerTutorialGui = tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui();
        submissionControllerTutorialGui.setDialogueButtonIsEnabled(true);
        speedController.pause();
    }
    
    public void printDialogue(String dialogue) {
        tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui().showDialoguePanel();
        tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui().printDialogue(dialogue);
    }
    
    public void doFrames(int numFrames) {
        tutorialNotebookDeveloper.doFrames(numFrames);
    }
    
    public void startTutorial() {
        hideArrows();
        tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui().showDialoguePanel();
        printDialogue(INTRO_TEXT);
        tutorialLittleManGui.getNotebookDeveloperGui().getSubmissionControllerTutorialGui().addDialogueActionListener(endIntroBeginNotebookNotebookListener);
    }
    
    private void hideArrows() {
        tutorialNotebookDeveloper.setIsInputPanelArrowShown(false);
        tutorialNotebookDeveloper.setIsNotebookArrowShown(false);
        tutorialNotebookDeveloper.setIsNotebookPageSheetArrowShown(false);
        tutorialNotebookDeveloper.setIsOutputPanelArrowShown(false);
        tutorialNotebookDeveloper.setIsWorksheetArrowShown(false);
    }
    
}
