package com.Master5.main.web.Catcher.service;

import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Master5.main.web.Catcher.entry.CatcherTask;

public class ProducerConsumer {
	
	@Autowired
	CatcherService catcherService;
	
	// 建立一个阻塞队列
	private static LinkedBlockingQueue<Object> queue = new LinkedBlockingQueue<Object>(10);

	private static ProducerConsumer producerConsumer;
	
	public static ProducerConsumer getInstance() {

		if (producerConsumer == null) {
			producerConsumer = new ProducerConsumer();
		}
		return producerConsumer;

	}

	private ProducerConsumer() {
		try {
			new Consumer().start();
		} catch (Exception e) {
			producerConsumer=null;
		}
	}

	public void put(CatcherTask task) throws InterruptedException {
		queue.put(task);
	}

	class Consumer extends Thread {
		public void run() {
			CatcherTask task = null;
			while (true) {
				try {
					task= (CatcherTask) queue.take();
					catcherService.catcherWork(null,task.getStartDate(),task.getEndDate(),task);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("任务失败："+task.getName());
					continue;
				}
			}
		}
	}

}
