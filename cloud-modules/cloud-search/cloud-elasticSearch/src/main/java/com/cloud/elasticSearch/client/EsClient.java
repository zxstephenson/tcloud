package com.cloud.elasticSearch.client;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cloud.common.enums.Sort;
import com.cloud.common.search.bean.Page;
import com.cloud.common.search.bean.SearchRequestData;
import com.cloud.common.search.dao.EsDao;
import com.cloud.common.search.exception.SearchException;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月27日
 */
@Component
public class EsClient implements EsDao
{

    @Autowired
    private ElasticsearchTemplate esTemplate; 
    
    /**
     * 用于全文搜索
     * @return
     * @throws SearchException 
     */
    public <T> List<T> queryStringQuery(SearchRequestData searchRequestData, 
             Class<T> responseType) throws SearchException{
        
        if(searchRequestData == null){
            throw new SearchException("-", "SearchRequestData不能为空！");
        }
        
        QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(searchRequestData.getQueryString());
        return query(searchRequestData, queryBuilder, responseType);
    }
    /**
     * 返回指定索引下的所有数据
     * @return
     */
    public <T> List<T> matchAllQuery(SearchRequestData searchRequestData, 
            Class<T> responseType) throws SearchException{

        return query(searchRequestData, QueryBuilders.matchAllQuery(), responseType);
    }
    
    /**
     * 分词搜索
     * @return
     */
    public <T> List<T> matchQuery(SearchRequestData searchRequestData,
            Class<T> responseType) throws SearchException{

        if(searchRequestData == null){
            throw new SearchException("-", "SearchRequestData不能为空！");
        }
        
        String fieldName = searchRequestData.getFieldName();
        String queryString = searchRequestData.getQueryString();
        
        QueryBuilder queryBuilder = QueryBuilders.matchQuery(fieldName, queryString);
        return query(searchRequestData, queryBuilder, responseType);
    }
    
    /**
     * 短语搜索
     * @return
     */
    public <T> List<T> matchPhraseQuery(SearchRequestData searchRequestData, 
             Class<T> responseType) throws SearchException{
        
        return matchPhraseQuery(searchRequestData, responseType, false);
    }
    
    /**
     * 
     * @param searchRequestData
     * @param responseType
     * @param ignoreWordTurn    是否可以忽略单词之间的顺序，忽略单词之间的紧密度
     * @return
     * @throws SearchException
     */
    public <T> List<T> matchPhraseQuery(SearchRequestData searchRequestData, 
            Class<T> responseType, boolean ignoreWordTurn) throws SearchException{
       
       if(searchRequestData == null){
           throw new SearchException("-", "SearchRequestData不能为空！");
       }
       
       String fieldName = searchRequestData.getFieldName();
       String queryString = searchRequestData.getQueryString();
       
       QueryBuilder queryBuilder = null;
       if(ignoreWordTurn){
           queryBuilder = QueryBuilders.matchPhraseQuery(fieldName, queryString).slop(Integer.MAX_VALUE);
       }else {
           queryBuilder = QueryBuilders.matchPhraseQuery(fieldName, queryString);
       }
       return query(searchRequestData, queryBuilder, responseType);
   }
    
    /**
     * 精确搜索
     * @return
     */
    public <T> List<T> termQuery(SearchRequestData searchRequestData, 
             Class<T> responseType) throws SearchException{
        
        if(searchRequestData == null){
            throw new SearchException("-", "SearchRequestData不能为空！");
        }
        
        String fieldName = searchRequestData.getFieldName();
        String queryString = searchRequestData.getQueryString();
        
        QueryBuilder queryBuilder = QueryBuilders.termQuery(fieldName, queryString);
        return query(searchRequestData, queryBuilder, responseType);
    }
    
    /**
     * 指定多字段搜索
     * @return
     */
    public <T> List<T> multiMatchQuery(SearchRequestData searchRequestData, 
             Class<T> responseType) throws SearchException{
        
        if(searchRequestData == null){
            throw new SearchException("-", "SearchRequestData不能为空！");
        }
        
        String queryString = searchRequestData.getQueryString();
        
        List<String> list = searchRequestData.getFields();
        if(list != null){
            String[] fields = new String[list.size()];
            fields = list.toArray(fields);
            QueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(queryString, fields);
            return query(searchRequestData, queryBuilder, responseType);
        }
        
        return null;
    }
    
    /**
     * 合并搜索
     * @return
     */
    public <T> List<T> boolQuery(SearchRequestData searchRequestData, 
            String queryString, Class<T> responseType) throws SearchException{
        
        if(searchRequestData == null){
            throw new SearchException("-", "SearchRequestData不能为空！");
        }
        
        QueryBuilder queryBuilder = QueryBuilders.boolQuery();
        return query(searchRequestData, queryBuilder, responseType);
    }
    
    public <T> List<T> query(final SearchRequestData searchRequestData, 
            QueryBuilder queryBuilder, Class<T> responseType) throws SearchException{
     
        return query(searchRequestData, queryBuilder, responseType, null);
    }
    
    public <T> List<T> query(final SearchRequestData searchRequestData, 
            QueryBuilder queryBuilder, Class<T> responseType, QueryBuilder filterBuilder) throws SearchException{

        if(searchRequestData == null){
            throw new SearchException("-", "SearchRequestData不能为空！");
        }
        
        /**
         * 检查搜索对象是否有@Document注解，如果有配置@Document注解，使用注解中配置的index和type进行搜索，
         * 否则使用searchRequestData中传入的index和type进行搜索 
         */
        Document document = responseType.getAnnotation(Document.class);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        
        if(document == null){
            if(StringUtils.isEmpty(searchRequestData.getIndexName())){
                throw new SearchException("-", "未指定索引名！");
            }
            nativeSearchQueryBuilder.withIndices(searchRequestData.getIndexName())
                                    .withTypes(searchRequestData.getTypeName());
        }
        
        if(!StringUtils.isEmpty(searchRequestData.getSortFieldName())){
            withSort(searchRequestData, nativeSearchQueryBuilder);
        }
        
       /* FilterCondition filterCondition = searchRequestData.getFilterCondition();
        if(filterCondition != null){
            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("").lt(12);
            QueryBuilders.matchQuery(name, text)
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery().must(rangeQueryBuilder);
            nativeSearchQueryBuilder.withFilter(boolQueryBuilder);
        }*/
        
        
        
        Page page = searchRequestData.getPage();
        Pageable pageable = PageRequest.of(page.getPageNo(), page.getPageSize());
        
        SearchQuery searchQuery = nativeSearchQueryBuilder
                                  .withQuery(queryBuilder)
                                  .withPageable(pageable).build();
        return esTemplate.queryForList(searchQuery, responseType);
        
    }
    
    /**
     * 设置按指定字段排序
     * @param searchRequestData
     * @param nativeSearchQueryBuilder
     */
    private void withSort(final SearchRequestData searchRequestData,
            NativeSearchQueryBuilder nativeSearchQueryBuilder)
    {
        String sortFieldName = searchRequestData.getSortFieldName();
        SortOrder sortOrder = SortOrder.ASC;
        if(searchRequestData.getOrder() == Sort.DESC){
            sortOrder = SortOrder.DESC;
        }
        FieldSortBuilder sortBuilder = SortBuilders.fieldSort(sortFieldName).order(sortOrder);
        nativeSearchQueryBuilder.withSort(sortBuilder);
    }
    
}
