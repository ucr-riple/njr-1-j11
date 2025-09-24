package yeah.cstriker1407.design_patterns.bridge;

import yeah.cstriker1407.design_patterns.bridge.abstraction.HouseFactory;
import yeah.cstriker1407.design_patterns.bridge.abstraction.SanzaiFactory;
import yeah.cstriker1407.design_patterns.bridge.implementor.House;
import yeah.cstriker1407.design_patterns.bridge.implementor.Mp3;

public class BridgeTest
{
	public static void test()
	{
		House house = new House();
		HouseFactory houseFactory = new HouseFactory(house);
		houseFactory.makeMoney();
		
		Mp3 mp3 = new Mp3();
		SanzaiFactory sanzaiFactory = new SanzaiFactory(mp3);
		sanzaiFactory.makeMoney();
	}
}
