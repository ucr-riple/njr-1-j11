package yeah.cstriker1407.design_patterns;

import java.util.ArrayList;
import java.util.List;

import yeah.cstriker1407.design_patterns.Composite.CompositeTest;
import yeah.cstriker1407.design_patterns.bridge.BridgeTest;
import yeah.cstriker1407.design_patterns.builder.BuilderTest;
import yeah.cstriker1407.design_patterns.decorator.DecoratorTest;
import yeah.cstriker1407.design_patterns.factory.abstractfactory.AbstractFactory1;
import yeah.cstriker1407.design_patterns.interpreter.InterpreterTest;
import yeah.cstriker1407.design_patterns.mediator.MediatorTest;
import yeah.cstriker1407.design_patterns.observe.ObserveTest;
import yeah.cstriker1407.design_patterns.responsechain.ResponsechainTest;
import yeah.cstriker1407.design_patterns.singleton.Singleton_doublecheck;
import yeah.cstriker1407.design_patterns.singleton.Singleton_enum;
import yeah.cstriker1407.design_patterns.singleton.Singleton_hungry;
import yeah.cstriker1407.design_patterns.singleton.Singleton_lazy;
import yeah.cstriker1407.design_patterns.state.LiftStates;
import yeah.cstriker1407.design_patterns.template.TemplateTest;

public class Main 
{
	public static void main(String[] args)
	{
		/* Singleton */
		System.out.println("直接加载");
		Singleton_hungry.getInstance().sayHello();

		System.out.println("懒加载");
		Singleton_lazy.donth();
		System.out.println("--");
		Singleton_lazy.getInstance().sayHello();
		
		System.out.println("double check");
		Singleton_doublecheck.getInstance().sayHello();

		System.out.println("enum");
		Singleton_enum.INSTANCE.sayHello();
		
		/* factory */
		System.out.println("factory");
		AbstractFactory1.test();
		
		/* templates */
		TemplateTest.test();
		
		/* Builder */
		System.out.println("Builder");
		BuilderTest.test();
		
		
		/* Builder */
		System.out.println("State");
		LiftStates.normal();
		LiftStates.test();
		
		
		/* Observer */
		ObserveTest.test();
		
		
		/* Responsechain */
		ResponsechainTest.test();
		
		
		/* bridge test */
		BridgeTest.test();
		
		
		/* Composite Test */
		CompositeTest.test();
		
		/* Decorator Test */
		DecoratorTest.test();
		
		
		/* MediatorTest test */
		MediatorTest.test();
		
		/* Interpreter test */
		InterpreterTest.test();
		
	}
	
	class Person
	{
		public int id;
		public List<Integer> datiqingkuang;
		public int success;
		public int fails;
		public int score;
		public int paiming;
	}
	
	//N个参赛选手和T个题目
	private void test(int N, int T)
	{
		List<Person> list = new ArrayList<Person>();
		for (int i = 0; i < N; i++)
		{
			Person person = new Person();
			person.id = i;
			list.add(person);
		}
		int [] timuFenzhi = new int[T];
		for (int i = 0; i < T; i++)
		{
			for (int j = 0; j < N; j++)
			{
				timuFenzhi[i] += list.get(j).datiqingkuang.get(i);
			}
		}
		for (int i = 0; i < N; i++)
		{
			for (int j = 0; j < T; j++)
			{
				list.get(i).score += list.get(i).datiqingkuang.get(j) * timuFenzhi[j];
			}
		}
	}
}
