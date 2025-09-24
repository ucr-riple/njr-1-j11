package com.disclinc.netmanager.variable.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.com.innovatium.mumps2java.datastructure.Tree;

public class TreeTest {
	private Tree tree;

	@Before
	public void init() {
		tree = new Tree();
		tree.set(new Object[] { "carro" }, 65);
		tree.set(new Object[] { "carro", "esportivo" }, 99);
		tree.set(new Object[] { "carro", "esportivo", "amarelo" }, 76);
		tree.set(new Object[] { "contrato"}, 66);
		tree.set(new Object[] { "contrato", "transporte"}, 123);
		tree.set(new Object[] { "contrato", "transporte", "publico" }, 88);

	}

	@Test
	public void testRecoveringVariable() {
		assertEquals(65, tree.get(new Object[] { "carro" }));
		assertEquals(99, tree.get(new Object[] { "carro", "esportivo" }));
		assertEquals(76,
				tree.get(new Object[] { "carro", "esportivo", "amarelo" }));
		

	}
	
	@Test
	public void testRemoveNodes() {
		tree.kill("carro");
		assertEquals("The node should not be into the tree after killing node method", null, tree.get("carro"));
		assertEquals("The node should not be into the tree after killing node method", null, tree.get("carro", "esportivo"));
		assertEquals("The node should not be into the tree after killing node method", null, tree.get("carro", "esportivo", "amarelo"));
		assertEquals("The node should be into the tree because he was not removed from the tree", 66, tree.get("contrato"));
		assertEquals("The node should be into the tree because he was not removed from the tree", 123, tree.get("contrato", "transporte"));
		assertEquals("The node should be into the tree because he was not removed from the tree", 88, tree.get("contrato", "transporte", "publico"));
	}
}
