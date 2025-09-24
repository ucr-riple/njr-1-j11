package yeah.cstriker1407.design_patterns.Composite;

import java.util.ArrayList;
import java.util.List;

public class CompositeTest
{
	public static void test()
	{
		Leader A01 = new Leader("A01");
		
		Leader A11 = new Leader("A11");
		Leader A12 = new Leader("A12");
		Leaf   B13 = new Leaf("B13");
		
		A01.addCrop(A11).addCrop(A12).addCrop(B13);
		
		Leaf   B21 = new Leaf("B21");
		Leaf   B22 = new Leaf("B22");
		Leaf   B23 = new Leaf("B23");
		
		A11.addCrop(B21);
		A12.addCrop(B22).addCrop(B23);
		A01.info();
	}
}
class Leaf extends Crop
{
	public Leaf(String name)
	{
		super(name);
	}

	@Override
	public String oper()
	{
		return "LeafOper";
	}
}
class Leader extends Crop
{
	public Leader(String arg0)
	{
		super(arg0);
	}

	@Override
	public String oper()
	{
		return "LeaderOper";
	}
}

abstract class Crop
{
	private String name;
	private List<Crop> cropList = new ArrayList<Crop>();
	public Crop(String name)
	{
		super();
		this.name = name;
	}
	public List<Crop> getCropList()
	{
		return cropList;
	}
	public Crop addCrop(Crop crop)
	{
		this.cropList.add(crop);
		return this;
	}
	public abstract String oper();
	public void info()
	{
		if (cropList.isEmpty())
		{
			System.out.println("No Crop, I am the leaf: " + name + "  " + oper());
		}else 
		{
			System.out.println("I Have childeren, I am: " + name + "  " + oper());
			for (Crop crop : cropList)
			{
				crop.info();
			}
		}
	}
}