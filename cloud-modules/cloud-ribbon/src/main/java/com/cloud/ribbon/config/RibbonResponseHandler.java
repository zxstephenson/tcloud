package com.cloud.ribbon.config;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月8日
 */

public class RibbonResponseHandler implements ResponseErrorHandler
{

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException
    {
        return false;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException
    {

    }

}
