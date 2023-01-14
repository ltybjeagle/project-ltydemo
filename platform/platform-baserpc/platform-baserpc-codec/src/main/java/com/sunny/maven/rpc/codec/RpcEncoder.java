package com.sunny.maven.rpc.codec;

import com.sunny.maven.rpc.common.utils.SerializationUtils;
import com.sunny.maven.rpc.protocol.RpcProtocol;
import com.sunny.maven.rpc.protocol.header.RpcHeader;
import com.sunny.maven.rpc.serialization.api.Serialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author SUNNY
 * @description: 实现RPC编码
 * @create: 2022-12-28 17:23
 */
public class RpcEncoder extends MessageToByteEncoder<RpcProtocol<Object>> implements RpcCodec {
    @Override
    protected void encode(ChannelHandlerContext ctx, RpcProtocol<Object> msg, ByteBuf byteBuf) throws Exception {
        RpcHeader header = msg.getHeader();
        byteBuf.writeShort(header.getMagic());
        byteBuf.writeByte(header.getMsgType());
        byteBuf.writeByte(header.getStatus());
        byteBuf.writeLong(header.getRequestId());
        String serializationType = header.getSerializationType();
        // TODO Serialization是扩展点
        Serialization serialization = getSerialization(serializationType);
        byteBuf.writeBytes(SerializationUtils.paddingString(serializationType).getBytes("UTF-8"));
        byte[] data = serialization.serialize(msg.getBody());
        byteBuf.writeInt(data.length);
        byteBuf.writeBytes(data);
    }
}
