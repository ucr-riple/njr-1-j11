package edu.concordia.dpis;

import java.net.UnknownHostException;
import java.util.List;

import edu.concordia.dpis.commons.Address;
import edu.concordia.dpis.stationserver.StationServerImpl;
import edu.concordia.dpis.stationserver.domain.StationType;

// Instantiates Replica 3 
public class Replica3Test {

	public static void main(String[] args) throws UnknownHostException {
		start();
	}

	public static void start() throws UnknownHostException {

		Address frontEndAddress = new Address("localhost", 2100);

		Address leaderAddress = new Address("localhost", 2200);
		ProxyNode leader = new ProxyNode(leaderAddress);
		leaderAddress.setId("100");

		Address replica2Address = new Address("localhost", 2300);
		ProxyNode replica2 = new ProxyNode(replica2Address);
		replica2Address.setId("75");

		Address replica4Address = new Address("localhost", 2500);
		ProxyNode replica4 = new ProxyNode(replica4Address);
		replica4Address.setId("25");

		Replica replica3 = new Replica(2400, 50, frontEndAddress)
				.addNode(leader).addNode(replica2).addNode(replica4);

		DefaultRequestHandler requestHandler = new DefaultRequestHandler();

		requestHandler.addCommand("isAlive", new Command() {

			@Override
			public Object execute(List<Object> params) {
				return true;
			}
		});

		StationServerImpl spvm = new StationServerImpl(StationType.SPVM);
		spvm.startUDPServer("4013");
		spvm.startTCPPServer("4014");
		StationServerImpl spb = new StationServerImpl(StationType.SPB);
		spb.startUDPServer("4015");
		spb.startTCPPServer("4016");
		StationServerImpl spl = new StationServerImpl(StationType.SPL);
		spl.startUDPServer("4017");
		spl.startTCPPServer("4018");

		spvm.addOtherUDPStationHostNPort(StationType.SPB, "localhost", "4015");
		spvm.addOtherTCPStationHostNPort(StationType.SPB, "localhost", "4016");
		spvm.addOtherUDPStationHostNPort(StationType.SPL, "localhost", "4017");
		spvm.addOtherTCPStationHostNPort(StationType.SPL, "localhost", "4018");

		spb.addOtherUDPStationHostNPort(StationType.SPVM, "localhost", "4013");
		spb.addOtherTCPStationHostNPort(StationType.SPVM, "localhost", "4014");
		spb.addOtherUDPStationHostNPort(StationType.SPL, "localhost", "4017");
		spb.addOtherTCPStationHostNPort(StationType.SPL, "localhost", "4018");

		spl.addOtherUDPStationHostNPort(StationType.SPVM, "localhost", "4013");
		spl.addOtherTCPStationHostNPort(StationType.SPVM, "localhost", "4014");
		spl.addOtherUDPStationHostNPort(StationType.SPB, "localhost", "4015");
		spl.addOtherTCPStationHostNPort(StationType.SPB, "localhost", "4016");

		requestHandler.addCommand("createCRecord", new CreateCriminalRecord(
				spvm, spb, spl));

		requestHandler.addCommand("createMRecord", new CreateMissingRecord(
				spvm, spb, spl));

		requestHandler.addCommand("getRecordCounts", new GetRecordCounts(spvm,
				spb, spl));

		requestHandler.addCommand("editRecord", new EditRecord(spvm, spb, spl));

		requestHandler.addCommand("transferRecord", new TransferRecord(spvm,
				spb, spl));

		replica3.setRequestHandler(requestHandler);
		replica3.start();
		replica3.startFailureDetection();
	}
}
