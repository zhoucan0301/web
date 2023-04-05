package com.example.estatemanagement.controller;

import com.example.estatemanagement.common.ListPage;
import com.example.estatemanagement.domain.Admin;
import com.example.estatemanagement.service.impl.AdminServiceImpl;
import com.example.estatemanagement.common.ResBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AdminController {
    private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    AdminServiceImpl service;
    @RequestMapping("/admin/loginByPassword")
    @ResponseBody

    public ResBody loginByPassword(@RequestBody Map<String, Object> params,
                                   HttpSession session, HttpServletRequest request) {
        ResBody resBody = new ResBody();
        String username = (String)(params.get("username"));
        String password = (String)(params.get("password"));
        Admin admin = service.findAdmin(username,password);
        if (admin == null){
            resBody.setCode(500);
            resBody.setMsg("登录失败，请重新登录");
        }else {
            session.setAttribute("admin",admin);
            LOG.info(admin.toString());
            resBody.setCode(200);
            resBody.setMsg("登录成功");
        }
        return resBody;
    }



        @RequestMapping("/admin/admininfo")
        @ResponseBody
        public Map<String,Object> common(String page,HttpSession session, HttpServletRequest request) {
            Map<String,Object> result=new HashMap<>();
            if(StringUtils.isEmpty(page)||"undefined".equals(page)){
                page="1";
            }
            //获取所有数据
            List<Map<String,Object>> allData = (List<Map<String,Object>>) service.allData();
            //获取数据页数
            Integer pageMax=new ListPage<Map<String,Object>>().getPageSize(allData);
            //返回当前页的数据
            if(allData.size()>10) {
                if (Integer.parseInt(page) == pageMax) {
                    allData = new ListPage<Map<String,Object>>().getList(allData, Integer.parseInt(page),allData.size(), 10);
                } else {
                    allData = new ListPage<Map<String,Object>>().getList(allData, Integer.parseInt(page), Integer.parseInt(page) * 10, 10);
                }
            }
            result.put("pageMax",pageMax);
            result.put("page",Integer.parseInt(page));
            Admin admin =(Admin)session.getAttribute("admin");
            if(admin!=null){
                result.put("super",admin.getUsername());
            }

            result.put("datas",allData);
            return result;
        }

        @RequestMapping("/admin/delete")
        @ResponseBody
        public Map<String,Object> delete(String id,HttpSession session, HttpServletRequest request) {
            Map<String,Object> result=new HashMap<>();

        if(StringUtils.isEmpty(id)){
            result.put("message","该用户不存在!");
        }else{
            int i=service.deleteData(Integer.parseInt(id));
            if(i>0){
                result.put("message","删除成功!");
            }else{
                result.put("message","删除失败!");
            }
        }


        return result;
    }
    @RequestMapping("/admin/addAdmin")
    @ResponseBody
    public Map<String,Object> addAdmin(String username,String password,HttpSession session, HttpServletRequest request) {
        Map<String,Object> result=new HashMap<>();

        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            result.put("message","添加失败!");
        }else{
            int i=service.addData(username,password);
            if(i>0){
                result.put("message","添加成功!");
            }else{
                result.put("message","添加失败!");
            }
        }


        return result;
    }

    @RequestMapping("/admin/updateAdmin")
    @ResponseBody
    public Map<String,Object> updateAdmin(String id,String password,HttpSession session, HttpServletRequest request) {
        Map<String,Object> result=new HashMap<>();

        if(StringUtils.isEmpty(id)||StringUtils.isEmpty(password)){
            result.put("message","更新失败!");
        }else{
            int i=service.updateData(id,password);
            if(i>0){
                result.put("message","更新成功!");
            }else{
                result.put("message","更新失败!");
            }
        }


        return result;
    }


}
