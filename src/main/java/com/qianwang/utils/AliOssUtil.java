package com.qianwang.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public class AliOssUtil {

    public static final String ALI_DOMAIN = "https://qjw-00.oss-cn-guangzhou.aliyuncs.com/";

    public static String uploadImage(MultipartFile file) throws IOException {
        //生成文件名
        String originalFilename = file.getOriginalFilename(); //原图片名
        String ext = "." + FilenameUtils.getExtension(originalFilename);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String fileName = uuid + ext;
        //地域节点
        String endpoint = "http://oss-cn-guangzhou.aliyuncs.com";
        String accessKeyId = "LTAI5tJvsit6vpz4NSkMcgJw";
        String accessKeySecret = "n92VlZwlMp02hJV5LeXaiVtkBS2o3L";
        //OSS客户端对象
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject("qjw-00", fileName, file.getInputStream());
        ossClient.shutdown();
        return ALI_DOMAIN + fileName;

    }


}
