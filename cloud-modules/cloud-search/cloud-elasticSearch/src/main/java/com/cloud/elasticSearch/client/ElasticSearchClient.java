package com.cloud.elasticSearch.client;

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
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月27日
 */
@Component
public class ElasticSearchClient
{

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate; 
    
    public <T> List<T> query(String indexName, String typeName, Class<T> responseType){
        Client client = elasticsearchTemplate.getClient();
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(indexName).setSearchType(typeName);
        SearchResponse searchResponse = searchRequestBuilder.setQuery(QueryBuilders.matchQuery("", ""))
                .execute().actionGet();
        SearchHits searchHits = searchResponse.getHits();
        for(SearchHit hit : searchHits){
            Map<String, Object> map = hit.getSource();
        }
        
        return null;
    }
    
}
