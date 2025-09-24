package testbed;

import java.io.File;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Vector;

import utilities.FIO;
import bbms.GlobalFuncs;
import jneat.*;

public class JNEATtest {
	
	public static void test1() {
		Network testNet = new Network(null, null);
		
		NNode node1 = new NNode(NodeTypeEnum.SENSOR);
		NNode node2 = new NNode(NodeTypeEnum.NEURON);
		
		testNet.attachInput(node1);
		testNet.attachOutput(node2);
		
		testNet.linkNodes(node1, node2, 0.3);
		node1.LoadSensor(0.7);
		
		System.out.println("\n" + node1.PrintNode());
		System.out.println("\n" + node2.PrintNode());
		
		testNet.ActivateNetwork();
		
		System.out.println("\n" + node1.PrintNode());
		System.out.println("\n" + node2.PrintNode());				
	}
	
	public static void test2() {
		double gT = 99.0;
		double eP = gT * 0.8;
		
		System.out.println("Answer: " + eP);
	}
	
	public static void test3() {
		Genome x = new Genome();
		NNode node1 = new NNode(NodeTypeEnum.SENSOR);
		NNode node2 = new NNode(NodeTypeEnum.NEURON);
		Trait t = new Trait();
		Trait t2 = new Trait();
		t2.setTraitParam(3, 1.5);
		Gene l1 = new Gene(node1, node2, 0.3);
		
		x.nodes.add(node1);
		x.nodes.add(node2);
		x.traits.add(t);
		x.traits.add(t2);
		x.genes.add(l1);
		
		Genome x2 = x.duplicate(1);
		x.traits.get(1).setTraitParam(1, -5);
		
		System.out.println(x.PrintGenome());
		System.out.println(x2.PrintGenome());
		
		
	}
	
	public static void test4() {
		Genome testGenome = new Genome();
		
		NNode sensor1 = new NNode(NodeTypeEnum.SENSOR);
		NNode sensor2 = new NNode(NodeTypeEnum.SENSOR);
		
		NNode hidden1 = new NNode(NodeTypeEnum.NEURON);
		NNode hidden2 = new NNode(NodeTypeEnum.NEURON);
		
		NNode output1 = new NNode(NodeTypeEnum.NEURON, NodeLabelEnum.OUTPUT);
		
		testGenome.nodes.add(sensor1);
		testGenome.nodes.add(sensor2);
		testGenome.nodes.add(hidden1);
		testGenome.nodes.add(hidden2);
		testGenome.nodes.add(output1);
				
		testGenome.genes.add(new Gene(sensor1, hidden1, GlobalFuncs.randFloat()));
		testGenome.genes.add(new Gene(sensor2, hidden1, GlobalFuncs.randFloat()));
		//testGenome.genes.add(new Gene(hidden1, hidden2, GlobalFuncs.randFloat()));
		testGenome.genes.add(new Gene(hidden2, output1, GlobalFuncs.randFloat()));
		testGenome.genes.add(new Gene(hidden1, output1, GlobalFuncs.randFloat()));
		
		Population y = new Population(testGenome, 1);
		
		
		Network x = testGenome.Genesis(1);
		
		System.out.println(testGenome.PrintGenome());

		
	
		
		x.inputs.get(0).LoadSensor(0.75);
		x.inputs.get(1).LoadSensor(0.25);
		
		System.out.println("Minimal Test: " + x.IsMinimal());
		// System.out.println("Stabilizes in: " + x.IsStabilized(0));
		System.out.println("Max depth: " + x.max_depth());
						
		System.out.println(x.PrintNetwork(true));
		
		
		 
		x.ActivateNetwork();		
		System.out.println(x.PrintNetwork());
		
		x.ActivateNetwork();		
		System.out.println(x.PrintNetwork());
		
		x.ActivateNetwork();		
		System.out.println(x.PrintNetwork());
		
		
		System.out.println("\n\n" + y.PrintPopulation());
				
	}
	
	public static double test5fitness(double sensVal, boolean netOutput) {
		if (sensVal > 0.75) {
			if (netOutput) return 1.0;
			else return 0.0;
		}
		else {
			if (!netOutput) return 1.0;
			else return 0.0;
		}
	}
	
	public static void test5sensors(Population pop, int iterations) {
		for (int i = 0; i < iterations; i++) {
			System.out.println("Iteration #" + i);
			
			// Input range [-1, +1]
			double sensorInp = GlobalFuncs.randFloat() * GlobalFuncs.randPosNeg();			
			System.out.println("Input sensor is: " + sensorInp);
			
			for (int j = 0; j < pop.organisms.size(); j++) {
				Organism finger = pop.organisms.elementAt(j);
				finger.net.inputs.firstElement().LoadSensor(sensorInp);
				finger.net.ActivateNetwork();
				
				double result = finger.net.outputs.firstElement().getActivation();
				
				System.out.println("Organism #" + j + " outputs: " + result);				
				boolean stayHere;
				if (result > 0.5) stayHere = true;
				else stayHere = false;
				
				// Maintains average fitness over i tests
				double fitness = test5fitness(sensorInp, stayHere);
				finger.fitness = (finger.fitness * i) + fitness;
				finger.fitness /= (i + 1);
				
				if (stayHere) {
					System.out.print("   >>> Agent will STAY.  ");
				}
				else {
					System.out.print("   >>> Agent will NOT.  ");
				}
				System.out.println("Output val: " + result + " with fitness " + fitness + " and avg: " + finger.fitness);
			}			
		}
	}
	
	public static void test5() {
		Population pop = new Population(50, 1, 1, 5, false, 0.01);
		
		// System.out.println(pop.PrintPopulation());
		
		test5sensors(pop, 20);				
		
		System.out.println(pop.PrintPopulation());
		
		System.out.println("\n\n---- Epoch ----\n\n");
		pop.epoch();
		
		System.out.println("\n\n First Epoch completed.");
		
		System.out.println(pop.PrintPopulation());
		
		test5sensors(pop, 20);
		
		System.out.println("SECOND RUN");
		System.out.println(pop.PrintPopulation());
		
		System.out.println("\n\n---- Epoch ----\n\n");
		pop.epoch();
		
		System.out.println("\n\n Second Epoch completed.");
		
		System.out.println(pop.PrintPopulation());

		
		System.out.println("Program complete.");
		
	}
	
	public static Population testLoad(String loadFile) {		
		File f = new File(loadFile);
		if (!f.exists()) return null;
		
		Path p = f.toPath();
		Population pop = new Population(p);
		
		return pop;
	}
	
	public static void testSave(String saveFile, Population pop) {
		File f = new File(saveFile);
		if (!f.exists()) FIO.newFile(saveFile);
		
		Path p = f.toPath();
		
		
		pop.SavePopulationToFile(p);
	}
	
	public static void test6() {
		Population pop = new Population(50, 1, 1, 5, false, 0.1);				
		test5sensors(pop, 20);				
		testSave("src/saves/pop2.txt", pop);
		
		pop.epoch();
		testSave("src/saves/pop1.txt", pop);
	}
	
	public static void test7() {
		Population pop = new Population(50, 1, 1, 5, false, 0.1);	
		test5sensors(pop, 20);
		testSave("src/saves/pop2.txt", pop);
		
		pop.epoch();
		
		testSave("src/saves/pop1.txt", pop);
				
		//Population comp = testLoad("src/saves/pop2.txt");
		//testSave("src/saves/pop1.txt", comp);
	}
	
	public static void main(String[] args) {
		test7();
		
		//testLoad("src/saves/pop1.txt");
		
	} 
	
	public static void testX() {
		
		NodeFuncEnum x = NodeFuncEnum.valueOf("SIGMOID");
		
		System.out.println("This should be Sigmoid: " + x);
		
		return;
	}
	

}
