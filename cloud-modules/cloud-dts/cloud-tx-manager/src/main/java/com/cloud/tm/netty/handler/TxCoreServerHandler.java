/**
 * <p>文件名称: TxCoreServerHandler.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.netty.handler;

import com.alibaba.fastjson.JSONObject;
import com.cloud.tm.framework.utils.SocketManager;
import com.cloud.tm.framework.utils.SocketUtils;
import com.cloud.tm.manager.ModelInfoManager;
import com.cloud.tm.netty.service.IActionService;
import com.cloud.tm.netty.service.NettyService;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */

@ChannelHandler.Sharable
public class TxCoreServerHandler extends ChannelInboundHandlerAdapter
{ // (1)

    private NettyService nettyService;

    private static final Logger log = LoggerFactory.getLogger(TxCoreServerHandler.class);

    private Executor threadPool;

    public TxCoreServerHandler(Executor threadPool, NettyService nettyService)
    {
        this.threadPool = threadPool;
        this.nettyService = nettyService;
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg)
            throws Exception
    {
        final String json = SocketUtils.getJson(msg);
        log.debug("request->" + json);
        threadPool.execute(new Runnable()
        {
            @Override
            public void run()
            {
                service(json, ctx);
            }
        });
    }

    private void service(String json, ChannelHandlerContext ctx)
    {
        if (StringUtils.isNotEmpty(json))
        {
            JSONObject jsonObject = JSONObject.parseObject(json);
            String action = jsonObject.getString("a");
            String key = jsonObject.getString("k");
            JSONObject params = JSONObject
                    .parseObject(jsonObject.getString("p"));
            String channelAddress = ctx.channel().remoteAddress().toString();

            IActionService actionService = nettyService.getActionService(action);

            String res = actionService.execute(channelAddress, key, params);

            JSONObject resObj = new JSONObject();
            resObj.put("k", key);
            resObj.put("d", res);

            SocketUtils.sendMsg(ctx, resObj.toString());

        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception
    {

        // 是否到达最大上线连接数
        if (SocketManager.getInstance().isAllowConnection())
        {
            SocketManager.getInstance().addClient(ctx.channel());
        } else
        {
            ctx.close();
        }
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception
    {

        SocketManager.getInstance().removeClient(ctx.channel());
        String modelName = ctx.channel().remoteAddress().toString();
        SocketManager.getInstance().outLine(modelName);

        ModelInfoManager.getInstance().removeModelInfo(modelName);
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception
    {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception
    {
        cause.printStackTrace();
        // ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
            throws Exception
    {
        // 心跳配置
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass()))
        {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE)
            {
                ctx.close();
            }
        }
    }

}