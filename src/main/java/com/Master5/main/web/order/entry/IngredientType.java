package com.Master5.main.web.order.entry;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商品类型
 * 
 * @author Bada Lee
 *
 */
@Entity
@Table(name = "ingredient_type")
public class IngredientType {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(unique = true)
	private String name;

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

}
