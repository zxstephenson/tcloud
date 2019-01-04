/**
 * <p>文件名称: Page.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.core.model;

import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */

public class Page<T> extends JsonModel
{
    private int total;
    private Collection<T> rows;
    private int nowPage;
    private int pageNumber;
    private int pageSize;

    public int getTotal()
    {
        return this.total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    public Collection<T> getRows()
    {
        return this.rows;
    }

    public void setRows(Collection<T> rows)
    {
        this.rows = rows;
    }

    public int getNowPage()
    {
        return this.nowPage;
    }

    public void setNowPage(int nowPage)
    {
        this.nowPage = nowPage;
    }

    public int getPageNumber()
    {
        return this.pageNumber;
    }

    public void setPageNumber(int pageNumber)
    {
        this.pageNumber = pageNumber;
    }

    public int getPageSize()
    {
        return this.pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public String toRowsJsonString()
    {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("total", total);
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        if (rows != null && rows.size() > 0)
        {
            for (T t : rows)
            {
                if (t instanceof BaseEntity)
                {
                    maps.add(((BaseEntity) t).toMap());
                } else if (t instanceof Map)
                {
                    maps.add((Map) t);
                }
            }
        }
        data.put("rows", maps);
        return JSONObject.toJSONString(data);
    }
}
