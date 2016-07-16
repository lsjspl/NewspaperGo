package com.Master5.main.web.order.entry;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/***
 * 食材
 * 
 * @author Bada Lee
 *
 */
@Entity
@Table(name = "ingredient")
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(unique = true)
	private String name;

	@ManyToOne
	private IngredientType ingredientType;

	private double price;

	private String unit;

	private Date changeTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IngredientType getIngredientType() {
		return ingredientType;
	}

	public void setIngredientType(IngredientType ingredientType) {
		this.ingredientType = ingredientType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	@Override
	public String toString() {
		return "Ingredient [id=" + id + ", name=" + name + ", ingredientType=" + ingredientType + ", price=" + price
				+ ", unit=" + unit + ", changeTime=" + changeTime + "]";
	}

}
