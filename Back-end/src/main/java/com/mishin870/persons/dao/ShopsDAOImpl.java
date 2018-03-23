package com.mishin870.persons.dao;

import com.mishin870.persons.model.Shop;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ShopsDAOImpl implements ShopsDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void add(Shop shop) {
		sessionFactory.getCurrentSession().saveOrUpdate(shop);
	}
	
	@Override
	public List<Shop> getAll() {
		return (List<Shop>) sessionFactory.getCurrentSession().createQuery("from Shop").list();
	}
	
	@Override
	public void delete(Integer id) {
		Shop shop = (Shop) sessionFactory.getCurrentSession().load(Shop.class, id);
		if (shop != null) {
			this.sessionFactory.getCurrentSession().delete(shop);
		}
	}
	
	@Override
	public Shop get(int id) {
		return (Shop) sessionFactory.getCurrentSession().get(Shop.class, id);
	}
	
	@Override
	public Shop update(Shop shop) {
		sessionFactory.getCurrentSession().update(shop);
		return shop;
	}
}
