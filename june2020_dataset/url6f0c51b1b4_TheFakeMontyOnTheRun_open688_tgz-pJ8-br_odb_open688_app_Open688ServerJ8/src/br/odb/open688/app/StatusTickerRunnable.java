package br.odb.open688.app;

import br.odb.open688.app.Open688Server.SimulationStatus;

public class StatusTickerRunnable implements Runnable {

	private Open688Server context;

	public StatusTickerRunnable(Open688Server context) {
		this.context = context;
	}


	@Override
	public void run() {

		while ( context.isConnected() ) {
			
			try {
				Thread.sleep( 1000 );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if ( context.simulationStatus == SimulationStatus.PLAYING ) {
				
				context.update( 1000 );
			}
		}
	}
}
