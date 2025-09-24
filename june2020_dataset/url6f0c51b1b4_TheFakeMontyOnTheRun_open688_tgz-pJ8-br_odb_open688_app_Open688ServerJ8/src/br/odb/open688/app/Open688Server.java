package br.odb.open688.app;

import java.util.ArrayList;
import java.util.List;

import br.odb.gameapp.ConsoleApplication;
import br.odb.gameapp.UserCommandLineAction;
import br.odb.open688.app.commands.PauseCommand;
import br.odb.open688.app.commands.PlayCommand;
import br.odb.open688.app.commands.PumpCommand;
import br.odb.open688.app.commands.QuitCommand;
import br.odb.open688.app.commands.SteerCommand;
import br.odb.open688.app.commands.StepCommand;
import br.odb.open688.app.commands.ThrustCommand;
import br.odb.open688.app.commands.ToggleCommand;
import br.odb.open688.app.net.TelnetArmoryStation;
import br.odb.open688.app.net.TelnetCartographyStation;
import br.odb.open688.app.net.TelnetHealmStation;
import br.odb.open688.app.net.TelnetPropulsionStation;
import br.odb.open688.app.net.TelnetSonarStation;
import br.odb.open688.simulation.campaign.FuturisticScenario;
import br.odb.open688.simulation.campaign.Mission;
import br.odb.open688.simulation.campaign.Scenario;
import br.odb.open688.simulation.ship.Submarine;
import br.odb.utils.Utils;

public class Open688Server extends ConsoleApplication {

	enum SimulationStatus {
		STOPPED, PLAYING, PAUSED
	}

	private final List<Station> stations = new ArrayList<Station>();
	private Mission mission;
	private Thread tickerThread;

	SimulationStatus simulationStatus = SimulationStatus.STOPPED;

	public static void main(String[] args) {

		// Eventually, this part will be created according to graphical menus
		Scenario scenario = new FuturisticScenario();
		Mission mission = scenario.makeMission();

		// Create a dummy server.
		Open688Server openSubServer = (Open688Server) new Open688Server()
				.addStation(new TelnetSonarStation(1))
				.addStation(new TelnetCartographyStation(2))
				.addStation(new TelnetHealmStation(3))
				.addStation(new TelnetPropulsionStation(4))
				.addStation(new TelnetArmoryStation(5)).setScenario(scenario)
				.setAppName("Open688")
				.setAuthorName("Daniel 'MontyOnTheRun' Monteiro")
				.setLicenseName("3-Clause BSD").setReleaseYear(2015);

		openSubServer.createDefaultClient(); // just for the server
		openSubServer.startMission(mission);
	}

	public void update(long ms) {
		mission.update(ms);
	}

	private void startMission(Mission mission) {

		this.mission = mission;

		start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		pause();
		startStatusTicker();

		for (Station s : stations) {
			s.setMasterClient(this);
		}
	}

	private void startStatusTicker() {
		this.tickerThread = new Thread(new StatusTickerRunnable(this));
		this.tickerThread.start();
	}

	@Override
	public ConsoleApplication init() {

		registerCommand(new StatusCommand( this ));
		registerCommand(new QuitCommand( this ));
		registerCommand(new PlayCommand( this ));
		registerCommand(new PauseCommand( this ));
		registerCommand(new StepCommand( this ));
		registerCommand(new SteerCommand());
		registerCommand(new ThrustCommand());
		registerCommand(new ToggleCommand());
		registerCommand(new PumpCommand());

		return super.init();
	}

	private Open688Server setScenario(Scenario scenario) {
		return this;
	}

	private Open688Server addStation(Station station) {

		this.stations.add(station);

		return this;
	}

	public String getStatus() {

		Submarine submarine = (Submarine) getMission().getPlayerCapitalShip();

		return "\ncurrent status\n==============\n" + submarine.toString()
				+ "\n* simulation time:"
				+ getMission().getSimulationEllapsedTime() + "s\n\t";
	}

	@Override
	public void onDataEntered(String entry) {

		if (entry == null || entry.length() == 0) {
			return;
		}

		super.onDataEntered(entry);

		String[] tokens = Utils.tokenize(entry.trim(), " ");
		String operator = tokens[0];
		String operand = entry.replace(operator, "").trim();

		
		UserCommandLineAction cmd = getCommand( tokens[ 0 ] );

		if (cmd != null) {
			try {
				cmd.run(this, operand);
			} catch ( InvalidShipPart isp ) {
				getClient().alert( "Invalid ship part!" );
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void log(String tag, String string) {
	}

	@Override
	protected void doQuit() {
		continueRunning = false;
	}

	public boolean isConnected() {
		return this.continueRunning;
	}

	public Mission getMission() {
		return mission;
	}

	public void pause() {
		simulationStatus = SimulationStatus.PAUSED;
	}

	public void play() {
		simulationStatus = SimulationStatus.PLAYING;
	}
}
