/**
  Copyright (c) 2013 Artur Huletski (hatless.fox@gmail.com)
  Permission is hereby granted, free of charge, to any person obtaining a copy 
  of this software and associated documentation files (the "Software"),
  to deal in the Software without restriction, including without limitation
  the rights to use, copy, modify, merge, publish, distribute, sublicense,
  and/or sell copies of the Software, and to permit persons
  to whom the Software is furnished to do so,
  subject to the following conditions:
  
  The above copyright notice and this permission notice shall be included
  in all copies or substantial portions of the Software.
  
  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
  OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package ru.spbau.sd.model.framework;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents an instance of any field object
 * 
 * @author Artur Huletski (hatless.fox@gmail.com)
 *
 */
abstract public class FieldObject extends GameObject{

    private InteractionHandlers mIntHandlers = new InteractionHandlers();
    
    public FieldObject(int x, int y) {
        super(x, y);
    }
    
    /**
     * It's guaranteed that this method will be called
     * only on actual position change
     * 
     * @param x
     * @param y
     */
    public void setNewPosition(int x, int y) {
        setX(x);
        setY(y);
    }
    
    public void setNewPosition(Point2D point) {
        setX(point.x);
        setY(point.y);
    }
    
    
    /**
     * Registers interaction handler which will be called when given object
     * will be an initiator of collision with object of type typeOfObject.
     * 
     * @param typeOfActor
     * @param typeOfObject
     * @param handler
     */
    public <T extends FieldObject, U extends FieldObject> 
        void registerInteractionHandler(
            Class<T> typeOfActor, Class<U> typeOfObject,
            InteractionStrategy<T, U> handler) {
        mIntHandlers.register(typeOfActor, typeOfObject, handler);
    }
    
    /**
     * Handle interaction with some field object
     * @param obj
     */
    public void interact(FieldObject obj) {
        if (mIntHandlers.canInteract(obj.getClass())) {
            mIntHandlers.getHandler(obj.getClass()).performInteraction(this, obj);
        }
    }

    
    //container for interaction handlers
    private static class InteractionHandlers {
        protected Map<Class<?>, InteractionStrategy<?, ?>> mIntHandlers = new HashMap<Class<?>, InteractionStrategy<?, ?>>();
        
        public <T extends FieldObject, U extends FieldObject> void register(
            Class<T> typeOfActor, Class<U> typeOfObject,
            InteractionStrategy<T, U> strategy) {
            mIntHandlers.put(typeOfObject, strategy);
        }
        
        public boolean canInteract(Class<?> typeOfObject) {
            return mIntHandlers.containsKey(typeOfObject);
        }
        
        @SuppressWarnings("unchecked") //we register only InteractionStrategy<? extends FO, ? extends FO>
        public InteractionStrategy<FieldObject, FieldObject> getHandler(Class<? extends FieldObject> typeOfObject) {
            return (InteractionStrategy<FieldObject, FieldObject>) mIntHandlers.get(typeOfObject);
        }
    }
}
