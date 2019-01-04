/**
 * <p>文件名称: CountDownThread.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.core.utils.thread;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class CountDownThread<T> implements Runnable
{

    private ExecutorService threadPool;

    private CountDownLatch currentThread;

    private IExecute<T> execute;

    private List<T> list;
    
    public CountDownThread(ExecutorService threadPool, List<T> list,
            IExecute<T> execute, CountDownLatch currentThread)
    {
        this.threadPool = threadPool;
        this.list = list;
        this.execute = execute;
        this.currentThread = currentThread;
    }

    @Override
    public void run()
    {
        list.add(execute.execute());
        currentThread.countDown();
    }

    public void execute()
    {
        threadPool.execute(this);
    }

}
