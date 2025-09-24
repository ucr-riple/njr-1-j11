package ru.drunkard.field;

import ru.drunkard.fieldobjects.IFieldObj;
import ru.drunkard.gameprinters.GamePrinter;

public class Sector {
    private IFieldObj object;

    Sector(IFieldObj object) { this.object = object; }

    public boolean isEmpty() { return object == null; }
    public void setObject(IFieldObj object) { this.object = object; }
    public void acceptVisitor(IFieldObj visitor) {
        if(object != null) { object.accept(visitor); }
    }
    public void acceptVisitor(GamePrinter visitor) {
        if(object != null) { object.accept(visitor); }
    }
}
