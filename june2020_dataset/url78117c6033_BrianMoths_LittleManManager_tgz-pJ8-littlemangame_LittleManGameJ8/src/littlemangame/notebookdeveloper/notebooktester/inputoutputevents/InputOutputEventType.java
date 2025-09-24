/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package littlemangame.notebookdeveloper.notebooktester.inputoutputevents;

/**
 *
 * @author brian
 */
public enum InputOutputEventType {

    INPUT(new ActualAndExpectedActionPerformedProducer() {

        @Override
        public String getBareInfinitive() {
            return "ask for input";
        }

        @Override
        public String getPastTense() {
            return "asked for input";
        }

    }),
    OUTPUT(new ActualAndExpectedActionPerformedProducer() {

        @Override
        public String getBareInfinitive() {//this and expected can't both be right? 
            return "output ";
        }

        @Override
        public String getPastTense() {
            return "output ";
        }

    }),
    HALT(new ActualAndExpectedActionPerformedProducer() {

        @Override
        public String getBareInfinitive() {
            return "halt";
        }

        @Override
        public String getPastTense() {
            return "halted";
        }

    });

    private final ActualAndExpectedActionPerformedProducer actualAndExcpectedActionPerformedProducer;

    private InputOutputEventType(ActualAndExpectedActionPerformedProducer actualAndExcpectedActionPerformedProducer) {
        this.actualAndExcpectedActionPerformedProducer = actualAndExcpectedActionPerformedProducer;
    }

    public String getBareInfinitive() {
        return actualAndExcpectedActionPerformedProducer.getBareInfinitive();
    }

    public String getPastTense() {
        return actualAndExcpectedActionPerformedProducer.getPastTense();
    }

    static private interface ActualAndExpectedActionPerformedProducer {

        public String getBareInfinitive();

        public String getPastTense();

    }
}
