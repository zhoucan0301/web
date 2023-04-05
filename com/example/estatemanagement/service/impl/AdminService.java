package com.example.estatemanagement.service.impl;

import com.example.estatemanagement.domain.Admin;

import java.util.List;
import java.util.Map;

public interface AdminService {
     Admin findAdmin(String username, String password);

     List<Map<String, Object>> allData();


}
