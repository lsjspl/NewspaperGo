package com.Master5.main.web.Catcher.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Master5.main.web.Catcher.entry.Catcher;

public interface CatcherDao  extends JpaRepository<Catcher, Integer> {
	@Query(value="    SELECT urlsinfo.keyWord,urlsinfo.name,	catcher.url,catcher.title,catcher.content,catcher.time,catcher.state "+
			     "    FROM catcher LEFT JOIN urlsinfo ON catcher.urlId = urlsinfo.id and urlsinfo.state=0                            "+
			     "    WHERE content LIKE CONCAT(\"%\", keyWord, \"%\")                                                               ",
			     nativeQuery=true
			)
	public List<Object[]> queryTotal();
}
