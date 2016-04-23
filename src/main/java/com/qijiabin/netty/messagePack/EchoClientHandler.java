package com.qijiabin.netty.messagePack;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * ========================================================
 * 日 期：2016年4月23日 下午2:08:02
 * 作 者：qijiabin
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class EchoClientHandler extends ChannelHandlerAdapter {

	private int sendNumber;
	
	public EchoClientHandler(int sendNumber) {
		this.sendNumber = sendNumber;
	}
	
	/**
	 * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。
	 * 也就是客户端与服务端建立了通信通道并且可以传输数据
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Userinfo[] users = getUserinfos();
		for (Userinfo user : users) {
			ctx.write(user);
		}
		ctx.flush();
	}
	
	/**
	 * 简而言之就是从通道中读取数据，也就是服务端接收客户端发来的数据
	 * 但是这个数据在不进行解码时它是ByteBuf类型
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("客户端收到 : " + msg);
		ctx.write(msg);
	}
	
	/**
	 * 在通道读取完成后会在这个方法里通知，对应可以做刷新操作
	 */
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	private Userinfo[] getUserinfos() {
		Userinfo[] users = new Userinfo[sendNumber];
		Userinfo user = null;
		for (int i = 1; i <= sendNumber; i++) {
			user = new Userinfo();
			user.setAge(i);
			user.setName("tom " + i);
			users[i-1] = user;
		}
		return users;
	}
	
}
