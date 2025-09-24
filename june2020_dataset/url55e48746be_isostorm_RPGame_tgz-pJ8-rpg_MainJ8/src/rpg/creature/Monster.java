package rpg.creature;

import java.util.ArrayList;
import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

import rpg.item.Container;
import rpg.item.Dukat;
import rpg.item.Item;
import rpg.item.Purse;
import rpg.item.Weight;
import rpg.item.WeightUnit;
/**
 * A monster is a special type of creature with additionally damage.
 * 
 * @author Mathias, Frederic
 * 
 * @invar The damage of each monster is a valid weapon damage.
 *        | Weapon.isValidDamage(damage)
 * @invar The protection of each monster is positive.
 *        | getProtection() >= 0
 */
public class Monster extends Creature {

	/**
	 * Initializes a new monster with the given name, maximum hitpoints,
	 * protection and a variable amount of objects.
	 * 
	 * @param  name
	 *         The name of this monster.
	 * @param  maximumHitpoints
	 *         The maximum hitpoints of this monster.
	 * @param  damage
	 *         The natural damage of this monster.
	 * @param  protection
	 *         The protection factor of this monster.
	 * @param  items
	 *         The items this monster carries.
	 * @pre    The given damage must be a valid damage for a weapon.
	 *         | Weapon.isValidDamage(damage)
	 * @pre    The given protection must be positive.
	 *         | protection >= 0
	 * @post   Each anchor of this monster contains one of the given objects.
	 *         | for each item in items:
	 *         |    for some anchor in getAnchors():
	 *         |       anchor.containsDirectItem(item)
	 * @post   The protection of this monster is equal to the given protection.
	 *         | getProtection() == protection
	 * @post   The damage of this monster is equal to the given damage.
	 *         | getDamage() == damage
	 * @effect This monster is initialized as a new creature with the given 
	 *         name and maximumHitpoints.
	 *         | super(name, maximumhitpoints)
	 * @effect   The strength of this monster is set to a strength capable of carrying all the given objects.
	 *         | setStrength( getTotalWeight().getNumeral()/9 + 1 )
	 */
	@Raw
	public Monster(String name, int maximumHitpoints, int damage, int protection, Item ... items) {
		super(name, maximumHitpoints);
		Weight retWeight = new Weight(0, WeightUnit.KG);
		for(Item item: items)
		{
			if(item instanceof Container)
				retWeight.add( ((Container)item).getTotalWeight() );
			retWeight.add(item.getWeight());
		}
		
		// plus one to make sure the capacity is big enough in case of loss of precision
		setStrength(retWeight.getNumeral()/9 + 1); 
		
		for(Item item: items)
			new Anchor(this, item);
		
		this.protection = protection;
		this.damage = damage;
	}
	
	private final int protection;
	private final int damage;
	
	private static final ArrayList<Character> allowedCharacters = new ArrayList<Character>();
	
	/**
	 * Adds the given character to the list of allowed characters.
	 * 
	 * @param character
	 *        The character to add to the list of allowed characters.
	 */
	public static void addCharacter(char character)
	{
		allowedCharacters.add(character);
	}
	
	/**
	 * Checks whether this hero can have the given name as its name.
	 * @return False if the given name is not effective.
	 *         | if ( name == null ) then
	 *         |    result == false
	 * @return False if the name doesn't start with a capital letter
	 *         or doesn't only contains upper and lower case alphabetical
	 *         characters, spaces or apostrophes after removing all the 
	 *         allowed characters from the name.
	 *         | for each c in allowedCharacters:
	 *		   |    name = name.replace(c.toString(), "")
	 *         | if(!name.matches("[A-Z][A-Za-z ']*")) then
	 *         |    result == false
	 */
	@Override @Raw
	public boolean canHaveAsName(String name) {
		if(!super.canHaveAsName(name))
			return false;
		for(Character c : allowedCharacters)
			name = name.replace(c.toString(), "");
		if(!name.matches("[A-Z][A-Za-z ']*"))
			return false;
		return true;
	}

	/**
	 * Returns the capacity of this monster.
	 * 
	 * @return The product of a weight of 9KG multiplied with the strength of this monster.
	 *         | result == ( ( new Weight(9, WeightUnit.KG) ).multiply( getStrength() ) )
	 */
	@Override
	public Weight getCapacity() {
		Weight capacity = new Weight(9, WeightUnit.KG);
		return capacity.multiply(getStrength());
	}
	
	/**
	 * Returns the protection of of this monster.
	 */
	@Basic @Raw @Override @Immutable
	public int getProtection()
	{
		return this.protection;
	}
	/**
	 * Returns the damage of this monster.
	 */
	@Basic @Immutable @Raw
	public int getDamage()
	{
		return damage;
	}
	/**
	 * Attempts a hit on another creature.
	 * @effect If the other creature is not this creature,
	 *         a random number is generated between 0 and 100 and if
	 *         this random number is greater than the hitpoints of this
	 *         creature, number is equal to the hitpoints of this creature,
	 *         otherwise number is equal to the previous random number.
	 *         | if( other != this ) then
	 *         |    let
	 *         |       randomNumber = new Random().nextInt(101)
	 *         |       number = (randomNumber >= getHitpoints()) ? getHitpoints() : randomNumber
	 *         |    in
	 *         If number is more than or equal
	 *         to the protection of the other creature and the sum of total strength minus 5,
	 *         divided by 3 and the damage of this creature is more than 0
	 *         and the result of weakening the other creature
	 *         with a damage equal to the total strength minus 5, divided by 3 is true then
	 *         this monster will collect random items from the other dead creature.
	 *         |       if(randomNumber >= other.getProtection())
	 *         |          && ((getStrength()-5)/3 > 0)
	 *         |          && (other.weaken((getStrength()-5)/3) then
	 *         |                collect(other)
	 */
	public void hit(Creature other)
	{
		if(other == this)
			return;
		int randomNumber = new Random().nextInt(101);
		int number = (randomNumber >= getHitpoints()) ? getHitpoints() : randomNumber;
		if(number >= other.getProtection())
		{
			int damage = getDamage() + (int)(getStrength()-5)/3;
			if(damage > 0)
			{
				boolean otherIsDead = other.weaken(damage);
				if(otherIsDead)
				{
					collect(other);
				}
			}
		}
	}
	/**
	 * Randomly collects items from the other creature with a higher
	 * chance of retrieving dukats and purses.
	 * 
	 * @param  other
	 * 		   The creature to collect the treasure from
	 * @effect Each item in the treasure list is collected by this monster
	 *         if and only if the generated random number is smaller than 0.5 if the
	 *         item is a purse or a dukat, otherwise the random number must be smaller than 0.3.
	 *         The item is swapped with an existing item in an anchor if another generated random
	 *         number is less than or equal to 0.2 and there is atleast one anchor.
	 *         Otherwise the item is collected and added to an existing anchor if there 
	 *         is an anchor with no item.
	 *         | let
	 *         |    toCollect = new ArrayList<Item>()
	 *         | in
	 *         |    for each item in other.getTreasure():
	 *         |       let
	 *         |          chance = 0.3
	 *         |       in
	 *         |          if( item instanceof Dukat || item instanceof Purse) then
	 *         |             chance = 0.5
	 *         |          if( Math.random() <= chance ) then
	 *         |             if(math.random() <= 0.2 && getNbAnchors() > 0) then
	 *         |                getAnchorAt(new Random().nextInt(getNbAnchors())).swap(item)
	 *         |             else then
	 *         |                toCollect.add(item)
	 *         |    collect(other, toCollect)
	 */
	private void collect(Creature other)
	{
		ArrayList<Item> toCollect = new ArrayList<Item>();
		for(Item item: other.getTreasure())
		{
			double chance = 0.3;
			if(item instanceof Dukat || item instanceof Purse)
				chance = 0.5;
			if(Math.random() <= chance) // monster will take the item
			{
				if(Math.random() <= 0.2 && getNbAnchors() > 0) // monster will swap it with an item in an anchor point.
				{
					try
					{
						getAnchorAt(new Random().nextInt(getNbAnchors())).swap(item);
					}
					catch(Exception e) {}
				}
				else
					toCollect.add(item);
			}
		}
		collect(other, toCollect);
	}
}
