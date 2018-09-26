package com.cloud.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月25日
 */
@RestController
public class BookController2
{
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    /**
     * 查询所有
     * @return
     */
    @GetMapping("/all")
    public List<Map<String, Object>> searchAll(){
        Client client = elasticsearchTemplate.getClient();
        
        SearchRequestBuilder srb = client.prepareSearch("product").setTypes("book");
        SearchResponse searchResponse = srb.setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();
        SearchHits hits = searchResponse.getHits();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for(SearchHit hit : hits){
            Map<String, Object> source = hit.getSource();
            list.add(source);
            System.out.println(hit.getSourceAsString());
        }
        
        return list;
    }
    
}
