package com.Master5.main.web.order.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Master5.main.web.order.entry.Ingredient;
import com.Master5.main.web.order.entry.OrdersIngredient;

@Repository
public interface OrdersIngredientDao extends JpaRepository<OrdersIngredient, Integer> {

	@Query(value="select sum(orders_ingredient.amount) as amount,orders_ingredient.ingredient_id as ingredient_id,"
			+ "orders_ingredient.id as id,orders_ingredient.orders_id as orders_id from "
			+ "orders_ingredient left join orders on orders_ingredient.orders_id=orders.id"
			+ " group by orders_ingredient.ingredient_id  having sum(orders_ingredient.amount)>0 order by amount desc;",
			nativeQuery=true
			)
	public List<OrdersIngredient> queryTotal();
	
}
