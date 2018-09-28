package com.cloud.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloud.bean.Book;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月25日
 */
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
        
        SearchRequestBuilder srb = client.prepareSearch("customer").setTypes("doc");
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
    
    /**
     * 全文查询，将从所有字段中查询包含传入的word分词后字符串的数据集，默认排序
     * @param word
     * @param pageable
     * @return
     */
    @RequestMapping("/test01")
    public List<Book> test01(String word, @PageableDefault Pageable pageable){
//        Pageable pageable = PageRequest.of(page, size);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery(word))
                .withPageable(pageable)
                .build();
        List<Book> list = elasticsearchTemplate.queryForList(searchQuery, Book.class);
        return list;
    }
    
    /**
     * 全文搜索，按name进行降序排序
     * @param word
     * @param pageable
     * @return
     */
    @RequestMapping("/test02")
    public List<Book> test02(String word, 
            @PageableDefault(sort="name", direction=Direction.DESC) Pageable pageable){

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery(word))
                .withPageable(pageable)
                .build();
        
        return elasticsearchTemplate.queryForList(searchQuery, Book.class);
    }
    
    /**
     * 按指定字段进行模糊查询，使用matchQuery
     * @return
     */
    @RequestMapping("/test03")
    public List<Book> test03(String id, String name, @PageableDefault Pageable pageable){
        
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
//                                    .withQuery(QueryBuilders.matchQuery("id", id))
                                    .withQuery(QueryBuilders.matchQuery("name", name))
                                    .withPageable(pageable)
                                    .build();
        return elasticsearchTemplate.queryForList(searchQuery, Book.class);
    }
    
    /**
     * 单字段对某短语进行匹配查询，短语分词的顺序会影响结果； PhraseMatch查询
     * @return
     */
    @RequestMapping("/test04")
    public List<Book> test04(String message, @PageableDefault Pageable pageable){
        
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchPhraseQuery("message", message))
                .withPageable(pageable)
                .build();
        
        return elasticsearchTemplate.queryForList(searchQuery, Book.class);
    }
    
    @RequestMapping("/test05")
    public List<Book> test05(String message, @PageableDefault Pageable pageable){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                                    .withQuery(QueryBuilders.matchPhraseQuery("message", message).slop(2))
                                    .withPageable(pageable)
                                    .build();
        
        return elasticsearchTemplate.queryForList(searchQuery, Book.class);
    }
    
    /**
     * Term查询，这个是严格查询，属于低级查询，不进行分词
     * 传入什么值就会拿传入的值做完全匹配
     * 
     * @param message
     * @param pageable
     * @return
     */
    @RequestMapping("/test06")
    public List<Book> test06(String message, @PageableDefault Pageable pageable){
        
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                                    .withQuery(QueryBuilders.termQuery("message", message))
                                    .withPageable(pageable)
                                    .build();
        
        return elasticsearchTemplate.queryForList(searchQuery, Book.class);
    }
    
    /**
     * 多字段匹配，只要任意一个字段匹配成功即可
     * 比如：传入“我是”，在name和message字段中所有包含了“我”，“是”的都会被查询出来
     * @param content
     * @param pageable
     * @return
     */
    @RequestMapping("/test07")
    public List<Book> test07(String content, @PageableDefault Pageable pageable){
        
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                                .withQuery(QueryBuilders.multiMatchQuery(content, "name", "message"))
                                .withPageable(pageable)
                                .build();
        
        return elasticsearchTemplate.queryForList(searchQuery, Book.class);
    }
    
    @RequestMapping("/test08")
    public List<Book> test08(String content, @PageableDefault Pageable pageable){
        
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                                .withQuery(QueryBuilders.multiMatchQuery(content, "name", "message")
                                        .operator(Operator.AND).minimumShouldMatch("75%"))
                                .withPageable(pageable)
                                .build();
        
        return elasticsearchTemplate.queryForList(searchQuery, Book.class);
    }
    
    /**
     * 查询title字段包含"xxx"， 且userId=xxx, weight最好小于xxx的结果
     * @param title
     * @param userId
     * @param weight
     * @return
     */
    @RequestMapping("/test09")
    public List<Book> test09(String title, Integer userId, Integer weight){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                                    .withQuery(QueryBuilders.boolQuery()
                                            .must(QueryBuilders.termQuery("userId", userId))
                                            .should(QueryBuilders.rangeQuery("weight").lt(weight))
                                            .must(QueryBuilders.matchQuery("title", title)))
                                    .build();
        
        return elasticsearchTemplate.queryForList(searchQuery, Book.class);
    }
}
