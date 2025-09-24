package edu.concordia.dpis;

import java.net.UnknownHostException;
import java.util.List;

import edu.concordia.dpis.commons.Address;
import edu.concordia.dpis.stationserver.StationServerImpl;
import edu.concordia.dpis.stationserver.domain.StationType;

// Instantiates Replica 2 
public class Replica2Test {

	public static void main(String[] args) throws UnknownHostException {
		start();
	}

	public static void start() throws UnknownHostException {

		Address frontEndAddress = new Address("localhost", 2100);

		Address leaderAddress = new Address("localhost", 2200);
		ProxyNode leader = new ProxyNode(leaderAddress);
		leaderAddress.setId("100");

		Address replica3Address = new Address("localhost", 2400);
		ProxyNode replica3 = new ProxyNode(replica3Address);
		replica3Address.setId("50");

		Address replica4Address = new Address("localhost", 2500);
		ProxyNode replica4 = new ProxyNode(replica4Address);
		replica4Address.setId("25");

		Replica replica2 = new Replica(2300, 75, frontEndAddress)
				.addNode(leader).addNode(replica3).addNode(replica4);

		DefaultRequestHandler requestHandler = new DefaultRequestHandler();

		requestHandler.addCommand("isAlive", new Command() {

			@Override
			public Object execute(List<Object> params) {
				return true;
			}
		});

		StationServerImpl spvm = new StationServerImpl(StationType.SPVM);
		spvm.startUDPServer("4007");
		spvm.startTCPPServer("4008");

		StationServerImpl spb = new StationServerImpl(StationType.SPB);
		spb.startUDPServer("4009");
		spb.startTCPPServer("4010");

		StationServerImpl spl = new StationServerImpl(StationType.SPL);
		spl.startUDPServer("4011");
		spl.startTCPPServer("4012");

		spvm.addOtherUDPStationHostNPort(StationType.SPB, "localhost", "4009");
		spvm.addOtherTCPStationHostNPort(StationType.SPB, "localhost", "4010");
		spvm.addOtherUDPStationHostNPort(StationType.SPL, "localhost", "4011");
		spvm.addOtherTCPStationHostNPort(StationType.SPL, "localhost", "4012");

		spb.addOtherUDPStationHostNPort(StationType.SPVM, "localhost", "4007");
		spb.addOtherTCPStationHostNPort(StationType.SPVM, "localhost", "4008");
		spb.addOtherUDPStationHostNPort(StationType.SPL, "localhost", "4011");
		spb.addOtherTCPStationHostNPort(StationType.SPL, "localhost", "4012");

		spl.addOtherUDPStationHostNPort(StationType.SPVM, "localhost", "4007");
		spl.addOtherTCPStationHostNPort(StationType.SPVM, "localhost", "4008");
		spl.addOtherUDPStationHostNPort(StationType.SPB, "localhost", "4009");
		spl.addOtherTCPStationHostNPort(StationType.SPB, "localhost", "4010");

		requestHandler.addCommand("createCRecord", new CreateCriminalRecord(
				spvm, spb, spl));

		requestHandler.addCommand("createMRecord", new CreateMissingRecord(
				spvm, spb, spl));

		requestHandler.addCommand("getRecordCounts", new GetRecordCounts(spvm,
				spb, spl));

		requestHandler.addCommand("editRecord", new EditRecord(spvm, spb, spl));

		requestHandler.addCommand("transferRecord", new TransferRecord(spvm,
				spb, spl));
		replica2.setRequestHandler(requestHandler);
		replica2.start();
		replica2.startFailureDetection();
	}
}
