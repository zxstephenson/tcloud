/**
 * <p>文件名称: SocketUtils.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.framework.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class SocketUtils
{

    public static String getJson(Object msg)
    {
        String json;
        try
        {
            ByteBuf buf = (ByteBuf) msg;
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            json = new String(bytes);
        } finally
        {
            ReferenceCountUtil.release(msg);
        }
        return json;

    }

    public static void sendMsg(ChannelHandlerContext ctx, String msg)
    {
        ctx.writeAndFlush(Unpooled.buffer().writeBytes(msg.getBytes()));
    }

    public static void sendMsg(Channel ctx, String msg)
    {
        ctx.writeAndFlush(Unpooled.buffer().writeBytes(msg.getBytes()));
    }
}
