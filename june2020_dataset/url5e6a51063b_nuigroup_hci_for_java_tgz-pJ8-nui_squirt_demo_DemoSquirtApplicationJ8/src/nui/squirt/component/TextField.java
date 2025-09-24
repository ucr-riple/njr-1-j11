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
package nui.squirt.component;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;

import nui.squirt.ControlPoint;
import nui.squirt.Keyboard;
import nui.squirt.NUIController;
import nui.squirt.TextInput;
import nui.squirt.event.KeyEvent;
import nui.squirt.event.TextEvent;
import nui.squirt.listener.KeyListener;
import nui.squirt.listener.TextListener;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class TextField extends Rectangle implements TextInput, KeyListener {
	
	private static final Color TEXT_FIELD_FILL_COLOR = Color.WHITE;
	private static final Color TEXT_FIELD_BORDER_COLOR = Color.BLACK;
	private static final Color TEXT_FIELD_TEXT_COLOR = Color.BLACK;
	
	private static final int TEXT_FIELD_BORDER_WIDTH = 2;
	private static final int TEXT_FIELD_TEXT_PADDING = 4;
	
	private String text = new String();
	
	private PGraphics textCanvas;
	
	private int timer = 0;
	private int caretIndex = 0;
	private int secondaryCaretIndex = caretIndex;
	
	private float textOffset = 0;
	
	private boolean shiftDown = false;
	
	private Collection<TextListener> listeners = new ArrayList<TextListener>();
	
	private Collection<Keyboard> keyboards = new ArrayList<Keyboard>();
	
	private ControlPoint controlPoint;

	public TextField(float x, float y, float w) {
		super(x, y, w, 0);
	}
	
	public TextField(float x, float y, float w, String initialText) {
		this(x, y, w);
		this.text = initialText;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		TextEvent e = new TextEvent(this);
		e.setOldText(getText());
		e.setNewText(text);
		
		this.text = text;
		
		fireTextChanged(e);
	}

	public int getCaretIndex() {
		return caretIndex;
	}

	public void setCaretIndex(int caretIndex) {
		if (caretIndex < 0) caretIndex = 0;
		if (caretIndex > getText().length()) caretIndex = getText().length();
		this.caretIndex = caretIndex;
	}

	public int getSecondaryCaretIndex() {
		return secondaryCaretIndex;
	}

	public void setSecondaryCaretIndex(int secondaryCaretIndex) {
		if (secondaryCaretIndex < 0) secondaryCaretIndex = 0;
		if (secondaryCaretIndex > getText().length()) secondaryCaretIndex = getText().length();
		this.secondaryCaretIndex = secondaryCaretIndex;
	}

	@Override
	public Color getFillColor() {
		return TEXT_FIELD_FILL_COLOR;
	}
	
	@Override
	public Color getStrokeColor() {
		return TEXT_FIELD_BORDER_COLOR;
	}
	
	@Override
	public float getStrokeWeight() {
		return TEXT_FIELD_BORDER_WIDTH;
	}
	
	@Override
	public void preRender(PApplet p) {
		super.preRender(p);
		setHeight(p.textAscent()+p.textDescent()+2*TEXT_FIELD_TEXT_PADDING);
	}
	
	@Override
	public void render(PApplet p) {
		super.render(p);
		
		if (textCanvas == null) {
			textCanvas = p.createGraphics((int) getWidth()-2*TEXT_FIELD_TEXT_PADDING, (int) getHeight()-2*TEXT_FIELD_TEXT_PADDING, PApplet.JAVA2D);
		}
		textCanvas.beginDraw();
		textCanvas.stroke(TEXT_FIELD_TEXT_COLOR.getRGB());
		textCanvas.fill(TEXT_FIELD_TEXT_COLOR.getRGB());
		textCanvas.textAlign(PApplet.LEFT, PApplet.TOP);
		textCanvas.textFont(NUIController.getInstance().getFont());
		textCanvas.rectMode(PApplet.CORNER);
		
		textCanvas.background(TEXT_FIELD_FILL_COLOR.getRGB());
		float caretPos = textCanvas.textWidth(getText().substring(0, getCaretIndex()));
		if (caretPos + textOffset > textCanvas.width) {
			textOffset -= caretPos + textOffset - textCanvas.width + 2;
		}
		if (caretPos + textOffset < 0) {
			textOffset += -(caretPos + textOffset) + 2;
		}
		textCanvas.text(getText(), textOffset, 0);
		textCanvas.noStroke();
		textCanvas.fill(16, 16, 255, 80);
		textCanvas.rectMode(PApplet.CORNERS);
		float secondaryPos = textCanvas.textWidth(getText().substring(0, getSecondaryCaretIndex()));
		textCanvas.rect(secondaryPos + textOffset, 0, caretPos + textOffset, textCanvas.height);
		if (!keyboards.isEmpty() && ++timer%30 < 15) {
			textCanvas.stroke(TEXT_FIELD_TEXT_COLOR.getRGB());
			textCanvas.line(caretPos + textOffset, 0, caretPos + textOffset, textCanvas.height);
		}
		textCanvas.endDraw();
		
		p.image(textCanvas, 0, 0);
	}

	public void keyPressed(KeyEvent e) {
		if (e.isCoded()) {
			switch (e.getKeyCode()) {
				case KeyEvent.LEFT:
					int i = getCaretIndex();
					int s = getSecondaryCaretIndex();
					if (shiftDown) {
						setCaretIndex(i-1);
					}
					else {
						if (i == s) {
							setCaretIndex(i-1);
							setSecondaryCaretIndex(getCaretIndex());
						}
						else if (i < s) {
							setSecondaryCaretIndex(i);
						}
						else if (i > s) {
							setCaretIndex(s);
						}
					}
					break;
				case KeyEvent.RIGHT:
					i = getCaretIndex();
					s = getSecondaryCaretIndex();
					if (shiftDown) {
						setCaretIndex(i+1);
					}
					else {
						if (i == s) {
							setCaretIndex(i+1);
							setSecondaryCaretIndex(getCaretIndex());
						}
						else if (i > s) {
							setSecondaryCaretIndex(i);
						}
						else if (i < s) {
							setCaretIndex(s);
						}
					}
					break;
				case KeyEvent.SHIFT:
					shiftDown = true;
					break;
			}
		}
		timer = 0;
	}

	public void keyReleased(KeyEvent e) {
		if (e.isCoded()) {
			switch (e.getKeyCode()) {
				case KeyEvent.SHIFT:
					shiftDown = false;
					break;
			}
		}
	}

	public void keyTyped(KeyEvent e) {
		if (!e.isCoded()) {
			if (e.getKey() == KeyEvent.BACKSPACE && getCaretIndex() > 0) {
				setText(getText().substring(0, getCaretIndex()-1) + getText().substring(getCaretIndex()));
				setCaretIndex(getCaretIndex()-1);
			}
			else if (e.getKey() == KeyEvent.DELETE && getCaretIndex() < getText().length()) {
				setText(getText().substring(0, getCaretIndex()) + getText().substring(getCaretIndex()+1));
			}
			else if (e.getKey() != KeyEvent.DELETE && e.getKey() != KeyEvent.BACKSPACE) {
				int min = Math.min(getCaretIndex(), getSecondaryCaretIndex());
				int max = Math.max(getCaretIndex(), getSecondaryCaretIndex());
				setText(getText().substring(0, min) + e.getKey() + getText().substring(max));
				setCaretIndex(min+1);
			}
			setSecondaryCaretIndex(getCaretIndex());
		}
	}

	public void addTextListener(TextListener l) {
		listeners.add(l);
	}

	public void removeTextListener(TextListener l) {
		listeners.remove(l);
	}

	public void fireTextChanged(TextEvent e) {
		for (TextListener l: listeners) {
			l.textChanged(e);
		}
	}

	public void addKeyboard(Keyboard k) {
		keyboards.add(k);
	}

	public void removeKeyboard(Keyboard k) {
		keyboards.remove(k);
	}
	
	@Override
	public boolean canAcceptMoreControlPoints() {
		return controlPoint == null;
	}
	
	@Override
	public boolean offer(ControlPoint cp) {
		cp.addControlPointListener(this);
		return true;
	}
	
	@Override
	public void controlPointCreated(ControlPoint cp) {
		if (controlPoint == null && isUnderPoint(cp)) {
			this.controlPoint = cp;
			NUIController.getInstance().addKeyListener(this);
			PVector local = transformToLocalSpace(new PVector(cp.getX(), cp.getY()));
			local.add(getWidth()/2 - TEXT_FIELD_TEXT_PADDING, getHeight()/2 - TEXT_FIELD_TEXT_PADDING, 0);
			setCaretIndex(mapPosToStringIndex(local));
			if (!shiftDown) {
				setSecondaryCaretIndex(getCaretIndex());
			}
		}
	}
	
	@Override
	public void controlPointDied(ControlPoint cp) {
		if (controlPoint != null && controlPoint.equals(cp)) {
			controlPoint = null;
		}
	}
	
	@Override
	public void controlPointUpdated(ControlPoint cp) {
		if (controlPoint != null && controlPoint.equals(cp)) {
			PVector local = transformToLocalSpace(new PVector(cp.getX(), cp.getY()));
			local.add(getWidth()/2 - TEXT_FIELD_TEXT_PADDING, getHeight()/2 - TEXT_FIELD_TEXT_PADDING, 0);
			setCaretIndex(mapPosToStringIndex(local));
		}
	}

	private int mapPosToStringIndex(PVector pos) {
		int cur = getText().length()/2;
		float curPos = indexPos(cur);
		float nextPos = indexPos(cur+1);
		float prevPos = indexPos(cur-1);
		int leftBounds = 0;
		int rightBounds = getText().length();
		
		while (!(pos.x >= curPos && pos.x <= nextPos) && !(pos.x <= curPos && pos.x >= prevPos)) {
			if (pos.x > curPos) {
				leftBounds = rightBounds - leftBounds == 1 ? rightBounds : cur;
			}
			else if (pos.x < curPos) {
				rightBounds = rightBounds - leftBounds == 1 ? leftBounds : cur;
			}
			cur = (rightBounds - leftBounds)/2 + leftBounds;
			curPos = indexPos(cur);
			nextPos = indexPos(cur+1);
			prevPos = indexPos(cur-1);
		}
		if (pos.x >= curPos) {
			if (pos.x - curPos < nextPos - pos.x)
				return cur;
			else return cur+1 > getText().length() ? getText().length() : cur+1;
		}
		else {
			if (curPos - pos.x < pos.x - prevPos)
				return cur;
			else return cur-1 < 0 ? 0 : cur-1;
		}
	}

	private float indexPos(int index) {
		if (index > getText().length())
			return Float.POSITIVE_INFINITY;
		if (index < 0)
			return Float.NEGATIVE_INFINITY;
		return textCanvas.textWidth(getText().substring(0, index)) + textOffset;
	}

}
