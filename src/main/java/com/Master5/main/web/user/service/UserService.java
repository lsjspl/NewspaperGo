
package com.Master5.main.web.user.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.Master5.main.web.user.dao.UserDao;
import com.Master5.main.web.user.entry.User;

@Service("userService")
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	protected UserDao userDao;

	@Resource(name = "userDao")
	public void setUserDao(UserDao userDao) {

		this.userDao = userDao;
	}

	public List<User> findAll() {

		return userDao.findAll();
	}

	public User save(User user) {

		try {
			return userDao.save(user);
		} catch (Exception e) {
			return null;
		}
	}

	public boolean delete(long id) {

		try {

			userDao.delete(id);

		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public User findByNickName(String nickName) {

		return userDao.findByNickName(nickName);
	}

	public User findByName(String name) {

		return userDao.findByName(name);
	}

	public User findByEmail(String email) {

		return userDao.findByEmail(email);
	}

	public User findById(long id) {

		return userDao.findOne(id);
	}
}
