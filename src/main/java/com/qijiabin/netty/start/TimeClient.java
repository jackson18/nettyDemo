package com.qijiabin.netty.start;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * ========================================================
 * 日 期：2016年4月22日 下午2:32:41
 * 作 者：qijiabin
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class TimeClient {
	
	public static void main(String[] args) {
		new TimeClient().connect(8888, "localhost");
	}

	public void connect(int port, String host) {
		//配置客户端线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					// 半包处理【基于换行符】
					socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
					// 字符串编码
					socketChannel.pipeline().addLast(new StringDecoder());
					// 字符串解码
					socketChannel.pipeline().addLast(new StringEncoder());
					// 在管道中添加我们自己的接收数据实现方法
					socketChannel.pipeline().addLast(new TimeClientHandler());
				}
			});
			
			//发起异步连接操作
			ChannelFuture f = b.connect(host, port).sync();
			
			//等待客户端链接关闭
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			//释放资源
			group.shutdownGracefully();
		}
	}
	
}
