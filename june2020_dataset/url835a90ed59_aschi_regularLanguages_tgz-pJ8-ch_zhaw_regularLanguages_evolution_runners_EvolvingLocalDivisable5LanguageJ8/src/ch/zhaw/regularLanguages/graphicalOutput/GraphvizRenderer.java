package ch.zhaw.regularLanguages.graphicalOutput;

import java.io.File;

import fr.loria.graphViz.GraphViz;


public class GraphvizRenderer {
	public static void renderGraph(GraphvizRenderable drawable, String targetFileName){
		GraphViz gv = new GraphViz();
		byte[] graph = gv.getGraph(drawable.generateDotString(), "svg");
		gv.writeGraphToFile(graph, new File(targetFileName));
	}
}
