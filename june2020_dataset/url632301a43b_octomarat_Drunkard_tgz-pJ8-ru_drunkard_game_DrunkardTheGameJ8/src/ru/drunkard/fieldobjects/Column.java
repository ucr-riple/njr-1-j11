package ru.drunkard.fieldobjects;

import ru.drunkard.field.GameField;
import ru.drunkard.gameprinters.GamePrinter;

public class Column implements IFieldObj {

    public void doActions(GameField field) { /*does nothing*/ }

    public void visit(Drunkard drunkard) {}
    public void visit(Column column) {}
    public void visit(Bottle bottle) {}
    public void visit(LampPost lampPost) {}
    public void visit(Cop cop) {}
    public void visit(Hobo hobo) {}

    public void accept(IFieldObj visitor) { visitor.visit(this); }
    public void accept(GamePrinter printer) { printer.visit(this); }
}
