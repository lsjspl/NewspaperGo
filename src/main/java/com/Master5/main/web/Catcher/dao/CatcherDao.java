package com.Master5.main.web.Catcher.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.Master5.main.web.Catcher.entry.Catcher;

public interface CatcherDao  extends JpaRepository<Catcher, Integer> {
	@Query(value="    SELECT catcher.id,urlsInfo.keyWord,urlsInfo.name,urlsInfo.type,catcher.url,catcher.title,catcher.content,catcher.time,catcher.state "+
			     "    FROM catcher LEFT JOIN urlsInfo ON catcher.urlId = urlsInfo.id and urlsInfo.state=0   and catcher.taskId=:taskId                         "+
			     "    WHERE content REGEXP urlsInfo.keyWord                                                               ",
			     nativeQuery=true
			)
	public List<Object[]> queryTotal(@Param("taskId")int taskId);

	@Modifying
	@Transactional
	@Query(value = "delete from catcher where taskId = :taskId", nativeQuery = true)
	public void deleteByTaskId(@Param("taskId") int taskId);

	@Transactional
	public void deleteByTaskIdIn(List<Integer> taskIds);
}
