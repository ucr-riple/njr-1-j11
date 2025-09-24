package rpg;

import rpg.creature.Hero;
import rpg.creature.Monster;
import rpg.item.BackPack;
import rpg.item.Dukat;
import rpg.item.Purse;
import rpg.item.Weapon;
import rpg.item.Weight;
import rpg.item.WeightUnit;

public class Main {

	
	public static void main(String[] args) {
		BackPack heroBP = new BackPack(new Weight(100,WeightUnit.G), new Weight(100,WeightUnit.KG), 10);
		Weapon heroWeapon = new Weapon(new Weight(1, WeightUnit.KG), 60);
		Purse heroPurse = new Purse(new Weight(11, WeightUnit.G), new Weight(3, WeightUnit.KG), 20);
		heroBP.addItem(heroPurse);
		
		Hero hero = new Hero("James", 103, heroBP);
		hero.getAnchor("rightHand").addItem(heroWeapon);
		System.out.println("Hero total value: " + hero.getTotalValue());
		
		BackPack emptyBP = new BackPack(new Weight(100,WeightUnit.G), new Weight(100,WeightUnit.KG), 3);
		Monster monster = new Monster("Destroyer o'Hope", 41, 50, 17, new Dukat(), new Dukat(), emptyBP);
		System.out.println("Monster total value: " + monster.getTotalValue());
		boolean monsterHitsNext = Math.random() >= 0.5;
		while(!hero.isTerminated() && !monster.isTerminated())
		{
			if(monsterHitsNext)
			{
				monster.hit(hero);
			}
			else
			{
				hero.hit(monster);
			}
			monsterHitsNext = !monsterHitsNext;
		}
		
		if(monster.isTerminated())
		{
			hero.collect(monster, monster.getTreasure());
			System.out.println("Hero: " + hero.getName() + " is the winner! And now has a total value of " + hero.getTotalValue());
		}
		else
			System.out.println("Monster " + monster.getName() + " is the winner! And now has a total value of " + monster.getTotalValue());
	}

}
