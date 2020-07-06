package com.swb.security.demo.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.swb.security.demo.domain.FileInfo;
import com.swb.security.demo.domain.StudyPlan;
import com.swb.security.demo.domain.StudyPlan2;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.*;

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
    public FileInfo fileUpload(@RequestParam(value = "file") MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + ".txt";
        File loadFile = new File(filePath, fileName);
        file.transferTo(loadFile);
        return new FileInfo("/file/" + fileName);
    }

    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletResponse response) throws Exception {

        InputStream inputStream = new FileInputStream(new File(filePath, id + ".txt"));
        OutputStream outputStream = response.getOutputStream();

        response.setContentType("application/x-download");
        response.addHeader("Content-Disposition", "attachment;filename=test.txt");

        IOUtils.copy(inputStream, outputStream);
        outputStream.flush();
    }

    @PostMapping("/excel")
    public String importExcel(@RequestParam(value = "file") MultipartFile file) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
        List<List<Object>> read = reader.read();
        for (List list : read) {
            for (Object o : list) {
                System.out.print(o + "\t");
            }
            System.out.println();
        }
        return "{\"code\":\"1\",\"message\":\"ok\"}";
    }

    @PostMapping("/excel2")
    public String importExcel2(@RequestParam(value = "file") MultipartFile file) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream(), 0);
        Map<String, String> map = new HashMap<>(10);
        map.put("序号", "id");
        map.put("技术", "technology");
        map.put("资料", "data");
        map.put("开始时间", "startDate");
        map.put("结束时间", "endDate");
        map.put("结果", "result");
        map.put("备注", "remark");
        reader.setHeaderAlias(map);
        List<StudyPlan> read = reader.read(2, 3, StudyPlan.class);
        for (StudyPlan sp : read) {
            System.out.println(sp);
        }
        return "{\"code\":\"1\",\"message\":\"ok\"}";
    }

    @PostMapping("/excel3")
    public String importExcel3(@RequestParam(value = "file") MultipartFile file) throws IOException {
        ExcelReader reader = ExcelUtil.getReader(file.getInputStream(), 0);
        Map<String, String> map = new HashMap<>(10);
        map.put("序号", "id");
        map.put("技术", "technology");
        map.put("资料", "data");
        map.put("开始时间", "startDate");
        map.put("结束时间", "endDate");
        map.put("结果", "result");
        map.put("备注", "remark");
        reader.setHeaderAlias(map);
        List<StudyPlan2> read = reader.read(2, 3, StudyPlan2.class);
        for (StudyPlan2 sp : read) {
            System.out.println(sp);
        }
        return "{\"code\":\"1\",\"message\":\"ok\"}";
    }

    @GetMapping("/excel/1")
    public void exportExcel(@PathVariable String id, HttpServletResponse response) throws IOException {
        List<StudyPlan> studyPlans = new ArrayList<>();
        studyPlans.add(new StudyPlan(1, "redis", "D:\\BaiduYunDownload\\redis从入门到高可用", new Date(), new Date(), "了解了基础", ""));
        studyPlans.add(new StudyPlan(2, "多线程", "多线程", new Date(), new Date(), "了解了基础", ""));
        studyPlans.add(new StudyPlan(3, "高并发", "https://www.imooc.com/u/2145618/courses?sort=publish", new Date(), new Date(), "了解了基础", ""));
        studyPlans.add(new StudyPlan(4, "git", "git", new Date(), new Date(), "了解了基础", ""));
        studyPlans.add(new StudyPlan(5, "webSocket", "", new Date(), new Date(), "了解了基础", ""));
        studyPlans.add(new StudyPlan(6, "Spring Boot", "JavaSSM快速开发在线教育平台", new Date(), new Date(), "了解了基础", ""));

        ExcelWriter writer = ExcelUtil.getWriter(true);
//        writer.addHeaderAlias("id","序号");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("id", "序号");
        map.put("technology", "技术");
        map.put("data", "资料");
        map.put("startDate", "开始时间");
        map.put("endDate", "结束时间");
        map.put("result", "结果");
        map.put("remark", "备注");
        writer.setHeaderAlias(map);
        writer.merge(0, 1, 0, 6, "学习计划", true);
        writer.passRows(2);

        writer.write(studyPlans, true);
        writer.autoSizeColumnAll();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("学习计划1", "UTF-8") + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
    }

    @GetMapping("/excel/2")
    public void exportExcel2(HttpServletResponse response) throws IOException {
        List<StudyPlan2> studyPlans = new ArrayList<>();
        studyPlans.add(new StudyPlan2(1, "redis", "D:\\BaiduYunDownload\\redis从入门到高可用", LocalDateTime.now(), LocalDateTime.now(), "了解了基础", ""));
        studyPlans.add(new StudyPlan2(2, "多线程", "多线程", LocalDateTime.now(), LocalDateTime.now(), "了解了基础", ""));
        studyPlans.add(new StudyPlan2(3, "高并发", "https://www.imooc.com/u/2145618/courses?sort=publish", LocalDateTime.now(), LocalDateTime.now(), "了解了基础", ""));
        studyPlans.add(new StudyPlan2(4, "git", "git", LocalDateTime.now(), LocalDateTime.now(), "了解了基础", ""));
        studyPlans.add(new StudyPlan2(5, "webSocket", "", LocalDateTime.now(), LocalDateTime.now(), "了解了基础", ""));
        studyPlans.add(new StudyPlan2(6, "Spring Boot", "JavaSSM快速开发在线教育平台", LocalDateTime.now(), LocalDateTime.now(), "了解了基础", ""));

        ExcelWriter writer = ExcelUtil.getWriter(true);
//        writer.addHeaderAlias("id","序号");
        Map<String, String> map = new LinkedHashMap<>();
        map.put("id", "序号");
        map.put("technology", "技术");
        map.put("data", "资料");
        map.put("startDate", "开始时间");
        map.put("endDate", "结束时间");
        map.put("result", "结果");
        map.put("remark", "备注");
        writer.setHeaderAlias(map);
        writer.merge(0, 1, 0, 6, "学习计划", true);
        writer.passRows(2);

        writer.write(studyPlans, true);
        writer.autoSizeColumnAll();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("学习计划1", "UTF-8") + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
    }



}
