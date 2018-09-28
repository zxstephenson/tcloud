package com.cloud.common.search.bean;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月27日
 */

public class Page
{
    private int pageNo = 0;
    
    private int pageSize = 20;

    /**
     * @return the pageNo
     */
    public int getPageNo()
    {
        return pageNo;
    }

    /**
     * @param pageNo the pageNo to set
     */
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize()
    {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }


}
