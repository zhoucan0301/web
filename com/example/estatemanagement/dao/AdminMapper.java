package com.example.estatemanagement.dao;

import com.example.estatemanagement.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AdminMapper {
    @Autowired
    JdbcTemplate template;

    public Admin findAdmin(String username, String password) {
        List<Admin> list = template.query("select * from tb_admin where username = ? && password = ?" ,
                new Object[]{username,password}, new BeanPropertyRowMapper(Admin.class));
        if (list!=null && list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

    public List<Map<String, Object>> allData(){
        List<Map<String, Object>> allData=template.queryForList("select * from tb_admin");
        return allData;
    }


    public int addData(String username,String password) {
        String sql="INSERT INTO tb_admin(username,password) VALUES(?,?)";
        int i = template.update(sql, new Object[]{username, password});
        return i;

    }

    public int updateData(String id,String password) {
        String sql="UPDATE tb_admin SET password=? WHERE id=? ";
        int i = template.update(sql, new Object[]{password, id});
        return i;

    }

    public int deleteData(Integer cid) {
        String sql="DELETE FROM tb_admin WHERE id=? ";
        int i = template.update(sql,cid);
        System.out.println(i);
        return i;
    }
}
