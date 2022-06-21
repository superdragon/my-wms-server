package com.jiaming.wms.account.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author dragon
 */
public interface IFileService {
    String uploadImage(MultipartFile file);
}
