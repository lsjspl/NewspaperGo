package com.Master5.main.web.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Master5.main.web.user.entry.User;

public interface UserDao extends JpaRepository<User, Long> {

	public User findByNickName(String nickName);

	public User findByName(String name);

	public User findByEmail(String email);

}
