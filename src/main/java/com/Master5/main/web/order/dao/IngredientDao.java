package com.Master5.main.web.order.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Master5.main.web.order.entry.Ingredient;

@Repository
public interface IngredientDao extends JpaRepository<Ingredient, Integer> {

}
