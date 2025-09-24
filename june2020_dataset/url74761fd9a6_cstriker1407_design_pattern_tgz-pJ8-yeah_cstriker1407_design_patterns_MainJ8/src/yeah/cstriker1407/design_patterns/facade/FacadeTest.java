package yeah.cstriker1407.design_patterns.facade;

public class FacadeTest
{
	public static void test()
	{
		Person person = new Person();
		person.write("hello");
		person.send("Nanjing");
		
		PostOffice office = new PostOffice();
		office.sendLetter("Hello", "Nanjing");
	}
}

interface LetterPost
{
	public void write(String str);
	public void send(String addresss);
}

class Person implements LetterPost
{

	@Override
	public void write(String str)
	{
		System.out.println("write:" + str);
	}

	@Override
	public void send(String addresss)
	{
		System.out.println("addr:" + addresss);
	}
}

class PostOffice
{
	private LetterPost letterPost = new LetterPost()
	{
		@Override
		public void write(String str)
		{
			System.out.println("write:" + str);
		}
		
		@Override
		public void send(String addresss)
		{
			System.out.println("addr:" + addresss);
		}
	};	
	
	public void sendLetter(String text, String addr)
	{
		letterPost.write(text);
		letterPost.send(addr);
	}
}
