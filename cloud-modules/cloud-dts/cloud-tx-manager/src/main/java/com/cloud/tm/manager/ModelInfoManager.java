/**
 * <p>文件名称: ModelInfoManager.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.manager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.cloud.tm.model.ModelInfo;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class ModelInfoManager
{

    private List<ModelInfo> modelInfos = new CopyOnWriteArrayList<ModelInfo>();

    private static ModelInfoManager manager = null;

    public static ModelInfoManager getInstance()
    {
        if (manager == null)
        {
            synchronized (ModelInfoManager.class)
            {
                if (manager == null)
                {
                    manager = new ModelInfoManager();
                }
            }
        }
        return manager;
    }

    public void removeModelInfo(String channelName)
    {
        for (ModelInfo modelInfo : modelInfos)
        {
            if (channelName.equalsIgnoreCase(modelInfo.getChannelName()))
            {
                modelInfos.remove(modelInfo);
            }
        }
    }

    public void addModelInfo(ModelInfo minfo)
    {
        for (ModelInfo modelInfo : modelInfos)
        {
            if (minfo.getChannelName()
                    .equalsIgnoreCase(modelInfo.getChannelName()))
            {
                return;
            }

            if (minfo.getIpAddress().equalsIgnoreCase(modelInfo.getIpAddress()))
            {
                return;
            }
        }
        modelInfos.add(minfo);
    }

    public List<ModelInfo> getOnlines()
    {
        return modelInfos;
    }

    public ModelInfo getModelByChannelName(String channelName)
    {
        for (ModelInfo modelInfo : modelInfos)
        {
            if (channelName.equalsIgnoreCase(modelInfo.getChannelName()))
            {
                return modelInfo;
            }
        }
        return null;
    }

    public ModelInfo getModelByModel(String model)
    {
        for (ModelInfo modelInfo : modelInfos)
        {
            if (model.equalsIgnoreCase(modelInfo.getModel()))
            {
                return modelInfo;
            }
        }
        return null;
    }
}
