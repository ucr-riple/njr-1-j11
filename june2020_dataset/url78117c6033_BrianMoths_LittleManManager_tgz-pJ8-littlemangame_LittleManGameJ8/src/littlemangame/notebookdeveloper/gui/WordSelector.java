/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.notebookdeveloper.gui;

import java.util.Iterator;
import javax.swing.JComboBox;
import littlemangame.word.Word;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author brian
 */
public class WordSelector extends JComboBox<Word> {

    static private final Word[] WORDS = makeWords();

    static private Word[] makeWords() {
        Word[] words = new Word[Word.NUM_WORDS];
        Iterator<Word> wordIterator = Word.getIterator();
        int i = 0;
        while (wordIterator.hasNext()) {
            words[i] = wordIterator.next();
            i++;
        }
        return words;
    }

    private Word value;

    /**
     * Creates new form InputPanel
     */
    public WordSelector() {
        initWordModel();
    }

    private void initWordModel() {
        setModel(new javax.swing.DefaultComboBoxModel<>(WORDS)); //perhaps model can be static? that way I won't need a different instance test this soon.
        AutoCompleteDecorator.decorate(this);
    }

    public Word getLastSelectedWord() {
        return getItemAt(getSelectedIndex());
    }

    public void setSelectedWord(Word word) {
        setSelectedIndex(word.getUnsignedValue());
    }

    @Override
    public void setEnabled(boolean isEnabled) {
        super.setEnabled(isEnabled);
        super.isEditable = isEnabled;
        hidePopup();
    }

}
