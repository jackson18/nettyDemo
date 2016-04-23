package com.qijiabin.netty.messagePack;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * ========================================================
 * 日 期：2016年4月23日 下午2:22:42
 * 作 者：qijiabin
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class EchoServer {

	public static void main(String[] args) {
		new EchoServer().bind(8888);
	}
	
	public void bind(int port) {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("frameDecoder", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
					ch.pipeline().addLast("msgpack decode", new MsgPackDecoder());
					ch.pipeline().addLast("frameIncoder", new LengthFieldPrepender(2));
					ch.pipeline().addLast("msgpack encode", new MsgPackEncoder());
					ch.pipeline().addLast(new EchoServerHandler());
				}
			});
			
			//绑定端口，同步等待成功
			ChannelFuture f = b.bind(port).sync();
			System.out.println("the server is listen on " + port);
			
			//等待服务端监听端口关闭
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
}
