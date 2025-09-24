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
import java.awt.geom.AffineTransform;
import java.util.HashMap;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.MouseJoint;
import org.jbox2d.dynamics.joints.MouseJointDef;

import nui.squirt.ControlPoint;
import processing.core.PApplet;
import processing.core.PVector;

public abstract class PhysicsComponent extends AbstractComponent {

	private World world;
	private Body body;
	
	private HashMap<ControlPoint, MouseJoint> joints = new HashMap<ControlPoint, MouseJoint>();
	
	public PhysicsComponent(float x, float y, World world) {
		this.world = world;
		
		// Create physics body
		BodyDef bodyDef = new BodyDef();
		bodyDef.position = new Vec2(x, y);
		bodyDef.linearDamping = 0.9f;
		bodyDef.angularDamping = 0.99f;
		this.body = getWorld().createBody(bodyDef);
	}

	public World getWorld() {
		return world;
	}

	public Body getBody() {
		return body;
	}

	public void render(PApplet p) {
		for (MouseJoint j: joints.values()) {
			p.stroke(Color.RED.getRGB());
			Vec2 localAnchor = j.m_localAnchor;
			Vec2 target = getBody().getLocalPoint(j.m_target);
			p.line(localAnchor.x, localAnchor.y, target.x, target.y);
		}
	}

	public boolean canAcceptMoreControlPoints() {
		return true;
	}

	public void controlPointCreated(ControlPoint cp) {
		PVector local = transformToLocalSpace(new PVector(cp.getX(), cp.getY()));
		Vec2 pos = getBody().getWorldPoint(new Vec2(local.x, local.y));
		MouseJointDef mouseJointDef = new MouseJointDef();
        mouseJointDef.body1 = getWorld().getGroundBody();
        mouseJointDef.body2 = getBody();
        mouseJointDef.target.set(pos);
        mouseJointDef.maxForce = 100000.0f * getBody().m_mass;
        joints.put(cp, (MouseJoint) getWorld().createJoint(mouseJointDef));
        getBody().wakeUp();
	}

	public void controlPointDied(ControlPoint cp) {
		MouseJoint j = joints.get(cp);
		if (j != null) {
			getWorld().destroyJoint(j);
			joints.remove(cp);
		}
	}

	public void controlPointUpdated(ControlPoint cp) {
		MouseJoint j = joints.get(cp);
		if (j != null) {
			PVector local = transformToLocalSpace(new PVector(cp.getX(), cp.getY()));
			Vec2 pos = getBody().getWorldPoint(new Vec2(local.x, local.y));
			j.setTarget(pos);
		}
	}
	
	@Override
	public AffineTransform getTransformMatrix() {
		Vec2 pos = getBody().getPosition();
		Float angle = getBody().getAngle();

		AffineTransform transform = new AffineTransform();
		transform.translate(pos.x, pos.y);
		transform.rotate(angle);
		return transform;
	}

}
