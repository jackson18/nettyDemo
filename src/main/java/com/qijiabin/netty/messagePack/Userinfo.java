package com.qijiabin.netty.messagePack;

import org.msgpack.annotation.Message;

/**
 * ========================================================
 * 日 期：2016年4月23日 下午2:10:40
 * 作 者：qijiabin
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
@Message
public class Userinfo {

	private String name;
	private int age;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "Userinfo [name=" + name + ", age=" + age + "]";
	}
	
}
