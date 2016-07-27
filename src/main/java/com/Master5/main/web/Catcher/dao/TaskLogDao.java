package com.Master5.main.web.Catcher.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.Master5.main.web.Catcher.entry.TaskLog;

public interface TaskLogDao extends JpaRepository<TaskLog, Integer> {

	public List<TaskLog> findByTypeAndTaskIdOrderByIdDesc(int state, int taskId);

	@Modifying
	@Transactional
	@Query(value = "delete from taskLog where taskId = :taskId", nativeQuery = true)
	public void deleteByTaskId(@Param("taskId") int taskId);
	@Transactional
	public void deleteByTaskIdIn(List<Integer> taskIds);

}
