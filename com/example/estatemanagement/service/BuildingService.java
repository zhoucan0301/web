package com.example.estatemanagement.service;

import com.example.estatemanagement.domain.Building;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

//////////
///////////
public interface BuildingService {

    public List<Building> findAll();

    public Page<Building> search(Map searchMap);

    public Boolean add(Building building);

    public Building findById(Integer id);

    public Boolean update(Building building);

    //public Boolean updateStatus(String status, Integer id);

    public Boolean del(List<Integer> ids);

   /** ArrayList<String> getfile(String s);

    String parseDate(String s);**/


/////////
//////
}
