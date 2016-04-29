package com.qijiabin.netty.sendFile.client;

import java.io.RandomAccessFile;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.stream.ChunkedFile;

/**
 * ========================================================
 * 日 期：2016年4月29日 上午11:31:11
 * 作 者：qijiabin
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class FileControl {

    private ChannelHandlerContext channel;

    public FileControl(ChannelHandlerContext channel) {
        this.channel = channel;
    }

    /**
     * 传送文件
     * @param fileUrl
     */
    public void sendFile(String fileUrl) {
        // 开始执行
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(fileUrl, "rw");
            ChunkedFile chunkedFile = new ChunkedFile(raf);
            channel.writeAndFlush(chunkedFile).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    future.channel().close();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}