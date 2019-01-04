/**
 * <p>文件名称: TxInfo.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.netty.model;

import com.alibaba.fastjson.JSONObject;
import com.cloud.core.model.JsonModel;
import com.cloud.tm.model.ChannelSender;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class TxInfo extends JsonModel
{

    /**
     * 任务唯一标示
     */
    private String kid;

    /**
     * 模块管道名称（netty管道名称）
     */
    private String channelAddress;

    /**
     * 是否通知成功
     */
    private int notify;

    /**
     * 0 不组合 1 组合
     */
    private int isGroup;

    /**
     * tm识别标示
     */
    private String address;

    /**
     * tx识别标示
     */
    private String uniqueKey;

    /**
     * 管道发送数据
     */
    private ChannelSender channel;

    /**
     * 业务方法名称
     */
    private String methodStr;

    /**
     * 模块名称
     */
    private String model;

    /**
     * 模块地址
     */
    private String modelIpAddress;

    /**
     * 是否提交（临时数据）
     */
    private int isCommit;

    public int getIsCommit()
    {
        return isCommit;
    }

    public void setIsCommit(int isCommit)
    {
        this.isCommit = isCommit;
    }

    public String getMethodStr()
    {
        return methodStr;
    }

    public void setMethodStr(String methodStr)
    {
        this.methodStr = methodStr;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public String getModelIpAddress()
    {
        return modelIpAddress;
    }

    public void setModelIpAddress(String modelIpAddress)
    {
        this.modelIpAddress = modelIpAddress;
    }

    public String getKid()
    {
        return kid;
    }

    public void setKid(String kid)
    {
        this.kid = kid;
    }

    public ChannelSender getChannel()
    {
        return channel;
    }

    public void setChannel(ChannelSender channel)
    {
        this.channel = channel;
    }

    public String getChannelAddress()
    {
        return channelAddress;
    }

    public void setChannelAddress(String channelAddress)
    {
        this.channelAddress = channelAddress;
    }

    public int getNotify()
    {
        return notify;
    }

    public void setNotify(int notify)
    {
        this.notify = notify;
    }

    public int getIsGroup()
    {
        return isGroup;
    }

    public void setIsGroup(int isGroup)
    {
        this.isGroup = isGroup;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getUniqueKey()
    {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey)
    {
        this.uniqueKey = uniqueKey;
    }

    @Override
    public String toString()
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("kid", getKid());
        jsonObject.put("channelAddress", getChannelAddress());
        jsonObject.put("notify", getNotify());
        jsonObject.put("isGroup", getIsGroup());
        jsonObject.put("address", getAddress());
        jsonObject.put("uniqueKey", getUniqueKey());

        jsonObject.put("model", getModel());
        jsonObject.put("modelIpAddress", getModelIpAddress());
        jsonObject.put("methodStr", getMethodStr());

        return jsonObject.toString();
    }
}
