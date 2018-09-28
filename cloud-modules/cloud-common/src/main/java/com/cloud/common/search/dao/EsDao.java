package com.cloud.common.search.dao;

import java.util.List;

import com.cloud.common.search.bean.SearchRequestData;
import com.cloud.common.search.exception.SearchException;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月28日
 */

public interface EsDao extends SearchDao
{
    public <T> List<T> queryStringQuery(SearchRequestData searchRequestData, 
            Class<T> responseType) throws SearchException;
    
    public <T> List<T> matchAllQuery(SearchRequestData searchRequestData, 
            Class<T> responseType) throws SearchException;

    public <T> List<T> matchQuery(SearchRequestData searchRequestData,
            Class<T> responseType) throws SearchException;
    
    public <T> List<T> matchPhraseQuery(SearchRequestData searchRequestData, 
            Class<T> responseType) throws SearchException;

    public <T> List<T> matchPhraseQuery(SearchRequestData searchRequestData, 
            Class<T> responseType, boolean ignoreWordTurn) throws SearchException;
    
    public <T> List<T> termQuery(SearchRequestData searchRequestData, 
            Class<T> responseType) throws SearchException;
    
    public <T> List<T> multiMatchQuery(SearchRequestData searchRequestData, 
            Class<T> responseType) throws SearchException;
    
    public <T> List<T> boolQuery(SearchRequestData searchRequestData, 
            String queryString, Class<T> responseType) throws SearchException;
    
    /*public <T> List<T> query(final SearchRequestData searchRequestData, 
            QueryBuilder queryBuilder, Class<T> responseType) throws SearchException;
    
    public <T> List<T> query(final SearchRequestData searchRequestData, 
            QueryBuilder queryBuilder, Class<T> responseType, QueryBuilder filterBuilder) throws SearchException;
     */
    
}

