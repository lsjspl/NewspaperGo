package com.Master5.main.websocket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

@ServerEndpoint(value = "/websocket/{user}")
public class MyServerEndpoint {

	private Session session;
	private static final Logger sysLogger = Logger.getLogger("sysLog");

	@OnOpen
	public void open(Session session, @PathParam(value = "user") String user) {
		this.session = session;

		sysLogger.info("*** WebSocket opened from sessionId " + session.getId());
	}

	@OnMessage
	public void inMessage(String message) {
		
		for(int i=0;i<50;i++){
			send("哈哈 搞毛："+message);
		}
		sysLogger.info("*** WebSocket Received from sessionId " + this.session.getId() + ": " + message);
	}

	@OnClose
	public void end() {
		sysLogger.info("*** WebSocket closed from sessionId " + this.session.getId());
	}

	public void send(String msg) {
		try {
			session.getBasicRemote().sendText(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}