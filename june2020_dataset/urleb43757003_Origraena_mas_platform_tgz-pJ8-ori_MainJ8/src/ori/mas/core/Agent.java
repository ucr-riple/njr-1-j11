package ori.mas.core;

import ori.ogapi.report.Reportable;
import ori.ogapi.report.Reporter;

public class Agent implements Reportable {

	public Agent() {
		_mind = null;
		_body = null;
	}

	public Agent(Body body) {
		_mind = null;
		_body = body;
		if (_body != null)
			_body.setAgent(this);
	}

	public Agent(Mind mind) {
		_mind = mind;
		_body = null;
		if (_mind != null)
			_mind.setAgent(this);
	}

	public Agent(Mind mind, Body body) {
		_mind = mind;
		_body = body;
		if (_mind != null)
			_mind.setAgent(this);
		if (_body != null)
			_body.setAgent(this);
	}

	public boolean hasMind() {
		return (_mind != null);
	}

	public Mind mind() {
		return _mind;
	}

	public void setMind(Mind mind) {
		_mind = mind;
		_mind.setAgent(this);
	}

	public boolean hasBody() {
		return (_body != null);
	}

	public Body body() {
		return _body;
	}

	public void setBody(Body body) {
		_body = body;
		_body.setAgent(this);
	}

	public Influence tick(World w) {
		//System.out.println("sense");
		this.sense(w);
		//System.out.println("think");
		this.think();
		//System.out.println("act");
		return this.act();
	}

	public void sense(World w) {
		if ((this.hasBody()) && (this.hasMind()))
			_mind.percept(_body.sense(w));
	}

	public void think() {
		if ((this.hasMind()) && (this.hasBody()))
			_body.prepareActor(_mind.nextActor());
	}

	public Influence act() {
		if (this.hasBody())
			return _body.act();
		return null;
	}

	/** {@inheritDoc} */
	public void reportIn(Reporter out) {
		out.newSection(this.hashCode()+"@Agent{\n");
		out.incSection();
//		out.newSection("id: "+this.id);
		out.newSection();
		out.report(this.mind());
		out.newSection();
		out.report(this.body());
		out.decSection();
		out.newLine();
		out.report("}");
	}

	@Override
	public Agent clone() {
		Body b = null;
		Mind m = null;
		if (this.hasBody())
			b = _body.clone();
		if (this.hasMind())
			m = _mind.clone();
		return new Agent(m,b);
	}

//	public void tell(Message m) {
//		_tong.tell(m);
//	}
// TODO
//	public void shout(Message m) {
//		_body.shout(m);
//		body.tong().shout
//	}

	private Body _body;
	private Mind _mind;

};

