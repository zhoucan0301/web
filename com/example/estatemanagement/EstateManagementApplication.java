package com.example.estatemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

import java.io.*;
import java.sql.*;
import  java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@SpringBootApplication
//注意：MapperScan注解要导入TK包下的
@MapperScan(basePackages = "com.example.estatemanagement.dao")
public class EstateManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EstateManagementApplication.class, args);

        String driver = "com.mysql.jdbc.Driver";

        String url = "jdbc:mysql://localhost:3306/heima-estate?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";

        String username = "root";

        String password = "root";

        Connection conn = null;

        Statement stmt = null;



        File file = new File("c:/down/text.txt");

        StringBuffer sql = null;
        StringBuffer delsql = null;//数据库初始化，先把之前读取的数据内容清除

        BufferedReader reader = null;

        String line = null;

        String[] str = null;


        String id = null ;
        String communityName = null ;
        String communityId = null;
        String name = null;
        Date  updateTime1 = null;
        Date  updateTime2 = null;
        String createTime = null;
        int i =1;//循环参数4


        try {

            Class.forName(driver);

            conn = DriverManager.getConnection(url, username, password);

            reader = new BufferedReader(new FileReader(file));

            stmt = conn.createStatement();
            delsql = new StringBuffer();
             delsql.append( "delete from tb_building where id>=1 ");
            stmt.executeUpdate(delsql.toString());



            while((line = reader.readLine())!= null){

                if(i<=1){
                    //sql = new StringBuffer();

                    str = line.split(" ");
                    createTime =(str[0]+"000000");
                    SimpleDateFormat format0 = new SimpleDateFormat("yyyyMMddHHmmss");
                    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                     //Date date0 =  (Date) format0.parse(createTime);
                     updateTime1 =  (Date) format0.parse(createTime);//这个时候是sql

                   // updateTime1 = (Date) format0.parse(createTime);
                    //String date0 =  format0.parse(createTime);
                   //updateTime1 = (Date) format0.parse(createTime);
                    i++;

                }

                if(i>1){


                sql = new StringBuffer();
                //str[] = new String();


                str = line.split(" ");

                /*str[0]=null;
                str[1]=null;
                str[2]=null;
                str[3]=null;
                str[4]=null;*/

                //id = str[0];
                     id =String.valueOf(i);

             if(str.length>3){
                 communityName=str[1];
                 communityId=str[2];
                 name=str[3];
                 //输出的是updateTime
                 Date updateTime = new Date((updateTime1.getTime()+6000));
                 SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                 java.sql.Date updateTime3=new java.sql.Date(updateTime2.getTime());
                 // Date updateTime =  format1.parse(updateTime3);

                 // java.sql.Date updateTime=   new java.sql.Date(updateTime3.getTime());

                 // updateTime = updateTime+1;
                 //调取第一行的数据第一个
                 //   updateTime = date0;

                 sql.append("insert into tb_building(id,community_name,community_id,name,update_time) values('");

                 sql.append(id+"','");

                 sql.append(communityName+"','");

                 sql.append(communityId+"','");

                 sql.append(name+"','");

                 sql.append(updateTime+"')");

                 stmt.executeUpdate(sql.toString());

                 i++;
             }
               }

            }
        } catch (ParseException e) {
            e.printStackTrace();

        } catch (FileNotFoundException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        } catch (IOException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        } catch (SQLException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();

        } finally{

            if(reader != null){

                try {

                    reader.close();

                } catch (IOException e) {

                    // TODO Auto-generated catch block

                    e.printStackTrace();

                }

            }

        }

    }




    }






