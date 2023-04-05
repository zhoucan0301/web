package com.example.estatemanagement.common;

import com.example.estatemanagement.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;

//@Configuration      //1.主要用于标记配置类，兼备Component的效果。
//@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {
    @Autowired
    private CommonService commonService;

    //3.添加定时任务
    @Scheduled(cron = "0/10 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        String filePath="D:"+ File.separator+"dataFile";
        //String filePath="/instrument/meteorological";
        File homeFile=new File(filePath);
        if (homeFile.isDirectory()) {
            String[] children = homeFile.list();
            for (int i = 0; i < children.length; i++) {
                //txt文件
                File txtFile=new File(filePath+File.separator+children[i]);
                String time=txtFile.getName().replace(".txt","");
                String content=fileLine(txtFile,time);
                System.out.println(time+"内容:"+content);
//                String cot[]=content.split(" ");
//不换行代码
//                for(int k = 0; k < cot.length; k++){
//                    if(k+1%3==0){
//                        //没有超数组下标的话
//                        if(k+2<cot.length){
//                            String key1=cot[k];
//                            String key2=cot[k+1];
//                            String key3=cot[k+2];
//                            //下面是入库逻辑
//                        }
//                    }
//                }
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
                    Integer hour=fz/60;
                    //时间小时
                    String hourStr;
                    if(hour<10){
                        hourStr="0"+hour;
                    }else{
                        hourStr=hour.toString();
                    }

                    String scondStr=fz+"";
                    if(fz<10){
                        scondStr="0"+scondStr;
                    }
                    String sfm=time+hourStr+scondStr+"00";
                    System.out.println(sfm);

                    String reg = "(\\d{4})(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\d{2})";
                    String newtime = sfm.replaceAll(reg, "$1-$2-$3 $4:$5:$6");
                    //去库里查看该时间段有没有 没有则添加
                    if(!commonService.findData(newtime)){
                        if(cot.length>2){
                            commonService.InsertData(newtime,cot[0],cot[1],cot[2]);
                        }
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
