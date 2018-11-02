package com.cloud.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.bean.Book;
import com.cloud.bean.Customer;
import com.cloud.common.search.bean.Page;
import com.cloud.common.search.bean.SearchRequestData;
import com.cloud.common.search.dao.EsDao;
import com.cloud.common.search.exception.SearchException;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月28日
 */
@RestController
public class BookController3
{
    @Autowired
    private EsDao esClient;
    
    
    @RequestMapping("/matchAll")
    public Object matchAll() throws SearchException{
        
        SearchRequestData searchRequestData = new SearchRequestData();
        return esClient.matchAllQuery(searchRequestData, Book.class);
    }

    @RequestMapping("/queryStringQuery")
    public Object queryStringQuery(String value) throws SearchException{
        SearchRequestData searchRequestData = new SearchRequestData();
        searchRequestData.setQueryString(value);
        return esClient.queryStringQuery(searchRequestData, Book.class);
    }
    
    @RequestMapping("/matchQuery")
    public Object matchQuery(String key, String value) throws SearchException{
        SearchRequestData searchRequestData = new SearchRequestData();
        searchRequestData.setIndexName("customer");
        searchRequestData.setTypeName("doc");
        searchRequestData.setFieldName(key);
        searchRequestData.setQueryString(value);
        return esClient.matchQuery(searchRequestData, Customer.class);
    }
    
    @RequestMapping("/matchPhraseQuery")
    public Object matchPhraseQuery(String key, String value) throws SearchException{
        SearchRequestData searchRequestData = new SearchRequestData();
        searchRequestData.setFieldName(key);
        searchRequestData.setQueryString(value);
        return esClient.matchPhraseQuery(searchRequestData, Book.class, true);
    }
    
    @RequestMapping("/termQuery")
    public Object termQuery(String key, String value) throws SearchException{
        SearchRequestData searchRequestData = new SearchRequestData();
        searchRequestData.setFieldName(key);
        searchRequestData.setQueryString(value);
        return esClient.termQuery(searchRequestData, Book.class);
    }
    
    @RequestMapping("/multiMatchQuery")
    public Object multiMatchQuery(String key, String value) throws SearchException{
        SearchRequestData searchRequestData = new SearchRequestData();
        
        List<String> list = new ArrayList<>();
        list.add("name");
        list.add("message");
        searchRequestData.setFields(list);
        searchRequestData.setQueryString(value);
        return esClient.multiMatchQuery(searchRequestData, Book.class);
    }
    
    @RequestMapping("/queryHystrixInfo/{value}/{pageSize}/{pageNo}")
    public Object queryHystrixInfo(@PathVariable String value,
            @PathVariable int pageSize, @PathVariable int pageNo) throws SearchException{
        SearchRequestData searchRequestData = new SearchRequestData();
        searchRequestData.setIndexName("hystrix-2018-10-29");
        searchRequestData.setQueryString(value);
        searchRequestData.setTypeName("hystrix");
        Page page = new Page(pageSize, pageNo);
        searchRequestData.setPage(page);
        return esClient.queryStringQuery(searchRequestData, Object.class);
    }
    
   /* 
    @RequestMapping("/boolQuery")
    public Object boolQuery(){
        SearchRequestData searchRequestData = new SearchRequestData();
        return elasticSearchClient.boolQuery(searchRequestData, Book.class);
    }*/
    
}
