package com.mishin870.persons.service;

import com.mishin870.persons.model.Shop;

import java.util.List;

public interface ShopService {
	
	void add(Shop shop);

	List<Shop> getAll();

	void delete(Integer id);

	Shop get(int id);

	Shop update(Shop shop);
}
