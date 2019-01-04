/**
 * <p>文件名称: Task.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.core.utils.task;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class Task
{

    private Lock lock;

    private Condition condition;

    private volatile IBack back;

    private Object obj;

    private volatile IBack execute;

    private volatile boolean hasExecute = false;

    /**
     * 是否被唤醒
     */
    private volatile boolean isNotify = false;

    /**
     * 是否执行等待
     */
    private volatile boolean isAwait = false;

    /**
     * 唯一标示key
     */
    private String key;

    /**
     * 数据状态用于业务处理
     */
    private volatile int state = 0;

    /**
     * 是否被唤醒
     * 
     * @return true 是，false，否
     */
    public boolean isNotify()
    {
        return isNotify;
    }

    /**
     * 是否被移除 true 是 false 否
     * 
     * @return
     */
    public boolean isRemove()
    {
        return !ConditionUtils.getInstance().hasKey(getKey());
    }

    public boolean isAwait()
    {
        return isAwait;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public IBack getBack()
    {
        return back;
    }

    public void setBack(IBack back)
    {
        this.back = back;
    }

    protected Task()
    {
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public synchronized Object execute(IBack back)
    {
        try
        {
            execute = back;
            hasExecute = true;
            executeSignalTask();
            while (execute != null && !Thread.currentThread().interrupted())
            {
                try
                {
                    TimeUnit.MILLISECONDS.sleep(1);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            return obj;
        } finally
        {
            obj = null;
        }
    }

    public void remove()
    {
        ConditionUtils.getInstance().removeKey(getKey());
    }

    private void executeSignalTask()
    {
        while (!isAwait && !Thread.currentThread().interrupted())
        {
            try
            {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            lock.lock();
            condition.signal();
        } finally
        {
            lock.unlock();
        }
    }

    public void signalTask()
    {
        while (hasExecute && !Thread.currentThread().interrupted())
        {
            try
            {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        while (!isAwait && !Thread.currentThread().interrupted())
        {
            try
            {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            lock.lock();
            isNotify = true;
            condition.signal();
        } finally
        {
            lock.unlock();
        }
    }

    public void signalTask(IBack back)
    {
        while (hasExecute && !Thread.currentThread().interrupted())
        {
            try
            {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        while (!isAwait && !Thread.currentThread().interrupted())
        {
            try
            {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            lock.lock();
            isNotify = true;
            try
            {
                back.doing();
            } catch (Throwable e)
            {
            }
            condition.signal();
        } finally
        {
            lock.unlock();
        }
    }

    private void waitTask() throws Throwable
    {
        condition.await();
        if (hasExecute)
        {
            try
            {
                obj = execute.doing();
            } catch (Throwable e)
            {
                obj = e;
            }
            hasExecute = false;
            execute = null;
            waitTask();
        }
    }

    public void awaitTask()
    {
        try
        {
            lock.lock();
            isAwait = true;
            waitTask();
        } catch (Throwable e)
        {
        } finally
        {
            lock.unlock();
        }
    }

    public void awaitTask(IBack back)
    {
        try
        {
            lock.lock();
            try
            {
                back.doing();
            } catch (Throwable e)
            {
            }
            isAwait = true;
            waitTask();
        } catch (Throwable e)
        {
        } finally
        {
            lock.unlock();
        }
    }
}
