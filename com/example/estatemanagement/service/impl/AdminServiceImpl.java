package com.example.estatemanagement.service.impl;

import com.example.estatemanagement.dao.AdminMapper;
import com.example.estatemanagement.domain.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper mapper;
    public Admin findAdmin(String username, String password) {
        return mapper.findAdmin(username,password);
    }


    @Override
    public List<Map<String, Object>> allData(){
        return mapper.allData( );
    }

    public int addData(String username,String password) {
        int i = mapper.addData(username,password);
        return i;

    }

    public int updateData(String id,String password) {

        int i = mapper.updateData(id,password);
        return i;

    }

    public int deleteData(Integer cid) {

        int i = mapper.deleteData(cid);
        return i;
    }
}
