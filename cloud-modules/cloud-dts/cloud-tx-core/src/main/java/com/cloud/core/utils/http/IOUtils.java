/**
 * <p>文件名称: IOUtils.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.core.utils.http;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class IOUtils
{

    public synchronized static String getStringFromInputStream(
            CloseableHttpResponse response)
    {
        if (response == null)
        {
            return null;
        }
        HttpEntity entity = response.getEntity();
        Header[] headers = response.getAllHeaders();
        String res = null;
        boolean isGzip = false;
        if (headers != null)
            for (Header header : headers)
            {
                String name = header.getName();
                String value = header.getValue();
                if (name.equals("Content-Encoding"))
                {
                    if (value != null && value.toLowerCase().contains("gzip"))
                    {
                        isGzip = true;
                    }
                }
            }
        String encode = "utf-8";
        if (entity.getContentType() != null)
        {
            HeaderElement[] hes = entity.getContentType().getElements();
            if (hes != null && hes.length > 0)
            {
                for (HeaderElement he : hes)
                {
                    encode = he.getParameterByName("charset") == null ? "utf-8"
                            : he.getParameterByName("charset").getValue();
                }
            }
        }
        try
        {
            InputStream is = entity.getContent();
            if (is == null)
            {
                return null;
            }
            if (isGzip)
            {
                GZIPInputStream gis = new GZIPInputStream(is);
                ByteArrayBuffer bt = new ByteArrayBuffer(4096);
                int l;
                byte[] tmp = new byte[4096];
                while ((l = gis.read(tmp)) != -1)
                {
                    bt.append(tmp, 0, l);
                }
                is.close();
                gis.close();
                res = new String(bt.toByteArray(), encode);
            } else
            {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(is, encode));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                if (is != null)
                {
                    is.close();
                }
                if (br != null)
                {
                    br.close();
                }
                res = sb.toString();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                response.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return res;

    }
}
