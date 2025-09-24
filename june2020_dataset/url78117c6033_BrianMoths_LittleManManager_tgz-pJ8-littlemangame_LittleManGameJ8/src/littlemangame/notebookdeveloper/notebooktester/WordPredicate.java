/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.notebookdeveloper.notebooktester;

import littlemangame.word.Word;

/**
 *
 * @author brian
 */
public abstract class WordPredicate {

    static public WordPredicate alwaysTruePredicate = new WordPredicate() {

        @Override
        public boolean isCorrect(Word word) {
            return true;
        }

        @Override
        public String expectedWordDescription() {
            return "any word";
        }

    };

    static WordPredicate makeEqualsWordPredicate(final Word expectedWord) {
        return new WordPredicate() {

            @Override
            public boolean isCorrect(Word actualWord) {
                return expectedWord != null && expectedWord.equals(actualWord);
            }

            @Override
            public String expectedWordDescription() {
                return expectedWord.toString();
            }

        };
    }

    abstract public boolean isCorrect(Word word);

    public String expectedWordString() {
        return "I expected him to output " + expectedWordDescription();
    }

    abstract protected String expectedWordDescription();

}
