package com.Master5.main.web.Catcher.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Master5.main.web.Catcher.entry.TaskLog;
import com.Master5.main.web.Catcher.entry.UrlsInfo;

public interface TaskLogDao  extends JpaRepository<TaskLog, Integer>{
	
	public List<UrlsInfo> findByTypeAndTaskId(int state,int taskId);

}
