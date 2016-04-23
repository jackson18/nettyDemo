package com.qijiabin.netty.pb;

import com.qijiabin.netty.pb.proto.SubscribeReqProto;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * ========================================================
 * 日 期：2016年4月23日 下午5:21:45
 * 作 者：qijiabin
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class ReqClientHandler extends ChannelHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 1; i < 10; i++) {
			ctx.write(createReq(i));
		}
		ctx.flush();
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("客户端收到： " + msg);
	}
	
	private SubscribeReqProto.SubscribeReq createReq(int id) {
		SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
		builder.setSubReqID(id);
		builder.setUserName("jack");
		builder.setAddress("xian");
		return builder.build();
	}
	
}
