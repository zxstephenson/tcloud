package com.cloud.tx;

import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.cloud.ribbon.loadbalancer.LcnRibbonConfiguration;

/**
 * Created by lorne on 2017/6/26.
 */

@Configuration
@ComponentScan
@RibbonClients(defaultConfiguration=LcnRibbonConfiguration.class)
public class TransactionConfiguration {



}
