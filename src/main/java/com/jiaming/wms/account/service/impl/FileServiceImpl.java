package com.jiaming.wms.account.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.jiaming.wms.account.service.IFileService;
import com.jiaming.wms.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author dragon
 */
@Service
public class FileServiceImpl implements IFileService {

    @Autowired
    AppConfig appConfig;

    @Override
    public String uploadImage(MultipartFile file) {
        // 获取文件名称 xxx.jpg xxx.mp3
        String fileName = file.getOriginalFilename();
        if (StrUtil.isEmpty(fileName)) {
            return "";
        }
        // 截取文件的后缀名
        String[] names = fileName.split("\\.");
        // jpg  mp3
        String fileType = names[names.length - 1];
        // 判断文件类型是否允许上传
        if (!appConfig.getImageTypes().contains(fileType)) {
            return "";
        }
        // 判断文件大小是否超过允许范围
        if (appConfig.getImageMaxSize() < file.getSize()) {
            return "";
        }
        // 自定义唯一的空文件名  UUID 是32位的唯一字符串  xxxxxx.jpg
        String newFileName = IdUtil.fastSimpleUUID() + "." + fileType;
        // 指定空文件名的存储路径 D:\images\xxxxx.jpg
        String filePath = appConfig.getSavePath() + newFileName;
        try {
            // 创建空文件
            File newFile = FileUtil.touch(filePath);
            // 把上传文件的内容写入到空文件
            FileUtil.writeBytes(file.getBytes(), newFile);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        // 获取HTTP访问的基础路径 http://localhost:8081/xxxx.jpg
        String httpPath = appConfig.getHttpPath() + newFileName;
        // 返回文件的HTTP访问路径
        return httpPath;
    }
}
