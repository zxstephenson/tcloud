/**
 * <p>文件名称: TxManagerApplication.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
@SpringBootApplication
@EnableDiscoveryClient
public class TxManagerApplication
{

    public static void main(String[] args)
    {
        args = new String[] { "--spring.profiles.active=dev" };
        SpringApplication.run(TxManagerApplication.class, args);
    }

}
