
package com.Master5.main.web.record.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Master5.main.web.record.dao.RecordDao;
import com.Master5.main.web.record.entry.Record;

@Service
public class RecordService {

	@Autowired
	RecordDao recordDao;

	public List<Record> findAll() {

		return recordDao.findAll();
	}

	public boolean save(Record record) {

		try {
			recordDao.save(record);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public boolean delete(long id) {

		try {
			recordDao.delete(id);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public Record findById(long id) {

		return recordDao.findOne(id);
	}

}
