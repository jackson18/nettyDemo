package com.qijiabin.netty.messagePack;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * ========================================================
 * 日 期：2016年4月23日 下午12:08:36
 * 作 者：qijiabin
 * 版 本：1.0.0
 * 类说明：编码器
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class MsgPackEncoder extends MessageToByteEncoder<Object>{

	@Override
	protected void encode(ChannelHandlerContext ctx, Object obj, ByteBuf buf) throws Exception {
		MessagePack messagePack = new MessagePack();
		// 将对象编码为byte数组
		byte[] data = messagePack.write(obj);
		buf.writeBytes(data);
	}

}
