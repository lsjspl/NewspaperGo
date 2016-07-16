package com.Master5.main.web.order.entry;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.Master5.main.web.user.entry.User;

/**
 * 订单
 * 
 * @author Bada Lee
 *
 */
@Entity
@Table(name = "orders")
@JsonIgnoreProperties(value={"detail"})
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	@JoinColumn(name = "supplierID")
	private Supplier supplierID;

	private int status;

	private String remarks;

	private Date createtime;

	@ManyToOne
	private User buyyer;

	private Date buttime;

	@ManyToOne
	private User manager;

	private Date intime;
	
	private int type;

//	@ManyToMany
//	@JoinTable(name = "orders_ingredient", joinColumns = { @JoinColumn(name = "orders_id") }, inverseJoinColumns = {
//			@JoinColumn(name = "ingredient_id") })
//	private List<Ingredient> ingredients;

	@OneToMany(fetch=FetchType.EAGER,cascade={CascadeType.ALL})
	@JoinColumn(name = "orders_id")
	private List<OrdersIngredient> detail;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Supplier getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(Supplier supplierID) {
		this.supplierID = supplierID;
	}

	public List<OrdersIngredient> getDetail() {
		return detail;
	}

	public void setDetail(List<OrdersIngredient> detail) {
		this.detail = detail;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public User getBuyyer() {
		return buyyer;
	}

	public void setBuyyer(User buyyer) {
		this.buyyer = buyyer;
	}

	public Date getButtime() {
		return buttime;
	}

	public void setButtime(Date buttime) {
		this.buttime = buttime;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public Date getIntime() {
		return intime;
	}

	public void setIntime(Date intime) {
		this.intime = intime;
	}

//	public List<Ingredient> getIngredients() {
//		return ingredients;
//	}
//
//	public void setIngredients(List<Ingredient> ingredients) {
//		this.ingredients = ingredients;
//	}

	@Override
	public String toString() {
		return "Orders [id=" + id + ", supplierID=" + supplierID + ", status=" + status + ", remarks=" + remarks
				+ ", createtime=" + createtime + ", buyyer=" + buyyer + ", buttime=" + buttime + ", manager=" + manager
				+ ", intime=" + intime   + ", detail=" + detail + "]";
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
