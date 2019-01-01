package com.cloud.ribbon.handle;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;
/**
 * 
 * @author : 张薪
 * @date : 2019年1月1日 下午10:21:31
 * @Description :
 */
public class DefaultResponseErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return response.getStatusCode().value() != HttpServletResponse.SC_OK;
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		String resp = StreamUtils.copyToString(response.getBody(), Charset.forName("UTF-8"));
		try {
			throw new Exception(resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
