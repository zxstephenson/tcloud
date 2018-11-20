package com.cloud.metrics;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author zyg
 *
 */
@Configuration
public class InfluxDBConfiguration {
    
    private String username = "admin";//用户名
    private String password = "admin";//密码
    private String openurl = "http://IP:8086";//InfluxDB连接地址
    private String database = "test_db";//数据库
    
    @Bean
    public InfluxDBConnect getInfluxDBConnect(){
        InfluxDBConnect influxDB = new InfluxDBConnect(username, password, openurl, database);
        
        influxDB.influxDbBuild();
        
        influxDB.createRetentionPolicy();
        return influxDB;
    }
}
