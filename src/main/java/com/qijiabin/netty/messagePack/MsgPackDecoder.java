package com.qijiabin.netty.messagePack;

import java.util.List;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * ========================================================
 * 日 期：2016年4月23日 下午12:14:02
 * 作 者：qijiabin
 * 版 本：1.0.0
 * 类说明：解码器
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class MsgPackDecoder extends MessageToMessageDecoder<ByteBuf> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
		int length = buf.readableBytes();
		byte[] data = new byte[length];
		// 获取要解码的byte数组
		buf.getBytes(buf.readerIndex(), data, 0, length);
		MessagePack messagePack = new MessagePack();
		// read方法将其反序列化为object对象
		list.add(messagePack.read(data));
	}

}
