package com.example.estatemanagement.service.impl;


import com.example.estatemanagement.dao.BuildingMapper;
import com.example.estatemanagement.domain.Building;
import com.example.estatemanagement.domain.Community;
import com.example.estatemanagement.service.BuildingService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;
import java.util.Map;

//////

////

 @Service
public class BuildingServiceImpl implements BuildingService {

     @Autowired
     private BuildingMapper buildingMapper;

     @Override
     public List<Building> findAll() {
         List<Building> buildings = buildingMapper.selectAll();
         return buildings;
     }

     @Override
     public Page<Building> search(Map searchMap) {
         //通用Mapper多条件搜索，标准写法
         Example example = new Example(Community.class);//指定查询的表tb_community
         //1.初始化分页条件
         int pageNum = 1;
         int pageSize = 2;
         if (searchMap != null) {
             Example.Criteria criteria = example.createCriteria();//创建查询条件
             //时间区间
             if (StringUtil.isNotEmpty((String) searchMap.get("startTime"))) {
                 criteria.andGreaterThanOrEqualTo("createTime", searchMap.get("startTime"));
             }
             if (StringUtil.isNotEmpty((String) searchMap.get("endTime"))) {
                 criteria.andLessThanOrEqualTo("createTime", searchMap.get("endTime"));
                 criteria.andLessThanOrEqualTo("createTime", searchMap.get("endTime"));
             }
             //名称模糊搜索
             if (StringUtil.isNotEmpty((String) searchMap.get("name"))) {
                 criteria.andLike("name", "%" + (String) searchMap.get("name") + "%");
             }
             //分页
            /*if(StringUtil.isNotEmpty((String) searchMap.get("pageNum"))){
                pageNum = Integer.parseInt((String) searchMap.get("pageNum"));
            }
            if(StringUtil.isNotEmpty((String) searchMap.get("pageSize"))){
                pageSize = Integer.parseInt((String) searchMap.get("pageSize"));
            }*/
             if ((Integer) searchMap.get("pageNum") != null) {
                 pageNum = (Integer) searchMap.get("pageNum");
             }
             if ((Integer) searchMap.get("pageSize") != null) {
                 pageSize = (Integer) searchMap.get("pageSize");
             }
         }
         PageHelper.startPage(pageNum, pageSize);//使用PageHelper插件完成分页
         Page<Building> buildings = (Page<Building>) buildingMapper.selectByExample(example);
         return buildings;
     }

     @Override
     public Boolean add(Building building) {
         int row = buildingMapper.insert(building);
         if (row > 0) {
             return true;
         } else {
             return false;
         }
     }

     @Override
     public Building findById(Integer id) {
         return buildingMapper.selectByPrimaryKey(id);
     }

     @Override
     public Boolean update(Building building) {
         int row = buildingMapper.updateByPrimaryKeySelective(building);
         if (row > 0) {
             return true;
         } else {
             return false;
         }
     }

     /**
      * @Override //此为修改状态，不需要！！！！！（修改状态是仪器是否启用需要的）
      * public Boolean updateStatus(String status, Integer id) {
      * Building building = new Building();
      * building.setId(id);
      * building.setStatus(status);
      * int row = buildingMapper.updateByPrimaryKeySelective(community);
      * if(row>0){
      * return true;
      * }else{
      * return false;
      * }
      * }
      **/

     @Override
     public Boolean del(List<Integer> ids) {
         for (Integer id : ids) {
             buildingMapper.deleteByPrimaryKey(id);
         }
         return true;
     }

 /**    @Override
     public ArrayList<String> getfile(String s) {
         try {
             String temp = null;
             //这里的filepath填自己的文件路径
             File f = new File("c:/down/test.txt");
             //指定读取编码用于读取中文
             InputStreamReader read = new InputStreamReader(new FileInputStream(f), "utf-8");
             ArrayList<String> readList = new ArrayList<String>();
             BufferedReader reader = new BufferedReader(read);
             //bufReader = new BufferedReader(new FileReader(filepath));
             while ((temp = reader.readLine()) != null && !"".equals(temp)) {
                 readList.add(temp);
             }
             read.close();
             return readList;

         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
     }


     @Override
     public String parseDate(String s) {
          SimpleDateFormat input_date = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
          SimpleDateFormat output_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          String finalDate = "";
          try {
          //这个dateStr也是一个文件路径
          Date parse_date = input_date.parse("c:\\down\\test1.txt");
          finalDate = output_date.format(parse_date);
          } catch (ParseException e) {
          e.printStackTrace();
          }

          return  finalDate;
     }**/



    // }


}

