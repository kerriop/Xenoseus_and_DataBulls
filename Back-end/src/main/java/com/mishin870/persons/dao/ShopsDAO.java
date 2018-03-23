package com.mishin870.persons.dao;

import com.mishin870.persons.model.Shop;

import java.util.List;

public interface ShopsDAO {
	
	void add(Shop shop);
	
	List<Shop> getAll();
	
	void delete(Integer id);
	
	Shop update(Shop shop);
	
	Shop get(int id);
}
