package com.qijiabin.bio.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ========================================================
 * 日 期：2016年4月22日 上午10:11:14
 * 作 者：qijiabin
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class TimeServerHandlerExecutePool {

	private ExecutorService service;
	
	public TimeServerHandlerExecutePool(int maxPoolSize, int queueSize) {
		try {
			service = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L,
					TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void execute(Runnable task) {
		service.execute(task);
	}
	
}
