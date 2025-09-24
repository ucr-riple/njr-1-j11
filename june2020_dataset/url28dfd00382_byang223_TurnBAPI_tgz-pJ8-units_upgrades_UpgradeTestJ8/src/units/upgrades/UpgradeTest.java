package units.upgrades;

import map.LevelMap;
import player.Player;
import units.PikachuUnit;
import units.Unit;
import achiever.upgrades.AchieverAddUnitModification;
import achiever.upgrades.AchieverUpgradable;
import ai.HumanPlayer;
import attribute.AttributeHealth;
import attribute.AttributeUpgrades;

/**
 * Place to test my design
 * @author Alex Mariakakis
 *
 */
public class UpgradeTest {

    public static void main(String[] args) {
    	// Create a player
    	Player player1 = new HumanPlayer("Player 1");
		player1.getAttribute("Experience").setData(60);

        // Create a unit
    	Unit oldUnit = new PikachuUnit();
    	AchieverUpgradable addUnit = new AchieverAddUnitModification(player1, oldUnit);
    	addUnit.modify();
        System.out.println("HP before: " + ((AttributeHealth) oldUnit.getAttribute("Health")).getHP());
        System.out.println("Experience before: " + player1.getAttribute("Experience").getData());
        
    	// Create an upgrade tree and give it to the unit
    	AttributeUpgrades tree = (AttributeUpgrades) oldUnit.getAttribute("Upgrades");
    	UpgradeTree myTree = tree.getMyTree();
    	myTree.addLeaf(myTree.getRoot(), new UpgradeNode(new HealthModification(oldUnit, 20, 5)));
    	
    	// Decorate the unit with better health
        UpgradeNode node1 = new UpgradeNode(new HealthModification(oldUnit, 20, 5));
        node1.setAvailable(true);
        node1.modify();
        System.out.println("HP after upgrade: " + ((AttributeHealth) oldUnit.getAttribute("Health")).getHP());
        System.out.println("Experience after upgrade: " + player1.getAttribute("Experience").getData());
        System.out.println("");
        
        // Decorate the map
        LevelMap myMap = new LevelMap(10);
        myMap.getTileByCoords(2, 2).setUnit(oldUnit);
        System.out.println("Unit in old location: " + myMap.getTileByCoords(2, 2).getUnit());
        System.out.println("Unit in new location: " + myMap.getTileByCoords(2, 3).getUnit());
        UpgradeNode node2 = new UpgradeNode(new TeleportModification(oldUnit, 20, myMap, 2, 3));
        node2.setAvailable(true);
        node2.modify();
        System.out.println("Unit in old location: " + myMap.getTileByCoords(2, 2).getUnit());
        System.out.println("Unit in new location: " + myMap.getTileByCoords(2, 3).getUnit());
        System.out.println("");
        
        // Decorate the image
        System.out.println(oldUnit.getImage());
        UpgradeNode node3 = new UpgradeNode(new ImageModification(oldUnit, 20, "resources/unit images/Bulbasaur.png"));
        node3.setAvailable(true);
        node3.modify();
        System.out.println(oldUnit.getImage());
        
    }
}
