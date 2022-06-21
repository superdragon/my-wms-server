package com.jiaming.wms.account.controller;

import com.jiaming.wms.account.service.IFileService;
import com.jiaming.wms.common.response.ResultCodeEnum;
import com.jiaming.wms.common.response.ResultVO;
import com.jiaming.wms.config.AppConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dragon
 */
@Api(tags = "文件上传API")
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    AppConfig appConfig;

    @Autowired
    IFileService fileService;

    @ApiOperation("上传单张图片")
    @PostMapping("/uploadImage")
    public ResultVO<String> uploadImage(@RequestPart("file") MultipartFile file) {
        String url = fileService.uploadImage(file);
        return new ResultVO<>(ResultCodeEnum.SUCCESS, url);
    }

    @ApiOperation("上传多张图片")
    @PostMapping("/uploadImages")
    public ResultVO<List<String>> uploadImage(@RequestPart("files") MultipartFile[] files) {
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            String url = fileService.uploadImage(file);
            urls.add(url);
        }
        return new ResultVO<>(ResultCodeEnum.SUCCESS, urls);
    }

    @GetMapping("/downFile")
    public ResponseEntity<byte[]> downFile(@RequestParam String filename) {
        // 获取下载文件的存放位置
        String downloadFilePath = appConfig.getSavePath();
        try {
            // 从文件保存目录中获取对应文件对象 D:\images\0c6f5d8fddff44cba5cd26276adc9738.jpg
            File file = new File(downloadFilePath, filename);
            // 设置响应头部信息
            HttpHeaders headers = new HttpHeaders();
            // 设置编码  为了解决中文名称乱码问题
            String downloadFileName = new String(filename.getBytes("UTF-8"), "iso-8859-1");
            // 将文件表述信息添加到 http 头信息中
            headers.setContentDispositionFormData("attachment", downloadFileName);
            // 在 http 头添加以下信息，浏览器会自动弹出下载提示框
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            // 创建响应实体对象
            // FileUtils.readFileToByteArray(file) 读取文件写到字节数组中
            ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
            return responseEntity;
        } catch (FileNotFoundException e) {
            System.out.println(e);
            System.out.println("找不到文件");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("文件下载出错");
        }
        return null;
    }
}
