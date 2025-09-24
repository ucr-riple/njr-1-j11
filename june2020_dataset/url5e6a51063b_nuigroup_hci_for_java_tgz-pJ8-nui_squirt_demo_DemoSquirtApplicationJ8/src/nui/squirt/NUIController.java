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
package nui.squirt;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import nui.squirt.component.AbstractContainer;
import nui.squirt.controlpoint.MouseControlPoint;
import nui.squirt.controlpoint.TUIOControlPoint;
import nui.squirt.event.KeyEvent;
import nui.squirt.listener.KeyListener;

import org.jbox2d.collision.AABB;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PVector;
import TUIO.TuioClient;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

@SuppressWarnings("serial")
public class NUIController extends AbstractContainer implements TuioListener, Keyboard {
	
	private static NUIController n;
	
	private float textAscent;
	private float textDescent;
	private PFont font;
	
	private TuioClient tc;
	
	private HashMap<TuioCursor, TUIOControlPoint> tuioControlPoints = new HashMap<TuioCursor, TUIOControlPoint>();
	
	private float screenWidth;
	private float screenHeight;

	private MouseControlPoint mouseControlPoint;
	
	private AABB worldBounds = new AABB(new Vec2(-1920, -1600), new Vec2(1920, 1600));
	private World world;

	private KeyListener keyListener;
	
	public NUIController() {
		this(0, 0);
	}
	
	public NUIController(float x, float y) {
		super(x, y);
	}

	public static class SquirtPApplet extends PApplet {
		@Override
		public void setup() {
			size(screen.width, screen.height);
			getInstance().screenWidth = width;
			getInstance().screenHeight = height;
			getInstance().getTransformMatrix().translate(width/2, height/2);
			smooth();
			
			PFont f = createFont("Helvetica", 32);
			textFont(f);
			getInstance().font = f;
			getInstance().textAscent = textAscent();
			getInstance().textDescent = textDescent();
		}
		
		@Override
		public void draw() {
			getInstance().preRender(this);
			getInstance().render(this);
			getInstance().postRender(this);
		}
		
		@Override
		public void mousePressed() {
			getInstance().addMouseControlPoint(new PVector(mouseX, mouseY));
		}
		
		@Override
		public void mouseReleased() {
			getInstance().removeMouseControlPoint();
		}
		
		@Override
		public void mouseDragged() {
			getInstance().updateMouseControlPoint(new PVector(mouseX, mouseY));
		}
		
		@Override
		public void keyPressed() {
			KeyEvent e = new KeyEvent(getInstance());
			e.setCoded(key == CODED);
			e.setKey(key);
			e.setKeyCode(keyCode);
			getInstance().fireKeyPressed(e);
		}
		
		@Override
		public void keyReleased() {
			KeyEvent e = new KeyEvent(getInstance());
			e.setCoded(key == CODED);
			e.setKey(key);
			e.setKeyCode(keyCode);
			getInstance().fireKeyReleased(e);
		}
		
		@Override
		public void keyTyped() {
			KeyEvent e = new KeyEvent(getInstance());
			e.setCoded(key == CODED);
			e.setKey(key);
			e.setKeyCode(keyCode);
			getInstance().fireKeyTyped(e);
		}
	}
	
	public static NUIController getInstance() {
		if (n == null)
			n = new NUIController();
		return n;
	}
	
	public float getTextAscent() {
		return textAscent;
	}

	public float getTextDescent() {
		return textDescent;
	}

	public PFont getFont() {
		return font;
	}

	public void start() {
		PApplet.main(new String[]{ "--present", "nui.squirt.NUIController$SquirtPApplet" });
		tc = new TuioClient();
		tc.addTuioListener(this);
		tc.connect();
	}

	public void update() {
		if (hasPhysicsWorld()) {
			getWorld().step(1f/60f, 10);
		}
	}

	public boolean hasPhysicsWorld() {
		return getWorld() != null;
	}

	public World getWorld() {
		return world;
	}
	
	public void createPhysicsWorld() {
		this.world = new World(worldBounds, new Vec2(), true);
	}

	public void preRender(PApplet p) {
		super.preRender(p);
		
		p.background(255);
	}

	public void render(PApplet p) {
		if (hasPhysicsWorld()) {
			AABB bounds = getWorld().getWorldAABB();
			p.rectMode(PApplet.CORNERS);
			p.stroke(Color.RED.getRGB());
			p.fill(0, 0);
			p.rect(bounds.lowerBound.x, bounds.lowerBound.y, bounds.upperBound.x, bounds.upperBound.y);
		}
		List<Component> l = new ArrayList<Component>(getComponents());
		for (Component c: l) {
			c.preRender(p);
			c.render(p);
			c.postRender(p);
		}
		
		Collection<TUIOControlPoint> tcps = tuioControlPoints.values();
		for (TUIOControlPoint tcp: tcps) {
			p.ellipseMode(PApplet.CENTER);
			p.fill(tcp.isDead() ? p.color(255, 0, 0, 150) : p.color(150, 150));
			p.stroke(0);
			p.strokeWeight(1);

			
			PVector v = transformToLocalSpace(new PVector(tcp.getX(), tcp.getY()));
			p.ellipse(v.x, v.y, 30, 30);
			p.fill(0);
			p.text(tcp.getCursor().getCursorID(), v.x, v.y);
		}
	}

	public boolean canAcceptMoreControlPoints() {
		for (Component c: getComponents()) {
			if (c.canAcceptMoreControlPoints())
				return true;
		}
		return false;
	}
	
	public boolean isUnderPoint(ControlPoint cp) {
		return true;
	}

	public boolean offer(ControlPoint cp) {
		ListIterator<Component> i = getComponents().listIterator(getComponents().size());
		while (i.hasPrevious()) {
			Component c = i.previous();
			if (c.isUnderPoint(cp) && c.offer(cp)) {
				i.remove();
				getComponents().add(c);
				return true;
			}
		}
		return false;
	}

	public void addTuioCursor(TuioCursor c) {
		TUIOControlPoint tcp = new TUIOControlPoint(c, screenWidth, screenHeight);
		tc.addTuioListener(tcp);
		offer(tcp);
		tcp.fireControlPointCreatedEvent();
		
		tuioControlPoints.put(c, tcp);
	}

	public void removeTuioCursor(TuioCursor c) {
		tuioControlPoints.remove(c);
	}

	public void updateTuioCursor(TuioCursor c) {}

	public void addTuioObject(TuioObject o) {
		// TODO Auto-generated method stub
		
	}

	public void removeTuioObject(TuioObject o) {
		// TODO Auto-generated method stub
		
	}

	public void updateTuioObject(TuioObject o) {
		// TODO Auto-generated method stub
		
	}

	public void refresh(TuioTime t) {
		// TODO Auto-generated method stub
		
	}
	
	public void addMouseControlPoint(PVector initialPoint) {
		if (mouseControlPoint != null) {
			mouseControlPoint.kill();
		}
		mouseControlPoint = new MouseControlPoint(initialPoint);
		offer(mouseControlPoint);
		mouseControlPoint.fireControlPointCreatedEvent();
	}
	
	public void removeMouseControlPoint() {
		if (mouseControlPoint != null) {
			mouseControlPoint.kill();
		}
		mouseControlPoint = null;
	}
	
	public void updateMouseControlPoint(PVector newPoint) {
		if (mouseControlPoint != null) {
			mouseControlPoint.addToPath(newPoint);
		}
	}

	public void controlPointCreated(ControlPoint cp) {
		// TODO Auto-generated method stub
		
	}

	public void controlPointDied(ControlPoint cp) {
		// TODO Auto-generated method stub
		
	}

	public void controlPointUpdated(ControlPoint cp) {
		// TODO Auto-generated method stub
		
	}

	public void addKeyListener(KeyListener l) {
		if (keyListener != null) {
			removeKeyListener(keyListener);
		}
		keyListener = l;
		l.addKeyboard(this);
	}

	public void removeKeyListener(KeyListener l) {
		keyListener = null;
		l.removeKeyboard(this);
	}

	public void fireKeyPressed(KeyEvent e) {
		if (keyListener != null)
			keyListener.keyPressed(e);
	}

	public void fireKeyReleased(KeyEvent e) {
		if (keyListener != null)
			keyListener.keyReleased(e);
	}

	public void fireKeyTyped(KeyEvent e) {
		if (keyListener != null)
			keyListener.keyTyped(e);
	}

}
