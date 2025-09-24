package patterns.gof.structural.proxy;

import patterns.gof.helpers.Client;

public class ProxyClient extends Client {
	@Override
	public void main() {
		cleanOutput();
		
		MathProxy p = new MathProxy();

		addOutput("3 + 1 = " + p.add(3, 1));
		addOutput("14 - 21 = " + p.sub(14, 21));
		addOutput("4 * 7 = " + p.mul(4, 7));
		addOutput("6 / 0 = " + p.div(6, 0));
		addOutput("10 / 3 = " + p.div(10, 3));
		
		super.main("Proxy");
	}
}