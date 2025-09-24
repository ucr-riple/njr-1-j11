package yeah.cstriker1407.design_patterns.mediator;

import java.util.Random;

public abstract class AbstractColleague
{
	protected AbstractMediator mediator;

	public AbstractColleague(AbstractMediator _mediator)
	{
		this.mediator = _mediator;
	}
}

class Purchase extends AbstractColleague
{
	public Purchase(AbstractMediator _mediator)
	{
		super(_mediator);
	}

	// 采购IBM型号的电脑
	public void buyIBMcomputer(int number)
	{
		super.mediator.execute("purchase.buy", number);
	}

	// 不在采购IBM电脑
	public void refuseBuyIBM()
	{
		System.out.println("不再采购IBM电脑");
	}
}

class Stock extends AbstractColleague
{
	public Stock(AbstractMediator _mediator)
	{
		super(_mediator);
	}

	// 刚开始有100台电脑
	private static int COMPUTER_NUMBER = 100;

	// 库存增加
	public void increase(int number)
	{
		COMPUTER_NUMBER = COMPUTER_NUMBER + number;
		System.out.println("库存数量为：" + COMPUTER_NUMBER);
	}

	// 库存降低
	public void decrease(int number)
	{
		COMPUTER_NUMBER = COMPUTER_NUMBER - number;
		System.out.println("库存数量为：" + COMPUTER_NUMBER);
	}

	// 获得库存数量
	public int getStockNumber()
	{
		return COMPUTER_NUMBER;
	}

	// 存货压力大了，就要通知采购人员不要采购，销售人员要尽快销售
	public void clearStock()
	{
		System.out.println("清理存货数量为：" + COMPUTER_NUMBER);
		super.mediator.execute("stock.clear");
	}
}

class Sale extends AbstractColleague
{
	public Sale(AbstractMediator _mediator)
	{
		super(_mediator);
	}

	// 销售IBM型号的电脑
	public void sellIBMComputer(int number)
	{
		super.mediator.execute("sale.sell", number);
		System.out.println("销售IBM电脑" + number + "台");
	}

	// 反馈销售情况,0——100之间变化，0代表根本就没人卖，100代表非常畅销，出1一个卖一个
	public int getSaleStatus()
	{
		Random rand = new Random(System.currentTimeMillis());
		int saleStatus = rand.nextInt(100);
		System.out.println("IBM电脑的销售情况为：" + saleStatus);
		return saleStatus;
	}

	// 折价处理
	public void offSale()
	{
		super.mediator.execute("sale.offsell");
	}
}