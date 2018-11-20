package com.cloud.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月15日
 */
@RestController
public class UploadController
{
    @RequestMapping(value="/uploadFile", method=RequestMethod.POST)
    public void uploadFile(@RequestParam("fileName") MultipartFile file)
    {
        if(file != null)
        {
            long fileSize = file.getSize();
            System.err.println(fileSize);
            System.err.println(file.getName());
            System.err.println(file.getOriginalFilename());
        }
    }
}
