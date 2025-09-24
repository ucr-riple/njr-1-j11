package de.hrw.mdbt.model;

import java.util.ArrayList;

public class PriceClass {
	private String name;
	private String description;
	private float freeKm;
	private float priceKm;
	private float priceDay;
	private ArrayList <Model> models = new ArrayList<Model>();

	public PriceClass(String name, String description, int freeKm, int priceDay, int priceKm) {
		this.setName(name);
		this.setDescription(description);
		this.setFreeKm(freeKm);
		this.setPriceDay(priceDay);
		this.setPriceKm(priceKm);
	}

	public PriceClass() {
	}

	public void addModel(Model m) {
		models.add(m);
	}
	public void removeModel(Model m) {
		models.remove(m);
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public float getFreeKm() {
		return freeKm;
	}
	public void setFreeKm(float freeKm) {
		this.freeKm = freeKm;
	}

	public float getPriceKm() {
		return priceKm;
	}
	public void setPriceKm(float priceKm) {
		this.priceKm = priceKm;
	}

	public float getPriceDay() {
		return priceDay;
	}
	public void setPriceDay(float priceDay) {
		this.priceDay = priceDay;
	}
}
