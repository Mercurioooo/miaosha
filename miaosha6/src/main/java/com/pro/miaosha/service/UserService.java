package com.pro.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pro.miaosha.dao.UserDao;
import com.pro.miaosha.domain.User;
import com.pro.miaosha.result.CodeMsg;
import com.pro.miaosha.vo.LoginVo;

@Service
public class UserService {
	@Autowired
	UserDao userDao;

	public User getById(int id) {
		return userDao.getById(id);
	}
	
	@Transactional
	public boolean tx() {
		User user1 = new User();
		user1.setId(2);
		user1.setName("user1");
		userDao.insert(user1);
		
		User user2 = new User();
		user2.setId(1);
		user2.setName("user2");
		userDao.insert(user2);
		return true;
	}

	
}