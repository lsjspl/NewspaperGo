package com.Master5.main.web.order.entry;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@Entity
@Table(name = "orders_ingredient")
public class OrdersIngredient {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	@JoinColumn(name = "orders_id")
	private Orders ordersId;

	@ManyToOne
	@JoinColumn(name = "ingredient_id")
	private Ingredient ingredientId;

	@Override
	public String toString() {
		return "OrdersIngredient [id=" + id + ", ordersId=" + ordersId + ", ingredientId=" + ingredientId + ", amount="
				+ amount + "]";
	}

	private int amount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Orders getOrderId() {
		return ordersId;
	}

	public Orders getOrdersId() {
		return ordersId;
	}

	public void setOrdersId(Orders ordersId) {
		this.ordersId = ordersId;
	}

	public Ingredient getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(Ingredient ingredientId) {
		this.ingredientId = ingredientId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
