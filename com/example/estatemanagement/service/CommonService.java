package com.example.estatemanagement.service;

import java.util.List;
import java.util.Map;

public interface CommonService {
     Integer InsertData(String date,String wd,String hpa,String mm);


      boolean findData(String addtime);


     List<Map<String, Object>> allData();

}
