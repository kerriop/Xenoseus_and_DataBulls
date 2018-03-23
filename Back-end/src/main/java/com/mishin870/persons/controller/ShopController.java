package com.mishin870.persons.controller;

import com.mishin870.persons.model.Shop;
import com.mishin870.persons.service.ShopService;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ShopController {
	private static final Logger logger = LoggerFactory.getLogger(ShopController.class);

	@Autowired
	private ShopService shopService;

	@RequestMapping(value = "/")
	public ModelAndView listEmployee(ModelAndView model) {
		List<Shop> list = shopService.getAll();
		model.addObject("list", list);
		model.setViewName("index");
		return model;
	}

	@RequestMapping(value = "/newShop", method = RequestMethod.GET)
	public ModelAndView newContact(ModelAndView model) {
		Shop shop = new Shop();
		model.addObject("shop", shop);
		model.setViewName("edit");
		return model;
	}

	@RequestMapping(value = "/saveShop", method = RequestMethod.POST)
	public ModelAndView saveEmployee(@ModelAttribute Shop shop) {
		if (shop.getId() == 0) {
			shopService.add(shop);
		} else {
			shopService.update(shop);
		}
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/deleteShop", method = RequestMethod.GET)
	public ModelAndView deleteEmployee(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		try {
			shopService.delete(id);
		} catch (ObjectNotFoundException onfe) {
			logger.warn(onfe.toString());
		}
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/editShop", method = RequestMethod.GET)
	public ModelAndView editContact(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		Shop shop = shopService.get(id);
		ModelAndView model = new ModelAndView("edit");
		model.addObject("shop", shop);
		
		return model;
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public List<Shop> test() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return shopService.getAll();
	}

}