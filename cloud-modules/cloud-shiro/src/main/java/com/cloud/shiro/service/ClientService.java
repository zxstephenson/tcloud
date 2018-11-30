package com.cloud.shiro.service;

import java.util.List;

import com.cloud.shiro.domain.Client;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月28日
 */

public interface ClientService
{
    public Client createClient(Client client);// 创建客户端
    
    public Client updateClient(Client client);// 更新客户端
    
    public void deleteClient(Long clientId);// 删除客户端
    
    Client findOne(Long clientId);// 根据 id 查找客户端
    
    List<Client> findAll();// 查找所有
    
    Client findByClientId(String clientId);// 根据客户端 id 查找客户端
    
    Client findByClientSecret(String clientSecret);//根据客户端安全 KEY 查找客户端

}
