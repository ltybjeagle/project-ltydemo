package com.liuty.maven.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class HandlerInitializer extends ChannelInitializer<Channel> {
    private static final byte SPACE = (byte) ' ';
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(new CmdDecoder(64 * 1024));
        pipeline.addLast(new CmdHandler());
        // 按报文长度分数据帧 报文头存储报文长度
        pipeline.addLast(new LengthFieldBasedFrameDecoder(64 * 1024
                , 0, 8));
        // 每分钟检测连接情况, 可以关闭不活动的连接
        pipeline.addLast(new IdleStateHandler(0, 0, 60
                , TimeUnit.SECONDS));
        pipeline.addLast(new HandlerInitializer.HeartbeatHandler());
    }

    public static final class Cmd {
        private final ByteBuf name;
        private final ByteBuf args;
        public Cmd(ByteBuf name, ByteBuf args) {
            this.name = name;
            this.args = args;
        }
        public ByteBuf name() {
            return name;
        }
        public ByteBuf args() {
            return args;
        }
    }
    // 按特殊字符分数据帧
    public static final class CmdDecoder extends LineBasedFrameDecoder {
        public CmdDecoder(int maxLength) {
            super(maxLength);
        }
        @Override
        protected Object decode(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
            ByteBuf frame = (ByteBuf) super.decode(ctx, byteBuf);
            if (frame == null) {
                return null;
            }
            int index = frame.indexOf(frame.readerIndex(), frame.writerIndex(), SPACE);
            return new Cmd(frame.slice(frame.readerIndex(), index)
                    , frame.slice(index + 1, frame.writerIndex()));
        }
    }
    public static final class CmdHandler extends SimpleChannelInboundHandler<Cmd> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Cmd cmd) throws Exception {

        }
    }
    // 绝对值解析器
    public static final class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {
        @Override
        protected void encode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list)
                throws Exception {
            while (byteBuf.readableBytes() >= 4) {
                int value = Math.abs(byteBuf.readInt());
                list.add(value);
            }
        }
    }
    // 按报文长度解析信息
    public static final class FixedLengthFrameDecoder extends ByteToMessageDecoder {
        private final int frameLength;
        public FixedLengthFrameDecoder(int frameLength) {
            if (frameLength <= 0) {
                throw new IllegalArgumentException(
                        "frameLength must be a positive integer: " + frameLength);
            }
            this.frameLength = frameLength;
        }
        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list)
                throws Exception {
            while (byteBuf.readableBytes() >= frameLength) {
                ByteBuf buf = byteBuf.readBytes(frameLength);
                list.add(buf);
            }
        }
    }
    // 心跳检测 处理器
    public static final class HeartbeatHandler extends ChannelInboundHandlerAdapter {
        private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled.unreleasableBuffer(
                Unpooled.copiedBuffer("HEARTBEAT", CharsetUtil.ISO_8859_1));
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateHandler) {
                ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
                        .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            } else {
                super.userEventTriggered(ctx, evt);
            }
        }
    }
}
