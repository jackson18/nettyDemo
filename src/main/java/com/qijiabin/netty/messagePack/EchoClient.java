package com.qijiabin.netty.messagePack;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * ========================================================
 * 日 期：2016年4月23日 下午12:26:44
 * 作 者：qijiabin
 * 版 本：1.0.0
 * 类说明：MessagePack编解码
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class EchoClient {
	
	private String host;
	private int port;
	private int sendNumber;
	
	public EchoClient(String host, int port, int sendNumber) {
		this.host = host;
		this.port = port;
		this.sendNumber = sendNumber;
	}

	public static void main(String[] args) {
		new EchoClient("localhost", 8888, 100).connect();;
	}
	
	public void connect() {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("frameDecode", new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));
					ch.pipeline().addLast("msgpack decode", new MsgPackDecoder());
					ch.pipeline().addLast("frameEncode", new LengthFieldPrepender(2));
					ch.pipeline().addLast("msgpack encode", new MsgPackEncoder());
					ch.pipeline().addLast(new EchoClientHandler(sendNumber));
				}
			});
			
			//发起异步连接操作
			ChannelFuture f = b.connect(host, port).sync();
			
			//等待客户端连接关闭
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}
	
}
