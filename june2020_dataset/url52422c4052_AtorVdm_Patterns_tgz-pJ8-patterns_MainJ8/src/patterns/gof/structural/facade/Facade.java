package patterns.gof.structural.facade;

import java.util.Random;

import patterns.gof.helpers.Pattern;

// Computer
public class Facade implements Pattern {
	private CPU cpu;
    private Memory memory;
    private HardDrive hardDrive;
 
    public Facade() {
        this.cpu = new CPU();
        this.memory = new Memory();
        this.hardDrive = new HardDrive();
    }
 
    public void startComputer() {
        cpu.freeze();
        memory.load(Math.abs((new Random()).nextLong()), hardDrive.read
        		( Math.abs((new Random()).nextLong()),
        				Math.abs((new Random()).nextInt(10)) ));
        cpu.jump((new Random()).nextLong());
        cpu.execute();
    }
}