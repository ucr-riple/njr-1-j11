package fr.noxx90.jflam.model;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import fr.noxx90.jflam.renderer.FlameRenderer;

public class Flame
{
	protected Map<String, Form> forms;
	
	public Flame() {
		forms = new HashMap<String, Form>();
	}
	
	public void add(String name, Form f) {
		forms.put(name, f);
		f.setName(name);
	}
	
	public void remove(String name) {
		forms.remove(name);
	}
	
	public void clear() {
		forms.clear();
	}

	public Map<String, Form> getForms() {
		return forms;
	}

	public BufferedImage render(FlameRenderer renderer, int width, int height, int quality, int sampling) {
		return renderer.render(this, width, height, quality, sampling);
	}
}
