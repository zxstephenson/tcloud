package com.cloud;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

import org.springframework.util.ClassUtils;
import org.springframework.util.StreamUtils;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月21日
 */

public class TestClient
{
    public static void main(String[] args) throws Exception
    {
        ClassUtils.getDefaultClassLoader();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println("uuid = " + uuid);
        
        File file = new File(uuid+".properties");
        if(!file.exists()){
            if(file.createNewFile()){
                OutputStream os = new FileOutputStream(file);
                StreamUtils.copy("hello=123\r\nname=stephen", Charset.forName("UTF-8"), os);
                os.close();
            }
        }
    }
}
