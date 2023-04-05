package com.example.estatemanagement.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CommonMapper {
    @Autowired
    JdbcTemplate template;

    public Integer InsertData(String date,String wd,String hpa,String mm) {

        String sql="insert into tb_data (addtime,wd,hpa,mm) values (?,?,?,?)";
        int count= template.update(sql, new Object[]{date,wd,hpa,mm});
        System.out.println(count);
        return count;
    }


    public boolean findData(String addtime) {
        try{
            Map<String, Object> result = template.queryForMap("select * from tb_data where addtime = '"+ addtime+"'");
            if (result!=null ){
                return true;
            }else{
                return false;
            }
        } catch(Exception e){
            // 在错误发生时怎么处理
        }
        return false;
    }

    public List<Map<String, Object>> allData(){
        List<Map<String, Object>> allData=template.queryForList("select * from tb_data order by addtime desc");
        return allData;
    }


}
