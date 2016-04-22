package com.qijiabin.bio.pool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ========================================================
 * 日 期：2016年4月22日 上午9:19:01
 * 作 者：qijiabin
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class TimeServer {

	public static void main(String[] args) {
		int port = 8888;
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			System.out.println("the time server is start in port: " + port);
			Socket socket = null;
			TimeServerHandlerExecutePool pool = new TimeServerHandlerExecutePool(50, 1000);
			while(true) {
				socket = server.accept();
				pool.execute(new TimeServerHandler(socket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (server != null) {
				System.out.println("the time server close");
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
