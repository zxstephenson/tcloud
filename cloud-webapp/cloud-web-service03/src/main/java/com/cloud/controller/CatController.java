package com.cloud.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

//import com.cloud.ribbon.client.RibbonClient;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月10日
 */

@RestController
public class CatController
{
    
//    @Autowired
//    private RibbonClient ribbonClient;
    
    @Bean(name="remoteRestTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Autowired
    @Qualifier(value = "remoteRestTemplate")
    private RestTemplate rest;
    
    @RequestMapping("/test01")
    public void test01() throws Exception{
        
//        String result = ribbonClient.remoteForGet("http://192.168.8.91:9002/test02", new HashMap<String, String>(), String.class);
        
        String result = rest.getForObject(new URI("http://127.0.0.1:9002/test02"), String.class);
        
        System.out.println("result = " + result);
        
        
        /*
        
        String pageName = "";
        String serverIp = "";
        double amount = 0;
        
        Transaction t = Cat.newTransaction("URL", pageName);
        try{
            Cat.logEvent("URL.Server", serverIp, Event.SUCCESS, 
                    "ip=" + serverIp +"&....");
            
            Cat.logMetricForCount("PayCount");
            
            Cat.logMetricForSum("PayAmount", amount);
            
            System.err.println("***********************");
            
            t.setStatus(Transaction.SUCCESS);
        }catch(Exception e){
            t.setStatus(e);
        }finally{
            t.complete();
        }
    */}
    
    
    
}
