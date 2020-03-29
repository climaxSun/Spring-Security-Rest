package com.swb.security.demo.controller;

import com.swb.security.demo.domain.FileInfo;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author swb
 * 时间  2020-03-26 15:48
 * 文件  FileController
 */
@RestController
@RequestMapping("/files")
public class FileController {

    @Value("${filePath}")
    private String filePath;

    @PostMapping
    public FileInfo fileUpload(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        String fileName = System.currentTimeMillis() + ".txt";
        File loadFile = new File(filePath, fileName);
        file.transferTo(loadFile);
        return new FileInfo("/file/" + fileName);
    }

    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletResponse response) throws Exception {

        try (InputStream inputStream = new FileInputStream(new File(filePath, id + ".txt"));
             OutputStream outputStream = response.getOutputStream()) {

            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");

            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }
    }


}
