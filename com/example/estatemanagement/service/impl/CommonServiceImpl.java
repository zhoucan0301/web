package com.example.estatemanagement.service.impl;


import com.example.estatemanagement.dao.CommonMapper;
import com.example.estatemanagement.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonMapper commonMapper;

    @Override
    public Integer InsertData(String date, String wd, String hpa, String mm) {
        return commonMapper.InsertData( date,  wd,  hpa,  mm);
    }

    @Override
    public boolean findData(String addtime){
        return commonMapper.findData( addtime);
    }

    @Override
    public List<Map<String, Object>> allData(){
        return commonMapper.allData( );
    }
}
