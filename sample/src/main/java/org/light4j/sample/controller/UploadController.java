package org.light4j.sample.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.light4j.sample.annotation.LogAction;
import org.light4j.sample.bean.Response;
import org.light4j.sample.exception.RestCode;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@Api(tags = {"UploadController"}, value = "文件上传")
@RequestMapping(value = "upload")
public class UploadController {

    @LogAction
    @PostMapping(value = "file")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "上传的文件", dataTypeClass = MultipartFile.class),
            @ApiImplicitParam(name = "param", value = "附加参数", required = false, defaultValue = "default", dataTypeClass = String.class),
    })
    public Response<String> uploadFile(HttpServletRequest request,
                                       @RequestParam(value = "file") MultipartFile file,
                                       @RequestParam(value = "param", required = false, defaultValue = "default") String param
    ) {
        String targetPath = "";
        try {
            // 上传目录地址
            String uploadDir = request.getSession().getServletContext().getRealPath("/") + "upload/";
            targetPath = saveFile(uploadDir, file);
        } catch (IOException e) {
            e.printStackTrace();
            return new Response<>(RestCode.UPLOAD_FILE_FAILD, targetPath);
        }
        return new Response<>(RestCode.SUCCESS, targetPath);
    }

    @LogAction
    @PostMapping(value = "files")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file[]", value = "上传的多个文件", dataTypeClass = MultipartFile[].class),
            @ApiImplicitParam(name = "param", value = "附加参数", required = false, defaultValue = "default", dataTypeClass = String.class),
    })
    public Response<List<String>> uploadFiles(HttpServletRequest request,
                                              @RequestParam("files") MultipartFile[] files,
                                              @RequestParam("param") String param) {
        List<String> targetPaths = new ArrayList<>();
        try {
            // 上传目录地址
            String uploadDir = request.getSession().getServletContext().getRealPath("/") + "upload/";
            for (MultipartFile file : files) {
                if (file != null) {
                    targetPaths.add(saveFile(uploadDir, file));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Response<>(RestCode.UPLOAD_FILE_FAILD, targetPaths);
        }
        return new Response<>(RestCode.SUCCESS, targetPaths);
    }


    public String saveFile(String uploadDir, MultipartFile file) throws IOException {

        // 如果目录不存在,自动创建
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        log.info("uploadDir:{}", uploadDir);
        // 上传文件名
        String fileName = file.getOriginalFilename();
        // 上传文件后缀名
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        // 生成文件名
        fileName = UUID.randomUUID() + suffix;
        // 保存文件对象
        File targetFile = new File(uploadDir + fileName);
        // 将上传文件写入服务器
        file.transferTo(targetFile);
        return targetFile.getAbsolutePath();
    }
}
