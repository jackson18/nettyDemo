package com.qijiabin.bio.pool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * ========================================================
 * 日 期：2016年4月22日 上午9:23:06
 * 作 者：qijiabin
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class TimeServerHandler implements Runnable {
	
	private Socket socket;
	
	public TimeServerHandler(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			out = new PrintWriter(this.socket.getOutputStream(), true);
			String buffer = null;
			while (true) {
				buffer = in.readLine();
				if (buffer == null) {
					break;
				}
				System.out.println("server receive: " + buffer);
				out.println("hi: " + buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
