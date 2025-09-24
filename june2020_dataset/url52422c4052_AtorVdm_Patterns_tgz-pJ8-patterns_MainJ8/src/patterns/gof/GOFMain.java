package patterns.gof;

import java.util.ArrayList;
import java.util.List;

import patterns.gof.behavioral.chainOfResponsibility.ChainOfResponsibilityClient;
import patterns.gof.behavioral.command.CommandClient;
import patterns.gof.behavioral.interpreter.InterpreterClient;
import patterns.gof.behavioral.iterator.IteratorClient;
import patterns.gof.behavioral.mediator.MediatorClient;
import patterns.gof.behavioral.memento.MementoClient;
import patterns.gof.behavioral.observer.ObserverClient;
import patterns.gof.behavioral.state.StateClient;
import patterns.gof.behavioral.strategy.StrategyClient;
import patterns.gof.behavioral.templateMethod.TemplateMethodClient;
import patterns.gof.behavioral.visitor.VisitorClient;
import patterns.gof.creational.abstractFactory.AbstractFactoryClient;
import patterns.gof.creational.builder.BuilderClient;
import patterns.gof.creational.factoryMethod.FactoryMethodClient;
import patterns.gof.creational.prototype.PrototypeClient;
import patterns.gof.creational.singleton.SingletonClient;
import patterns.gof.helpers.Client;
import patterns.gof.helpers.PatternList;
import patterns.gof.structural.adapter.AdapterClient;
import patterns.gof.structural.bridge.BridgeClient;
import patterns.gof.structural.composite.CompositeClient;
import patterns.gof.structural.decorator.DecoratorClient;
import patterns.gof.structural.facade.FacadeClient;
import patterns.gof.structural.flyweight.FlyweightClient;
import patterns.gof.structural.proxy.ProxyClient;

public class GOFMain {
	public void main() {
		List<PatternList> gofPatterns = new ArrayList<PatternList>();
		
		fillPatterns(gofPatterns);
		
		printPatterns(gofPatterns);
	}
	
	private void fillPatterns(List<PatternList> gofPatterns) {
		fillCreational(gofPatterns);
		fillStructural(gofPatterns);
		fillBehavioral(gofPatterns);
	}
	
	private void fillCreational(List<PatternList> gofPatterns) {
		gofPatterns.add(new PatternList("CREATIONAL", new Client[] {
				new AbstractFactoryClient(),
				new BuilderClient(),
				new FactoryMethodClient(),
				new PrototypeClient(),
				new SingletonClient()
		}));
	}
	
	private void fillStructural(List<PatternList> gofPatterns) {
		gofPatterns.add(new PatternList("STRUCTURAL", new Client[] {
				new AdapterClient(),
				new BridgeClient(),
				new CompositeClient(),
				new DecoratorClient(),
				new FacadeClient(),
				new FlyweightClient(),
				new ProxyClient()
		}));
	}
	
	private void fillBehavioral(List<PatternList> gofPatterns) {
		gofPatterns.add(new PatternList("BEHAVIORAL", new Client[] {
				new ChainOfResponsibilityClient(),
				new CommandClient(),
				new InterpreterClient(),
				new IteratorClient(),
				new MediatorClient(),
				new MementoClient(),
				new ObserverClient(),
				new StateClient(),
				new StrategyClient(),
				new TemplateMethodClient(),
				new VisitorClient()
		}));
	}
	
	private void printPatterns(List<PatternList> gofPatterns) {
		for (PatternList patternList : gofPatterns) {
			testingOutput(patternList.getListName());
			for (Client client : patternList.getPatterns()) {
				client.main();
			}
		}
	}
	
	private void testingOutput(String testingCase) {
		System.out.println("          /\\/\\/\\/\\/\\/\\/\\/\\/\\/\\");
		System.out.println("               " + testingCase);
		System.out.println("          \\/\\/\\/\\/\\/\\/\\/\\/\\/\\/");
		System.out.print("----------------------------------------\n");
	}
}