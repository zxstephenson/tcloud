/**
 * <p>文件名称: CountDownLatchHelper.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.core.utils.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class CountDownLatchHelper<T>
{

    private boolean isExecute = false;
    private int count;
    private List<T> data;
    private List<CountDownThread<T>> list;
    private CountDownLatch end;
    private ExecutorService threadPool = null;
    private List<IExecute<T>> executes = null;

    public CountDownLatchHelper()
    {
        threadPool = Executors.newFixedThreadPool(10);
        executes = new ArrayList<IExecute<T>>();
        data = Collections.synchronizedList(new ArrayList<T>());
        list = new ArrayList<CountDownThread<T>>();
    }

    public CountDownLatchHelper<T> addExecute(IExecute<T> execute)
    {
        executes.add(execute);
        return this;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public CountDownLatchHelper<T> execute()
    {
        this.count = executes.size();
        if (this.count > 0)
        {
            end = new CountDownLatch(count);
            for (IExecute<T> countDown : executes)
            {
                //为每一个IExecute创建一个线程执行
                CountDownThread countDownThread = new CountDownThread(threadPool, data, countDown, end);
                countDownThread.execute();
            }
            try
            {
                //等待所有子线程执行完成后继续执行
                end.await();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        threadPool.shutdown();
        isExecute = true;
        return this;
    }

    public List<T> getData()
    {
        if (!isExecute)
            throw new RuntimeException("no execute !");
        return data;
    }

}
