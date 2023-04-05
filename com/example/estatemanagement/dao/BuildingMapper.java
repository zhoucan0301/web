package com.example.estatemanagement.dao;

import com.example.estatemanagement.domain.Building;
import com.example.estatemanagement.domain.Community;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface BuildingMapper extends Mapper <Building>{
}
