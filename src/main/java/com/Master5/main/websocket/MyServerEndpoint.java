package com.Master5.main.websocket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

@ServerEndpoint(value = "/websocket/{param}")
public class MyServerEndpoint {

	private Session session;
	private static final Logger logger = Logger.getLogger("sysLog");

	int i=0;
	@OnOpen
	public void open(Session session, @PathParam(value = "param") String param) {
		this.session = session;

		logger.info("*** WebSocket opened from sessionId " + session.getId());
	}

	@OnMessage
	public void inMessage(String message) {
		i++;
		for(int i=0;i<50;i++){
			send("哈哈 搞毛："+message+i);
		}
		close();
		logger.info("*** WebSocket Received from sessionId " + this.session.getId() + ": " + message);
	}

	@OnClose
	public void end() {
		logger.info("*** WebSocket closed from sessionId " + this.session.getId());
	}

	private void send(String msg) {
		try {
			session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void close() {
		try {
			session.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("关闭失败");
		}
	}
}