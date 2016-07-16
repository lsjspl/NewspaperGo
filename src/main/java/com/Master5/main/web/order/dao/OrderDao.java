package com.Master5.main.web.order.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Master5.main.web.order.entry.Orders;

@Repository
public interface OrderDao extends JpaRepository<Orders, Integer> {
	List<Orders> findByType(int type);
}
