package com.qijiabin.netty.sendFile.client;

import java.util.Date;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * ========================================================
 * 日 期：2016年4月29日 上午10:52:16
 * 作 者：qijiabin
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class SendFileClient {

	public static void main(String[] args) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				new SendFileClient().connect("localhost", 8888);
			}
		}).start();
		
		while (null == Globle.channel) {
            try {
                Thread.sleep(1000);
                System.out.println("等待socket握手... ...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		
		System.out.println("发送文件 开始" + new Date());
        String fileUrl = "F:\\apache-maven-3.3.9-bin.zip";
        FileControl fileControl = new FileControl(Globle.channel);
        // 开始发文件
        fileControl.sendFile(fileUrl);
        System.out.println("发送文件 结束" + new Date());
	}
	
	private void connect(String host, int port) {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new ByteArrayDecoder());
					ch.pipeline().addLast(new ChunkedWriteHandler());
					ch.pipeline().addLast(new ClientHandler());
				}
			});
			
			ChannelFuture f = b.connect(host, port);
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}
	
}
