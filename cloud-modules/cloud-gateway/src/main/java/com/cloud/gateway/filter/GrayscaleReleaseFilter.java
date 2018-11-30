package com.cloud.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.cloud.common.utils.StringUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月30日
 */

public class GrayscaleReleaseFilter extends ZuulFilter
{

    @Override
    public Object run() throws ZuulException
    {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String lanch = request.getParameter("lanch");
        if(StringUtil.isNotEmpty(lanch))
        {
        }
        return null;
    }

    @Override
    public boolean shouldFilter()
    {
        return true;
    }

    @Override
    public int filterOrder()
    {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public String filterType()
    {
        return FilterConstants.PRE_TYPE;
    }

}
