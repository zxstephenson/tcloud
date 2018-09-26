package com.cloud.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.cloud.bean.Book;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月25日
 */

public interface BookDao extends ElasticsearchRepository<Book, String>
{

}
