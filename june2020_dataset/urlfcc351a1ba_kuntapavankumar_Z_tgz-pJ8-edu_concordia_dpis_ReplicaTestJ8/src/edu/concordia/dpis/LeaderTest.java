package edu.concordia.dpis;

import java.net.UnknownHostException;
import java.util.List;

import edu.concordia.dpis.commons.Address;
import edu.concordia.dpis.stationserver.StationServerImpl;
import edu.concordia.dpis.stationserver.domain.StationType;

// Starts the leader process
public class LeaderTest {

	public static void main(String[] args) throws UnknownHostException {
		start();
	}

	public static void start() throws UnknownHostException {

		Address frontEndAddress = new Address("localhost", 2100);

		Address replica2Address = new Address("localhost", 2300);
		replica2Address.setId("75");
		ProxyNode replica2 = new ProxyNode(replica2Address);

		Address replica3Address = new Address("localhost", 2400);
		ProxyNode replica3 = new ProxyNode(replica3Address);
		replica3Address.setId("50");

		Address replica4Address = new Address("localhost", 2500);
		ProxyNode replica4 = new ProxyNode(replica4Address);
		replica4Address.setId("25");

		Replica leader = new Replica(2200, true, 100, frontEndAddress)
				.addNode(replica2).addNode(replica3).addNode(replica4);

		DefaultRequestHandler requestHandler = new DefaultRequestHandler();

		requestHandler.addCommand("isAlive", new Command() {

			@Override
			public Object execute(List<Object> params) {
				return true + "";
			}
		});

		StationServerImpl spvm = new StationServerImpl(StationType.SPVM);
		spvm.startUDPServer("4001");
		spvm.startTCPPServer("4002");

		StationServerImpl spb = new StationServerImpl(StationType.SPB);
		spb.startUDPServer("4003");
		spb.startTCPPServer("4004");

		StationServerImpl spl = new StationServerImpl(StationType.SPL);
		spl.startUDPServer("4005");
		spl.startTCPPServer("4006");

		spvm.addOtherUDPStationHostNPort(StationType.SPB, "localhost", "4003");
		spvm.addOtherTCPStationHostNPort(StationType.SPB, "localhost", "4004");
		spvm.addOtherUDPStationHostNPort(StationType.SPL, "localhost", "4005");
		spvm.addOtherTCPStationHostNPort(StationType.SPL, "localhost", "4006");

		spb.addOtherUDPStationHostNPort(StationType.SPVM, "localhost", "4001");
		spb.addOtherTCPStationHostNPort(StationType.SPVM, "localhost", "4002");
		spb.addOtherUDPStationHostNPort(StationType.SPL, "localhost", "4005");
		spb.addOtherTCPStationHostNPort(StationType.SPL, "localhost", "4006");

		spl.addOtherUDPStationHostNPort(StationType.SPVM, "localhost", "4001");
		spl.addOtherTCPStationHostNPort(StationType.SPVM, "localhost", "4002");
		spl.addOtherUDPStationHostNPort(StationType.SPB, "localhost", "4003");
		spl.addOtherTCPStationHostNPort(StationType.SPB, "localhost", "4004");

		requestHandler.addCommand("createCRecord", new CreateCriminalRecord(
				spvm, spb, spl));

		requestHandler.addCommand("createMRecord", new CreateMissingRecord(
				spvm, spb, spl));

		requestHandler.addCommand("getRecordCounts", new GetRecordCounts(spvm,
				spb, spl));

		requestHandler.addCommand("editRecord", new EditRecord(spvm, spb, spl));

		requestHandler.addCommand("transferRecord", new TransferRecord(spvm,
				spb, spl));

		leader.setRequestHandler(requestHandler);
		leader.start();
		leader.startFailureDetection();
	}
}
