package com.cloud.ribbon.loadbalancer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.loadbalancer.NoOpLoadBalancer;
import com.netflix.loadbalancer.Server;

/**
 * created by foxdd 2017-12-05
 */
public class LcnNoOpLoadBalancerProxy extends NoOpLoadBalancer {
	
	private Logger logger = LoggerFactory.getLogger(LcnNoOpLoadBalancerProxy.class);
	
	LcnLoadBalancerRule lcnLoadBalancerRule = new LcnLoadBalancerRule();
	
	public LcnNoOpLoadBalancerProxy(){
		super();
	}

	@Override
	public Server chooseServer(Object key){
		logger.debug("enter chooseServer method, key:" + key);

		List<Server> serverList = new ArrayList<Server>();
		return lcnLoadBalancerRule.proxy(serverList, super.chooseServer(key));

	}

}
