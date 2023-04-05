package com.example.estatemanagement.common;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListPage<T> {
    public  Integer getPageSize(List<T> list){
        if(list!=null){
        	int size=list.size();
            int page=size/10;
            int page2=size%10;
            if(page2>0) page+=1;
             return page;
        }else{
        	return 1;
        }
    	
    
    }
    /*
    *page:页码
    * end:总条数
    * size:每页显示数
     */
    public  List<T> getList(List<T> list,Integer page,Integer end,Integer size){
        return list.subList((page-1)*size,end);
    }
    
    /**
     * 将List<Object>转换为List<Map<String,Object>>
     * @param list
     * @return
     */
    private List<Map<String,Object>> convertListMap(List<Object> list){
        List<Map<String,Object>> maps=new ArrayList<Map<String,Object>>();
        for(Object obj:list){
            Class c = obj.getClass();
            Field[] f = c.getDeclaredFields();
            Map<String,Object> map=new HashMap<String, Object>();
            for(Field fie : f){
                try {
                    fie.setAccessible(true);//取消语言访问检查
                    map.put(fie.getName(), fie.get(obj));//获取私有变量值
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            //获取父类的私有属性
            for(Field fie : c.getSuperclass().getDeclaredFields()){
                try {
                    fie.setAccessible(true);//取消语言访问检查
                    map.put(fie.getName(), fie.get(obj));//获取私有变量值
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            maps.add(map);
        }
        return maps;
    }
}
