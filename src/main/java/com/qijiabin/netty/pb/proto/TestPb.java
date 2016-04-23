package com.qijiabin.netty.pb.proto;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * ========================================================
 * 日 期：2016年4月23日 下午4:25:49
 * 作 者：qijiabin
 * 版 本：1.0.0
 * 类说明：
 * TODO
 * ========================================================
 * 修订日期     修订人    描述
 */
public class TestPb {

	public static void main(String[] args) throws InvalidProtocolBufferException {
		SubscribeReqProto.SubscribeReq req = createBean();
		System.out.println("编码前： " + req.toString());
		SubscribeReqProto.SubscribeReq newReq = decode(encode(req));
		System.out.println("解码后： " + newReq.toString());
		System.out.println("结果： " + req.equals(newReq));
	}
	
	private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
		return req.toByteArray();
	}
	
	private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
		return SubscribeReqProto.SubscribeReq.parseFrom(body);
	}
	
	private static SubscribeReqProto.SubscribeReq createBean() {
		SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
		builder.setSubReqID(1);
		builder.setUserName("jackson");
		builder.setAddress("xian");
		return builder.build();
	}
	
}
