package com.example.estatemanagement.controller;

import com.example.estatemanagement.common.ListPage;
import com.example.estatemanagement.service.CommonService;
import com.example.estatemanagement.service.impl.AdminServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CommonController {
    private static final Logger LOG = LoggerFactory.getLogger(CommonController.class);
    @Autowired
    private CommonService commonService;

    @Autowired
    AdminServiceImpl service;
    @RequestMapping("/all/deviedInfo")
    @ResponseBody
    public Map<String,Object> common(String page,HttpSession session, HttpServletRequest request) {
        Map<String,Object> result=new HashMap<>();
        if(StringUtils.isEmpty(page)||"undefined".equals(page)){
            page="1";
        }


        //获取所有数据
        List<Map<String,Object>> allData = (List<Map<String,Object>>) commonService.allData();
        //获取数据页数
        Integer pageMax=new ListPage<Map<String,Object>>().getPageSize(allData);
        //返回当前页的数据
        if(allData.size()>10) {
            if (Integer.parseInt(page) == pageMax) {
                allData = new ListPage<Map<String,Object>>().getList(allData, Integer.parseInt(page),allData.size(), 10);
            } else {
                allData = new ListPage<Map<String,Object>>().getList(allData, Integer.parseInt(page), Integer.parseInt(page) * 10, 10);
            }
        }
        result.put("pageMax",pageMax);
        result.put("page",Integer.parseInt(page));
        result.put("datas",allData);
        return result;
    }

    @RequestMapping("/all/files")
    @ResponseBody
    public Map<String,Object> files(String page,String startTime,String endTime,HttpSession session, HttpServletRequest request) {
        Map<String,Object> result=new HashMap<>();
        //获取所有数据
        List<Map<String,Object>> allData = new ArrayList<>();
        //String filePath="D:"+ File.separator+"dataFile";
        String filePath="/instrument/meteorological";
        File homeFile=new File(filePath);
        System.out.println(homeFile);
        if (homeFile.isDirectory()) {
            String[] children = homeFile.list();
            for (int i = 0; i < children.length; i++) {
                Map<String,Object> data=new HashMap<>();
                //txt文件
                File txtFile=new File(filePath+File.separator+children[i]);
                String time=txtFile.getName().replace(".txt","");
                startTime=startTime.replace("-","");
                endTime=endTime.replace("-","");


                if(!StringUtils.isEmpty(startTime)&&!StringUtils.isEmpty(endTime)){
                    BigDecimal startTimeBigDecimal = new BigDecimal(startTime);
                    BigDecimal endTimeBigDecimal = new BigDecimal(endTime);
                    BigDecimal timeBigDecimal = new BigDecimal(time);
                    if(timeBigDecimal.compareTo(startTimeBigDecimal)>=0&&timeBigDecimal.compareTo(endTimeBigDecimal)<=0){
                        data.put("fileName",txtFile.getName());
                        allData.add(data);
                    }
                }

                if(StringUtils.isEmpty(startTime)||StringUtils.isEmpty(endTime)){
                    data.put("fileName",txtFile.getName());
                    allData.add(data);
                }


            }
        }

        if(StringUtils.isEmpty(page)||"undefined".equals(page)){
            page="1";
        }

        //获取所有数据
        //获取数据页数
        Integer pageMax=new ListPage<Map<String,Object>>().getPageSize(allData);
        //返回当前页的数据
        if(allData.size()>10) {
            if (Integer.parseInt(page) == pageMax) {
                allData = new ListPage<Map<String,Object>>().getList(allData, Integer.parseInt(page),allData.size(), 10);
            } else {
                allData = new ListPage<Map<String,Object>>().getList(allData, Integer.parseInt(page), Integer.parseInt(page) * 10, 10);
            }
        }
        result.put("pageMax",pageMax);
        result.put("page",Integer.parseInt(page));
        result.put("datas",allData);
        return result;
    }

    @RequestMapping("/all/down")
    public void down(String fileName,HttpSession session, HttpServletRequest request,HttpServletResponse response) {
        //String downloadDir="D:"+ File.separator+"dataFile"+ File.separator+fileName;
        String downloadDir="/instrument/meteorological"+ File.separator+fileName;
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        try {
            // 这个文件名是前端传给你的要下载的图片的id
            // 然后根据id去数据库查询出对应的文件的相关信息，包括url，文件名等
            //1、设置response 响应头，处理中文名字乱码问题
            response.reset(); //设置页面不缓存,清空buffer
            response.setCharacterEncoding("UTF-8"); //字符编码
            response.setContentType("multipart/form-data"); //二进制传输数据
            //设置响应头，就是当用户想把请求所得的内容存为一个文件的时候提供一个默认的文件名。
            //Content-Disposition属性有两种类型：inline 和 attachment
            //inline ：将文件内容直接显示在页面
            //attachment：弹出对话框让用户下载具体例子：
            response.setHeader("Content-Disposition",
                    "attachment;fileName="+ URLEncoder.encode(fileName, "UTF-8"));

            // 通过url获取文件
            File file = new File(downloadDir);
            //2、 读取文件--输入流
            fileInputStream = new FileInputStream(file);
            //3、 写出文件--输出流
            outputStream = response.getOutputStream();

            byte[] buffer = new byte[1024];
            int len;
            //4、执行写出操作
            while ((len = fileInputStream.read(buffer)) != -1){
                outputStream.write(buffer,0,len);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if( fileInputStream != null ){
                try {
                    // 5、关闭输入流
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if( outputStream != null ){
                try {
                    // 5、关闭输出流
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @RequestMapping("/all/saveData")
    public void saveData(String page,HttpSession session, HttpServletRequest request) {
        //String filePath="D:"+ File.separator+"dataFile";
        String filePath="/instrument/meteorological";
        File homeFile=new File(filePath);
        //判断是否是文件夹
        if (homeFile.isDirectory()) {
            //列出文件目录
            String[] children = homeFile.list();
            for (int i = 0; i < children.length; i++) {
                //txt文件
                File txtFile=new File(filePath+File.separator+children[i]);
                //时间
                String time=txtFile.getName().replace(".txt","");
                String content=fileLine(txtFile,time);
                System.out.println(time+"内容:"+content);

            }
        }
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }

    public String fileLine(File file, String time) {

        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
            String s = null;
            int i=0;
            int fz=1;
            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
                if(i!=0){
                    System.out.println("行内容:"+s);
                    result.append(System.lineSeparator() + s);
                    String cot[]=s.split(" ");
                    //小时
                    Integer hour=fz/60%24;
                    //时间小时
                    String hourStr;
                    if(hour<10){
                        hourStr="0"+hour;
                    }else{
                        hourStr=hour.toString();
                    }

                   // String scondStr=fz+"";
                  //  if(fz<10){
                   //     scondStr="0"+scondStr;
                  //  }
                    //20230308000100
                    String scondStr = String.format("%02d", fz % 60);
                    String sfm=time+hourStr+scondStr+"00";
                    System.out.println(sfm);
                    //正则表达式
                    String reg = "(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})";
                    String newtime = sfm.replaceAll(reg, "$1-$2-$3 $4:$5:$6");
                    //去库里查看该时间段有没有 没有则添加
                    if(!commonService.findData(newtime)){
                        commonService.InsertData(newtime,cot[0],cot[1],cot[2]);
                    }
                    fz++;

                }
                i++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();

    }
}
