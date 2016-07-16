package com.Master5.main.web.record.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Master5.main.web.record.entry.Record;
import com.Master5.main.web.user.entry.User;

public interface RecordDao extends JpaRepository<Record, Long> {

	public User findByUserName(String userName);

}
