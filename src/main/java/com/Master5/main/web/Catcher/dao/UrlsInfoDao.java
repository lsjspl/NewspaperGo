package com.Master5.main.web.Catcher.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Master5.main.web.Catcher.entry.UrlsInfo;

public interface UrlsInfoDao  extends JpaRepository<UrlsInfo, Integer>{
	
	public List<UrlsInfo> findByState(int state);

}
