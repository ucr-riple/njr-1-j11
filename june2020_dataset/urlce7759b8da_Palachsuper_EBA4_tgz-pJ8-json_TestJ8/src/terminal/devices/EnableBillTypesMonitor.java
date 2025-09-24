package terminal.devices;


public class EnableBillTypesMonitor implements Runnable {

	private cashe_machine oCashMachine;
	
	public EnableBillTypesMonitor(cashe_machine pCashMachine)
	{
		this.oCashMachine = pCashMachine;
	}

	public void run() 
	{
		while(cashe_machine.FLAG_ENABLE_BILL_TYPES)
		{
			if (this.oCashMachine.enableBillTypesMonitor()) // если поймал купюру, то вышел
				return;
		}
		
		if (cashe_machine.FLAG_ENABLE_BILL_TYPES_ALARM_DISABLE)
			cashe_machine.FLAG_ENABLE_BILL_TYPES_ALARM_DISABLE = false;
		else
			this.oCashMachine.stopMonitor();
	}
	

	
}