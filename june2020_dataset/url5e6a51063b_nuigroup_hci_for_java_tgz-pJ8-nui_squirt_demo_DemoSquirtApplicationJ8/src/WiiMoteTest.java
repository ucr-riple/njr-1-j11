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
import java.util.ArrayList;

import motej.Extension;
import motej.Mote;
import motej.demos.common.SimpleMoteFinder;
import motej.event.CoreButtonEvent;
import motej.event.CoreButtonListener;
import motej.event.ExtensionEvent;
import motej.event.ExtensionListener;
import motejx.extensions.nunchuk.AnalogStickEvent;
import motejx.extensions.nunchuk.AnalogStickListener;
import motejx.extensions.nunchuk.Nunchuk;
import motejx.extensions.nunchuk.NunchukCalibrationData;
import processing.core.PApplet;
import processing.core.PVector;


@SuppressWarnings("serial")
public class WiiMoteTest extends PApplet {
	
	private Mote m;
	private Nunchuk n;
	private CoreButtonEvent lastButtonEvent;
	
	private ParticleSystem p;
	
	private PVector point;
	private NunchukCalibrationData cali;
	private float nunX;
	private float nunY;

	public static void main(String[] args) {
		PApplet.main(new String[]{ "WiiMoteTest" });
	}
	
	@Override
	public void setup() {
		size(400, 400, P3D);
		smooth();
		
		point = new PVector();
		
		SimpleMoteFinder f = new SimpleMoteFinder();
		m = f.findMote();
		m.addCoreButtonListener(new CoreButtonListener() {

			public void buttonPressed(CoreButtonEvent arg0) {
				lastButtonEvent = arg0;
				
				if (arg0.isButtonHomePressed()) exit();
			}
			
		});
		
		m.addExtensionListener(new ExtensionListener() {

			public void extensionConnected(ExtensionEvent evt) {
				Extension e = evt.getExtension();
				if (e instanceof Nunchuk) {
					n = (Nunchuk) e;
					System.out.println(n);
//					Thread t = new Thread() {
//						@Override
//						public void run() {
//							while (n.getCalibrationData() == null) {
//								try {
//									Thread.sleep(10);
//								} catch (InterruptedException e1) {
//									e1.printStackTrace();
//								}
//							}
//							cali = n.getCalibrationData();
//						}
//					};
//					t.start();
					
//					n.addAnalogStickListener(new AnalogStickListener() {
//
//						public void analogStickChanged(AnalogStickEvent arg0) {
//							nunX = arg0.getPoint().x;
//							nunY = arg0.getPoint().y;
//							System.out.println(nunX + " " + nunY);
//						}
//						
//					});
				}
			}

			public void extensionDisconnected(ExtensionEvent arg0) {
				n = null;
				System.out.println("Extension Disconnected!!!");
			}
			
		});
		
		p = new ParticleSystem(0, new PVector(0, 0));
		
	}
	
	@Override
	public void draw() {
		background(0);
		
		fill(255);
		point(point.x, point.y);
		
		if (n != null) {
//			point.add(nunX, nunY, 0);
//			point.sub(cali.getCenterAnalogPoint().x, cali.getCenterAnalogPoint().y, 0);
		}
		
		if (lastButtonEvent != null) {
			if (lastButtonEvent.isDPadDownPressed() && point.y < height) {
				point.add(new PVector(0, 5));
			}
			if (lastButtonEvent.isDPadUpPressed() && point.y > 0) {
				point.add(new PVector(0, -5));
			}
			if (lastButtonEvent.isDPadRightPressed() && point.x < width) {
				point.add(new PVector(5, 0));
			}
			if (lastButtonEvent.isDPadLeftPressed() && point.x > 0) {
				point.add(new PVector(-5, 0));
			}
			if (lastButtonEvent.isButtonAPressed()) {
				p.addParticle(new Particle(new PVector(point.x, point.y), color(255, 0, 0)));
			}
			if (lastButtonEvent.isButtonBPressed()) {
				p.addParticle(new Particle(new PVector(point.x, point.y), color(0, 255, 0)));
			}
		}

		p.run();
	}
	
	// A class to describe a group of Particles
	// An ArrayList is used to manage the list of Particles 

	private class ParticleSystem {

	  private ArrayList<Particle> particles;    // An arraylist for all the particles
	  private PVector origin;        // An origin point for where particles are born

	  public ParticleSystem(int num, PVector v) {
	    particles = new ArrayList<Particle>();              // Initialize the arraylist
	    origin = v.get();                        // Store the origin point
	    for (int i = 0; i < num; i++) {
	      particles.add(new Particle(origin, color(100)));    // Add "num" amount of particles to the arraylist
	    }
	  }

	  public void run() {
	    // Cycle through the ArrayList backwards b/c we are deleting
	    for (int i = particles.size()-1; i >= 0; i--) {
	      Particle p = (Particle) particles.get(i);
	      p.run();
	      if (p.dead()) {
	        particles.remove(i);
	      }
	    }
	  }

	  public void addParticle() {
	    particles.add(new Particle(origin, color(100)));
	  }
	  
	  public void addParticle(float x, float y) {
	    particles.add(new Particle(new PVector(x,y), color(100)));
	  }

	  public void addParticle(Particle p) {
	    particles.add(p);
	  }

	  // A method to test if the particle system still has particles
	  public boolean dead() {
	    if (particles.isEmpty()) {
	      return true;
	    } else {
	      return false;
	    }
	  }

	}
	
	// A simple Particle class

	private class Particle {
	  private PVector loc;
	  private PVector vel;
	  private PVector acc;
	  private float r;
	  private float timer;
	  private int color;
	  
	  // Another constructor (the one we are using here)
	  public Particle(PVector l, int color) {
	    acc = new PVector(0,(float) 0.05,0);
	    vel = new PVector(random(-1,1),random(-2,0),0);
	    loc = l.get();
	    r = (float) 10.0;
	    timer = (float) 100.0;
	    this.color = color;
	  }

	  public void run() {
	    update();
	    render();
	  }

	  // Method to update location
	  public void update() {
	    vel.add(acc);
	    loc.add(vel);
	    timer -= 1.0;
	  }

	  // Method to display
	  public void render() {
	    ellipseMode(CENTER);
	    stroke(255,timer);
	    fill(color,timer);
	    ellipse(loc.x,loc.y,r,r);
	    displayVector(vel,loc.x,loc.y,10);
	  }
	  
	  // Is the particle still useful?
	  public boolean dead() {
	    if (timer <= 0.0) {
	      return true;
	    } else {
	      return false;
	    }
	  }
	  
	   public void displayVector(PVector v, float x, float y, float scayl) {
	    pushMatrix();
	    float arrowsize = 4;
	    // Translate to location to render vector
	    translate(x,y);
	    // Call vector heading function to get direction (note that pointing up is a heading of 0) and rotate
	    rotate(v.heading2D());
	    // Calculate length of vector & scale it to be bigger or smaller if necessary
	    float len = v.mag()*scayl;
	    // Draw three lines to make an arrow (draw pointing up since we've rotate to the proper direction)
	    line(0,0,len,0);
	    line(len,0,len-arrowsize,+arrowsize/2);
	    line(len,0,len-arrowsize,-arrowsize/2);
	    popMatrix();
	  } 
	}
}
