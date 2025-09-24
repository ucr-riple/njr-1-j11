/*******************************************************************************
 * This file is part of sqUIrt
 * 
 *     Copyright (C) 2009  Ori Rawlings
 * 
 *     sqUIrt is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     sqUIrt is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with sqUIrt.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package nui.squirt.event;

import nui.squirt.Keyboard;
import processing.core.PConstants;

public class KeyEvent extends Event {
	
	// Key codes
	public static final int UP = PConstants.UP;
	public static final int DOWN = PConstants.DOWN;
	public static final int LEFT = PConstants.LEFT;
	public static final int RIGHT = PConstants.RIGHT;
	public static final int SHIFT = PConstants.SHIFT;
	public static final int ALT = PConstants.ALT;
	public static final int CONTROL = PConstants.CONTROL;
	
	// Key codes and char values
	public static final char BACKSPACE = PConstants.BACKSPACE;
	public static final char DELETE = PConstants.DELETE;
	public static final char TAB = PConstants.TAB;
	public static final char ENTER = PConstants.ENTER;
	public static final char RETURN = PConstants.RETURN;
	public static final char ESC = PConstants.ESC;
	
	private boolean coded;
	private char key;
	private int keyCode;

	public KeyEvent(Keyboard source) {
		super(source);
	}

	public boolean isCoded() {
		return coded;
	}

	public void setCoded(boolean coded) {
		this.coded = coded;
	}

	public char getKey() {
		return key;
	}

	public void setKey(char key) {
		this.key = key;
	}

	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

}
