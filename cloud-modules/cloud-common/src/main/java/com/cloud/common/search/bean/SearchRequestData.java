package com.cloud.common.search.bean;

import java.util.List;

import com.cloud.common.enums.Sort;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月27日
 */

public class SearchRequestData
{
    private String indexName;
    
    private String typeName;
    
    private String fieldName;
    
    private String queryString;
    
    private List<String> fields;
    
    private String sortFieldName;

    private Sort order;
    
    private Page page = new Page();

    private FilterCondition filterCondition;
    
    /**
     * @return the indexName
     */
    public String getIndexName()
    {
        return indexName;
    }

    /**
     * @param indexName the indexName to set
     */
    public void setIndexName(String indexName)
    {
        this.indexName = indexName;
    }

    /**
     * @return the typeName
     */
    public String getTypeName()
    {
        return typeName;
    }

    /**
     * @param typeName the typeName to set
     */
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    /**
     * @return the fieldName
     */
    public String getFieldName()
    {
        return fieldName;
    }

    /**
     * @param fieldName the fieldName to set
     */
    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    /**
     * @return the queryString
     */
    public String getQueryString()
    {
        return queryString;
    }

    /**
     * @param queryString the queryString to set
     */
    public void setQueryString(String queryString)
    {
        this.queryString = queryString;
    }

    /**
     * @return the fields
     */
    public List<String> getFields()
    {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(List<String> fields)
    {
        this.fields = fields;
    }

    /**
     * @return the sortFieldName
     */
    public String getSortFieldName()
    {
        return sortFieldName;
    }

    /**
     * @param sortFieldName the sortFieldName to set
     */
    public void setSortFieldName(String sortFieldName)
    {
        this.sortFieldName = sortFieldName;
    }

    /**
     * @return the order
     */
    public Sort getOrder()
    {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(Sort order)
    {
        this.order = order;
    }

    /**
     * @return the page
     */
    public Page getPage()
    {
        return page;
    }

    /**
     * @param page the page to set
     */
    public void setPage(Page page)
    {
        this.page = page;
    }

    /**
     * @return the filterCondition
     */
    public FilterCondition getFilterCondition()
    {
        return filterCondition;
    }

    /**
     * @param filterCondition the filterCondition to set
     */
    public void setFilterCondition(FilterCondition filterCondition)
    {
        this.filterCondition = filterCondition;
    }
    
}
