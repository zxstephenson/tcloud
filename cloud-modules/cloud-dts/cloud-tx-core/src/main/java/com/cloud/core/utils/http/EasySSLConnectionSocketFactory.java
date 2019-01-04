/**
 * <p>文件名称: EasySSLConnectionSocketFactory.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.core.utils.http;

import org.apache.http.HttpHost;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class EasySSLConnectionSocketFactory implements ConnectionSocketFactory
{

    private SSLContext sslcontext = null;

    @Override
    public Socket createSocket(HttpContext context) throws IOException
    {
        return getSSLContext().getSocketFactory().createSocket();
    }

    private static SSLContext createEasySSLContext() throws IOException
    {
        try
        {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null,
                    new TrustManager[] { new EasyX509TrustManager(null) },
                    null);
            return context;
        } catch (Exception e)
        {
            throw new IOException(e.getMessage());
        }
    }

    private SSLContext getSSLContext() throws IOException
    {
        if (this.sslcontext == null)
        {
            this.sslcontext = createEasySSLContext();
        }
        return this.sslcontext;
    }

    @Override
    public Socket connectSocket(int connectTimeout, Socket sock, HttpHost host,
            InetSocketAddress remoteAddress, InetSocketAddress localAddress,
            HttpContext context) throws IOException
    {
        SSLSocket sslsock = (SSLSocket) ((sock != null) ? sock
                : createSocket(context));
        if (localAddress != null)
        {
            InetSocketAddress isa = new InetSocketAddress(
                    localAddress.getAddress(), localAddress.getPort());
            sslsock.bind(isa);
        }
        sslsock.connect(remoteAddress, connectTimeout);
        return sslsock;
    }
}
