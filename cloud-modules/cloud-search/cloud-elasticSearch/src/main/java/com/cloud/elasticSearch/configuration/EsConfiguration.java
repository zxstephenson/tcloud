package com.cloud.elasticSearch.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloud.common.search.dao.EsDao;
import com.cloud.elasticSearch.client.EsClient;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月28日
 */
@Configuration
public class EsConfiguration
{
    @Bean
    @ConditionalOnMissingBean
    public EsDao getEsClient(){
        return new EsClient();
    }
    
}
