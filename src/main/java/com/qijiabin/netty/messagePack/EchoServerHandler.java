package com.qijiabin.netty.messagePack;

import java.util.Random;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * ========================================================
 * 日 期：2016年4月23日 下午2:36:50
 * 作 者：qijiabin
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class EchoServerHandler extends ChannelHandlerAdapter{

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println(ctx.channel().localAddress() + " channelActive");
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("服务端收到： " + msg);
		
		Userinfo user = new Userinfo();
		user.setAge(new Random().nextInt(30));
		user.setName("jack");
		ctx.writeAndFlush(user);
	}
	
}
