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
package nui.squirt.demo;

import java.awt.Color;

import nui.squirt.NUIController;
import nui.squirt.component.Button;
import nui.squirt.component.Circle;
import nui.squirt.component.Frame;
import nui.squirt.component.Image;
import nui.squirt.component.Knob;
import nui.squirt.component.Label;
import nui.squirt.component.PhysicsCircle;
import nui.squirt.component.PhysicsRectangle;
import nui.squirt.component.Rectangle;
import nui.squirt.component.Slider;
import nui.squirt.component.TextField;
import nui.squirt.event.ActionEvent;
import nui.squirt.event.ValueEvent;
import nui.squirt.listener.ActionListener;
import nui.squirt.listener.ValueListener;

public class DemoSquirtApplication {
	
	private static class SpinningRectangle extends Rectangle {
		public SpinningRectangle(float x, float y, float w, float h) {
			super(x, y, w, h);
		}
		@Override
		public void update() {
			super.update();
			
			getTransformMatrix().rotate(0.01F);
		}	
	}
	
	private static class SpinningScalingImage extends Image {
		
		private float counter = (float) 0.1;

		public SpinningScalingImage(float x, float y, float width, float height, String path) {
			super(x, y, width, height, path);
		}
		
		public SpinningScalingImage(float x, float y, String path) {
			super(x, y, path);
		}
		
		@Override
		public void update() {
			super.update();
			
			getTransformMatrix().rotate(-0.01F);
//			getTransformMatrix().scale(Math.sin(counter), Math.sin(counter));
//			setScale((float) Math.sin(counter));
			counter += 0.01;
		}
	}
	
	public static class SlidingSlider extends Slider {
		public SlidingSlider(float x, float y, float l, float minValue, float maxValue) {
			super(x, y, l, minValue, maxValue);
		}

		@Override
		public void update() {
			super.update();
			setValue(getValue()+0.1F);
		}
	}
	
	public static class ValueLabel extends Label implements ValueListener {
		
		public ValueLabel(float x, float y, String t) {
			super(x, y, t);
		}
	
		public void valueChanged(ValueEvent e) {
			super.setText(Float.toString(e.getNewValue()));
		}
		
		@Override
		public void setText(String t) {
			// Empty. We don't want anything external to change the text of this Label.
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NUIController n = NUIController.getInstance();
		
		Image i = new SpinningScalingImage(0, 0, "squirtUI.png");
		n.add(i);
		
		Rectangle r = new Rectangle(0, 0, 300, 600);
		Rectangle r0 = new Rectangle(0, 0, 100, 150);
		r0.setFillColor(new Color(128, 64, 32, 128));
		r0.getTransformMatrix().rotate(Math.PI/4, 0, 0);
		Rectangle r1 = new SpinningRectangle(-15, -100, 50, 60);
		r1.setFillColor(new Color(255, 0, 0, 150));
		n.add(r);
		n.add(r0);
		n.add(r1);
		
		Circle c = new Circle(150, -60, 150);
		c.setFillColor(new Color(0, 0, 255, 128));
		c.setStrokeColor(Color.YELLOW);
		c.setStrokeWeight(2);
		n.add(c);
		
		Frame buttonFrame = new Frame(-150, 170, 400, 250);
		Label l = new Label(0, -75, "button in frame");
		Button b = new Button(0, 50, "button");
		b.addActionListener(new ActionListener() {
			private int count = 0;
			public void actionPerformed(ActionEvent e) {
				((Button) e.getSource()).setText("pressed " + (++count) + " times");
			}
		});
		buttonFrame.add(l);
		buttonFrame.add(b);
		n.add(buttonFrame);
		
		Knob k = new Knob(450, 0, 128, -200, 1300, ((float) -Math.PI/2), ((float) Math.PI*3/4));
		ValueLabel knobLabel = new ValueLabel(450, 150, Float.toString(k.getValue()));
		k.addValueListener(knobLabel);
		n.add(k);
		n.add(knobLabel);
		
		Slider s = new Slider(0, 350, 813, 0, 1);
		s.getTransformMatrix().rotate(Math.PI/2);
		ValueLabel sliderLabel = new ValueLabel(0, 360, Float.toString(s.getValue()));
		s.addValueListener(sliderLabel);
		n.add(s);
		n.add(sliderLabel);
		
		if (!n.hasPhysicsWorld()) n.createPhysicsWorld();
		PhysicsRectangle p = new PhysicsRectangle(100, 200, 150, 150, n.getWorld());
		PhysicsCircle p1 = new PhysicsCircle(-100, -200, 75, n.getWorld());
		PhysicsRectangle p2 = new PhysicsRectangle(200, 300, 100, 190, n.getWorld());
		PhysicsCircle p3 = new PhysicsCircle(-400, 200, 100, n.getWorld());
		PhysicsRectangle p4 = new PhysicsRectangle(100, -200, 150, 150, n.getWorld());
		PhysicsRectangle p5 = new PhysicsRectangle(-100, 200, 150, 150, n.getWorld());
		n.add(p);
		n.add(p1);
		n.add(p2);
		n.add(p3);
		n.add(p4);
		n.add(p5);
		
		Label sqUIrt = new Label(0, 0, "sqUIrt");
		sqUIrt.getTransformMatrix().scale(2.5, 2.5);
		n.add(sqUIrt);
		
		TextField f = new TextField(200, -200, 200, "text field");
		TextField f1 = new TextField(-200, 200, 200, "text field 2");
		n.add(f);
		n.add(f1);
		
		n.start();
	}

}
