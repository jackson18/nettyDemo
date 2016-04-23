package com.qijiabin.netty.start;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * ========================================================
 * 日 期：2016年4月22日 下午2:39:59
 * 作 者：qijiabin
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

	private byte[] req;
	private int count;
	
	public TimeClientHandler() {
		//请求数据加上换行
		String msg = "hello world" + System.getProperty("line.separator");
		req = msg.getBytes();
	}
	
	/**
	 * 当客户端主动链接服务端的链接后，这个通道就是活跃的了。
	 * 也就是客户端与服务端建立了通信通道并且可以传输数据
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		ByteBuf buf = null;
		for (int i = 0; i < 100; i++) {
			buf = Unpooled.buffer(req.length);
			buf.writeBytes(req);
			ctx.writeAndFlush(buf);
		}
	}
	
	/**
	 * 简而言之就是从通道中读取数据，也就是服务端接收客户端发来的数据
	 * 但是这个数据在不进行解码时它是ByteBuf类型
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String) msg;
		System.out.println("result: " + body + ",count: " + ++count);
	}
	
	/**
	 * 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		//释放资源
		ctx.close();
		System.out.println("异常信息：\r\n"+cause.getMessage());
	}
	
}
