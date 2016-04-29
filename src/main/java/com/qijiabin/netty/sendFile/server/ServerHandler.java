package com.qijiabin.netty.sendFile.server;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * ========================================================
 * 日 期：2016年4月29日 下午12:06:13
 * 作 者：qijiabin
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class ServerHandler extends ChannelHandlerAdapter {
	
	private boolean first;
	private FileOutputStream fos;
    private BufferedOutputStream bufferedOutputStream;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		first = true;
		System.out.println(ctx.channel().localAddress());
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 第一次接收信息只创建文件
        if (first) {
        	System.out.println("创建文件");
        	first = false;
        	File file = new File("F://test" + new SimpleDateFormat("yyyymmddhhmmss").format(new Date()) + ".zip");
        	if (!file.exists()) {
        		try {
        			file.createNewFile();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        	}
        	try {
        		fos = new FileOutputStream(file);
        		bufferedOutputStream = new BufferedOutputStream(fos);
        	} catch (FileNotFoundException e) {
        		e.printStackTrace();
        	}
        }

        // 开始处理文件信息
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        System.out.println("本次接收内容长度：" + msg.toString().length());
        try {
            bufferedOutputStream.write(bytes, 0, bytes.length);
            buf.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		// 关闭流
		bufferedOutputStream.flush();
		bufferedOutputStream.close();
        first = false;
    }

	
}
