/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.notebookdeveloper.gui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import littlemangame.computer.computercomponents.Notebook;
import littlemangame.instructions.InstructionFromSet;
import littlemangame.word.Word;

/**
 *
 * @author brian
 */
public class NotebookEditorPanel extends javax.swing.JPanel {

    protected final List<PageEditor> memorySlotChoosers;

    /**
     * Creates new form MemoryEditorPanel
     */
    public NotebookEditorPanel() {
        initComponents();
        memorySlotChoosers = new ArrayList<>(Word.NUM_WORDS);
        addMemorySlotChoosers();
        writeDescriptions();
    }

    private void addMemorySlotChoosers() {
        Iterator<Word> wordIterator = Word.getIterator();
        scrollPanePanel.setLayout(new GridLayout(Word.NUM_WORDS, 1, 0, 5));
        while (wordIterator.hasNext()) {
            final PageEditor memorySlotChooser = new PageEditor(wordIterator.next());
            scrollPanePanel.add(memorySlotChooser);
            memorySlotChoosers.add(memorySlotChooser);
            revalidate();
        }
        revalidate();
        repaint();
    }

    private void writeDescriptions() {
        DefaultTreeCellRenderer treeCellRenderer = new DefaultTreeCellRenderer();
        treeCellRenderer.setLeafIcon(null);
        treeCellRenderer.setOpenIcon(null);
        treeCellRenderer.setClosedIcon(null);
        instructionDescriptionTree.setCellRenderer(treeCellRenderer);

        javax.swing.tree.DefaultMutableTreeNode rootNode = new javax.swing.tree.DefaultMutableTreeNode("rootNode");
        javax.swing.tree.DefaultMutableTreeNode haltNoop = new javax.swing.tree.DefaultMutableTreeNode("<html><b>Halt and no operation</b></html>");
        javax.swing.tree.DefaultMutableTreeNode jumps = new javax.swing.tree.DefaultMutableTreeNode("<html><b>Jumps</b></html>");
        javax.swing.tree.DefaultMutableTreeNode inputOutput = new javax.swing.tree.DefaultMutableTreeNode("<html><b>Input/output</b></html>");
        javax.swing.tree.DefaultMutableTreeNode dataMovement = new javax.swing.tree.DefaultMutableTreeNode("<html><b>Data movement</b></html>");
        javax.swing.tree.DefaultMutableTreeNode digitwiseManipulation = new javax.swing.tree.DefaultMutableTreeNode("<html><b>Digitwise manipulation</b></html>");
        javax.swing.tree.DefaultMutableTreeNode digitShifts = new javax.swing.tree.DefaultMutableTreeNode("<html><b>Digit shifts</b></html>");
        javax.swing.tree.DefaultMutableTreeNode arithmetic = new javax.swing.tree.DefaultMutableTreeNode("<html><b>Arithmetic</b></html>");
        List<DefaultMutableTreeNode> topNodes = new ArrayList<>();
        topNodes.add(haltNoop);
        topNodes.add(jumps);
        topNodes.add(inputOutput);
        topNodes.add(dataMovement);
        topNodes.add(digitwiseManipulation);
        topNodes.add(digitShifts);
        topNodes.add(arithmetic);

        for (DefaultMutableTreeNode defaultMutableTreeNode : topNodes) {
            rootNode.add(defaultMutableTreeNode);
        }

        List<Word> lowerLimitWords = new ArrayList<>();
        lowerLimitWords.add(Word.valueOfLastDigitsOfInteger(10));
        lowerLimitWords.add(Word.valueOfLastDigitsOfInteger(20));
        lowerLimitWords.add(Word.valueOfLastDigitsOfInteger(30));
        lowerLimitWords.add(Word.valueOfLastDigitsOfInteger(40));
        lowerLimitWords.add(Word.valueOfLastDigitsOfInteger(60));
        lowerLimitWords.add(Word.valueOfLastDigitsOfInteger(70));
        lowerLimitWords.add(Word.valueOfLastDigitsOfInteger(99));

        int categoryIndex = 0;
        Word lowerLimitOfNextCategory = lowerLimitWords.get(categoryIndex);
        DefaultMutableTreeNode currentTopNode = topNodes.get(categoryIndex);
        for (InstructionFromSet instructionFromSet : InstructionFromSet.values()) {
            if (instructionFromSet.getOpcode().compareToUnsigned(lowerLimitOfNextCategory) != -1) {
                categoryIndex++;
                lowerLimitOfNextCategory = lowerLimitWords.get(categoryIndex);
                currentTopNode = topNodes.get(categoryIndex);
            }
            currentTopNode.add(makeNode(instructionFromSet));
        }

        instructionDescriptionTree.setModel(new javax.swing.tree.DefaultTreeModel(rootNode));
    }

    DefaultMutableTreeNode makeNode(InstructionFromSet instructionFromSet) {
        DefaultMutableTreeNode instructionNode = new DefaultMutableTreeNode(instructionFromSet.getDescription());
        DefaultMutableTreeNode detailsNode = new DefaultMutableTreeNode(instructionFromSet.getDetails());
        instructionNode.add(detailsNode);
        return instructionNode;
    }

    public Notebook getNotebook() {
        Notebook memory = new Notebook();
        Iterator<Word> wordIterator = Word.getIterator();
        while (wordIterator.hasNext()) {
            final Word word = wordIterator.next();
            memory.setWordAtPage(word, memorySlotChoosers.get(word.getUnsignedValue()).getSelectedWord());
        }
        return memory;
    }

    public void setMemory(Notebook memory) {
        Iterator<Word> wordIterator = Word.getIterator();
        while (wordIterator.hasNext()) {
            final Word word = wordIterator.next();
            PageEditor memorySlotChooser = memorySlotChoosers.get(word.getUnsignedValue());
            memorySlotChooser.setSelectedWord(memory.getWordOnPage(word));
        }
    }

    public void setSaveAction(final ActionListener actionListener) {
        saveButton.addActionListener(actionListener);
    }

    public void setProblemDescription(String problemDescription) {
        problemDescriptionTextArea.setText(problemDescription);
        problemDescriptionTextArea.setCaretPosition(0);
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        instructionDescriptionTree = new javax.swing.JTree();
        jScrollPane1 = new javax.swing.JScrollPane();
        scrollPanePanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        problemDescriptionTextArea = new javax.swing.JTextArea();

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("JTree");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("<html><b>halt and no operation</b></html>");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("blue");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("violet");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("red");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("yellow");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("sports");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("basketball");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("soccer");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("football");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("hockey");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("food");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("hot dogs");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("pizza");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("ravioli");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("bananas");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        instructionDescriptionTree.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        instructionDescriptionTree.setRootVisible(false);
        instructionDescriptionTree.setShowsRootHandles(true);
        jScrollPane2.setViewportView(instructionDescriptionTree);

        saveButton.setText("Save");

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        javax.swing.GroupLayout scrollPanePanelLayout = new javax.swing.GroupLayout(scrollPanePanel);
        scrollPanePanel.setLayout(scrollPanePanelLayout);
        scrollPanePanelLayout.setHorizontalGroup(
            scrollPanePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 161, Short.MAX_VALUE)
        );
        scrollPanePanelLayout.setVerticalGroup(
            scrollPanePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 390, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(scrollPanePanel);

        problemDescriptionTextArea.setEditable(false);
        problemDescriptionTextArea.setColumns(20);
        problemDescriptionTextArea.setLineWrap(true);
        problemDescriptionTextArea.setRows(5);
        problemDescriptionTextArea.setWrapStyleWord(true);
        jScrollPane3.setViewportView(problemDescriptionTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree instructionDescriptionTree;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea problemDescriptionTextArea;
    protected final javax.swing.JButton saveButton = new javax.swing.JButton();
    private javax.swing.JPanel scrollPanePanel;
    // End of variables declaration//GEN-END:variables
}
