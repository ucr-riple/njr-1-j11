package yeah.cstriker1407.design_patterns.mediator;

public class MediatorTest
{
	public static void test()
	{
		AbstractMediator mediator = new Mediator();
		//采购人员采购电脑
		System.out.println("------采购人员采购电脑--------");
		Purchase purchase = new Purchase(mediator);
		Sale sale = new Sale(mediator);
		Stock stock = new Stock(mediator);

		mediator.setPurchase(purchase);
		mediator.setSale(sale);
		mediator.setStock(stock);
		
		purchase.buyIBMcomputer(100);
		//销售人员销售电脑
		System.out.println("\n------销售人员销售电脑--------");
		sale.sellIBMComputer(1);
		//库房管理人员管理库存
		System.out.println("\n------库房管理人员清库处理--------");
		stock.clearStock();
	}
}
