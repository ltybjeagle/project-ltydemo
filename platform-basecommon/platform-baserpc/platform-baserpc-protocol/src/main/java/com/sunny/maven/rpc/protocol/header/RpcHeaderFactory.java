package com.sunny.maven.rpc.protocol.header;

import com.sunny.maven.rpc.common.id.IdFactory;
import com.sunny.maven.rpc.constants.RpcConstants;
import com.sunny.maven.rpc.protocol.enumeration.RcpType;

/**
 * @author SUNNY
 * @description: RpcHeaderFactory
 * @create: 2022-12-27 17:29
 */
public class RpcHeaderFactory {
    public static RpcHeader getRequestHeader(String serializationType) {
        RpcHeader header = new RpcHeader();
        long requestId = IdFactory.getId();
        header.setMagic(RpcConstants.MAGIC);
        header.setRequestId(requestId);
        header.setMsgType((byte) RcpType.REQUEST.getType());
        header.setStatus((byte) 0x1);
        header.setSerializationType(serializationType);
        return header;
    }
}
