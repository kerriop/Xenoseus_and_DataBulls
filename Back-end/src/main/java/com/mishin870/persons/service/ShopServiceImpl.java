package com.mishin870.persons.service;

import com.mishin870.persons.dao.ShopsDAO;
import com.mishin870.persons.model.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShopServiceImpl implements ShopService {
	public void setShopDAO(ShopsDAO shopDAO) {
		this.shopDAO = shopDAO;
	}
	
	@Autowired
	private ShopsDAO shopDAO;
	
	@Override
	@Transactional
	public void add(Shop shop) {
		shopDAO.add(shop);
	}
	
	@Override
	@Transactional
	public List<Shop> getAll() {
		return shopDAO.getAll();
	}
	
	@Override
	@Transactional
	public void delete(Integer id) {
		shopDAO.delete(id);
	}
	
	public Shop get(int id) {
		return shopDAO.get(id);
	}
	
	public Shop update(Shop shop) {
		return shopDAO.update(shop);
	}
	
}
