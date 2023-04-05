package com.example.estatemanagement.service;

import com.example.estatemanagement.domain.Community;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface CommunityService {

    public List<Community> findAll();

    public Page<Community> search(Map searchMap);

    public Boolean add(Community community);

    public Community findById(Integer id);

    public Boolean update(Community community);

    public Boolean updateStatus(String status, Integer id);

    public Boolean del(List<Integer> ids);

}
