package com.qijiabin.netty.sendFile.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * ========================================================
 * 日 期：2016年4月29日 上午11:00:25
 * 作 者：qijiabin
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class ClientHandler extends ChannelHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Globle.channel = ctx;
		System.out.println(ctx.channel().localAddress());
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println(msg);
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Globle.channel = null;
	}
	
}
