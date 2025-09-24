package yeah.cstriker1407.design_patterns.factory.simplefactory;

public class Simplefactory1
{
	public static onSender createOnSender(String type)
	{
		if ("Mail".equals(type))
		{
			return new MailSender();
		}
		if ("SMS".equals(type))
		{
			return new SMSSender();
		}
		return null;
	}
	
	public static onSender createMail()
	{
		return new MailSender();
	}
	
	public static onSender createSMS()
	{
		return new SMSSender();
	}
}

interface onSender
{
	public void Sender();
}

class MailSender implements onSender
{
	@Override
	public void Sender()
	{
		System.out.println("Mail Sender");
	}
}

class SMSSender implements onSender
{
	@Override
	public void Sender()
	{
		System.out.println("SMS Sender");
	}
}
