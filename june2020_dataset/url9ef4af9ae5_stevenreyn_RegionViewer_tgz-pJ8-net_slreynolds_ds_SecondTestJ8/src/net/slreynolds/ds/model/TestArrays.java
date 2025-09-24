package net.slreynolds.ds.model;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;

import net.slreynolds.ds.ObjectSaver;
import net.slreynolds.ds.export.ExporterOptions;
import net.slreynolds.ds.export.GraphVizExporter;
import net.slreynolds.ds.export.SimpleGraphVizExporter;
import net.slreynolds.ds.model.Graph;
import net.slreynolds.ds.model.GraphPoint;
import net.slreynolds.ds.model.Named;

import org.junit.Test;


public class TestArrays {


	
	public static class C {
		@SuppressWarnings("unused")
		private final int _i;
		public C(int i) {
			_i = i;
		}
	}
	
	@Test
	public void testObjectArray() {
		C c0 = new C(0);
		C c1 = new C(1);
		C c2 = new C(2);
		C[] myobjarray = new C[]{c0,c1,c2};
		ExporterStub exporter = new ExporterStub();
		ObjectSaver stubSaver = new ObjectSaver(exporter);
		HashMap<String,Object> options = new HashMap<String,Object>();
		stubSaver.save(new Object[]{myobjarray},new String[]{"myobjarray"},options);
		Graph g = exporter.getGraph();
		assertEquals("num graph points",5,g.getGraphPoints().size());
		
		GraphPoint gp = g.getPrimaryGraphPoint();
		assertTrue("primary point isa symbol",gp.hasAttr(Named.SYMBOL));
		assertTrue("primary point isa symbol",(Boolean)gp.getAttr(Named.SYMBOL));
		assertEquals("primary has one link",1,gp.getNeighbors().size());
		
		String dir = "test_output/";
		ObjectSaver gvizSaver = new ObjectSaver(new GraphVizExporter());
		new File(dir+"myobjarray.dot").delete();
		options.put(ExporterOptions.OUTPUT_PATH, dir+"myobjarray.dot");
		gvizSaver.save(new Object[]{myobjarray},new String[]{"myobjarray"},options);
		
		ObjectSaver simpleGvizSaver = new ObjectSaver(new SimpleGraphVizExporter());
		new File(dir+"myobjarray_simple.dot").delete();
		options.put(ExporterOptions.OUTPUT_PATH, dir+"myobjarray_simple.dot");
		simpleGvizSaver.save(new Object[]{myobjarray},new String[]{"myobjarray"},options);
	}
	
	@Test
	public void testPrimitiveArray() {
		
		int[] myintarray = new int[]{0,1,2};
		ExporterStub exporter = new ExporterStub();
		ObjectSaver stubSaver = new ObjectSaver(exporter);
		HashMap<String,Object> options = new HashMap<String,Object>();
		stubSaver.save(new Object[]{myintarray},new String[]{"myintarray"},options);
		Graph g = exporter.getGraph();
		assertEquals("num graph points",2,g.getGraphPoints().size());
		
		GraphPoint gp = g.getPrimaryGraphPoint();
		assertTrue("primary point isa symbol",gp.hasAttr(Named.SYMBOL));
		assertTrue("primary point isa symbol",(Boolean)gp.getAttr(Named.SYMBOL));
		assertEquals("primary has one link",1,gp.getNeighbors().size());
		
		String dir = "test_output/";
		ObjectSaver gvizSaver = new ObjectSaver(new GraphVizExporter());
		new File(dir+"myintarray.dot").delete();
		options.put(ExporterOptions.OUTPUT_PATH, dir+"myintarray.dot");
		gvizSaver.save(new Object[]{myintarray},new String[]{"myintarray"},options);
		
		ObjectSaver simpleGvizSaver = new ObjectSaver(new SimpleGraphVizExporter());
		new File(dir+"myintarray_simple.dot").delete();
		options.put(ExporterOptions.OUTPUT_PATH, dir+"myintarray_simple.dot");
		simpleGvizSaver.save(new Object[]{myintarray},new String[]{"myintarray"},options);
	}	
	
	public static abstract class DoubleArrayTestConfig {
		public final boolean addInlineOption;
		public final boolean valueOfInLineOption;
		public DoubleArrayTestConfig(boolean addit, boolean value) {
			addInlineOption = addit;
			valueOfInLineOption = value;
		}
		abstract int getNumGraphPoints();
		abstract String getNameFragment();
	}
	
	public static class DefaultDouble extends DoubleArrayTestConfig {
		public DefaultDouble() { super(false,false); }
		int getNumGraphPoints() { return 5; }
		String getNameFragment() { return "default"; }
	}
	
	public static class NoInline extends DoubleArrayTestConfig {
		public NoInline() { super(true,false); }
		int getNumGraphPoints() { return 5; }
		String getNameFragment() { return "noinline"; }
	}
	
	public static class DoInline extends DoubleArrayTestConfig {
		public DoInline() { super(true,true); }
		int getNumGraphPoints() { return 2; }
		String getNameFragment() { return "doinline"; }
	}
	
	@Test
	public void testDoubleArray() {
		assertEquals(false,BuilderOptions.DEFAULT_INLINE_NUMBERS);
		checkDoubleArray(new DefaultDouble());
		checkDoubleArray(new NoInline());
		checkDoubleArray(new DoInline());
	}
	
	private void checkDoubleArray(DoubleArrayTestConfig testConfig) {
		final String name = "myDoubleArray_" + testConfig.getNameFragment();
		Double[] myDoubleArray = new Double[]{0.0,1.0,2.0};
		ExporterStub exporter = new ExporterStub();
		ObjectSaver stubSaver = new ObjectSaver(exporter);
		HashMap<String,Object> options = new HashMap<String,Object>();
		if (testConfig.addInlineOption) {
			options.put(BuilderOptions.INLINE_NUMBERS,testConfig.valueOfInLineOption);
		}
		stubSaver.save(new Object[]{myDoubleArray},new String[]{name},options);
		Graph g = exporter.getGraph();
		assertEquals("num graph points",testConfig.getNumGraphPoints(),g.getGraphPoints().size());
		
		GraphPoint gp = g.getPrimaryGraphPoint();
		assertTrue("primary point isa symbol",gp.hasAttr(Named.SYMBOL));
		assertTrue("primary point isa symbol",(Boolean)gp.getAttr(Named.SYMBOL));
		assertEquals("primary has one link",1,gp.getNeighbors().size());
		
		String dir = "test_output/";
		ObjectSaver gvizSaver = new ObjectSaver(new GraphVizExporter());
		new File(dir+name+".dot").delete();
		options.put(ExporterOptions.OUTPUT_PATH, dir+name+".dot");
		gvizSaver.save(new Object[]{myDoubleArray},new String[]{name},options);
		
		ObjectSaver simpleGvizSaver = new ObjectSaver(new SimpleGraphVizExporter());
		new File(dir+name+"_simple.dot").delete();
		options.put(ExporterOptions.OUTPUT_PATH, dir+name+"_simple.dot");
		simpleGvizSaver.save(new Object[]{myDoubleArray},new String[]{name},options);
	}	
}
