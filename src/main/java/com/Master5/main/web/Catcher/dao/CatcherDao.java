package com.Master5.main.web.Catcher.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Master5.main.web.Catcher.entry.Catcher;

public interface CatcherDao  extends JpaRepository<Catcher, Integer> {

}
