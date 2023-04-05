package com.example.estatemanagement.controller;

import com.example.estatemanagement.common.MessageConstant;
import com.example.estatemanagement.common.PageResult;
import com.example.estatemanagement.common.Result;
import com.example.estatemanagement.common.StatusCode;
import com.example.estatemanagement.domain.Building;
import com.example.estatemanagement.service.BuildingService;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Auth: zhuan
 * @Desc:
 *
 * !!!!!此页面为仪器三要素的最新数据显示！！！！！
 */
@RestController
@RequestMapping("/building")
public class BuildingController {

    @Autowired
    private BuildingService buildingService;

    @RequestMapping("/find")
    public Result find(){
        List<Building> all = buildingService.findAll();
        return new Result(false,200,"请求成功adasdasdas",all);
    }
    @RequestMapping("/search")
    public PageResult search(@RequestBody Map searchMap){
        Page<Building> page = buildingService.search(searchMap);
        return new PageResult(true, StatusCode.OK, MessageConstant.COMMUNITY_SEARCH_SUCCESS,page.getResult(),page.getTotal());
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Building building){
        Boolean add = buildingService.add(building);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_ADD_SUCCESS);
    }
    @RequestMapping("/findById")
    public Result findById(Integer id){
        Building building = buildingService.findById(id);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_FIND_BY_ID_SUCCESS,building);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody Building building){
        Boolean add = buildingService.update(building);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_UPDATE_SUCCESS);
    }
    // /community/updateStatus/0/1
   /** @RequestMapping("/updateStatus/{status}/{id}")
    public Result updateStatus(@PathVariable("status") String status,@PathVariable("id") Integer id){
        Boolean flag = buildingService.updateStatus(status,id);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_UPDATE_STATUS_SUCCESS);
    }**/
    @RequestMapping("/del")
    public Result del(@RequestBody List<Integer> ids){
        Boolean flag = buildingService.del(ids);
        return new Result(true,StatusCode.OK,MessageConstant.COMMUNITY_DELETE_SUCCESS);
    }

    ///////
    //txt文件封装进数据库

   /** public void convertData() throws ClassNotFoundException, SQLException {
        //txt文件读取
        File file = new File("c:/down/test.txt");
        ArrayList<String> list=buildingService.getfile("c:/down/test.txt");
        //List<String> list1=new ArrayList<String>();
        String driver = "com.mysql.jdbc.Driver";

        String url = "jdbc:mysql://localhost:3306/heima-estate?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai";

        String username = "root";

        String password = "root";

        // BufferedReader reader = new BufferedReader(new FileReader(file));

        Connection conn = null;

        Statement stmt = null;

        Class.forName(driver);

        conn = DriverManager.getConnection(url, username, password);

        //reader = new BufferedReader(new FileReader(file));

        stmt = (Statement) conn.createStatement();

        int num=list.size();
        for (int i = 0; i < num; i++) {
            //System.out.println(list.get(i));
         //_________________________________________-
            //数据库的连接操作





            if (list.get(i)!=null) {
                String[] s=list.get(i).split(" ");

                String data=buildingService.parseDate(s[2]);
                //txt每行可以分割成6个字符串存到是s[],
                String sql = "insert into tb_building(id,communityName,communityId,name,updateTime) values('" + s[0] + "','" +data
                        + "''" +s[2] + "''" + s[3] +"''" + s[4] + "')";
                //博主在自己文档加了service方法没有告诉你，你无法调用，其他的都因该解决了
             //   MessageService.add(sql);
               // stmt.executeUpdate(sql.toString());

            }

        }

        System.out.println("添加成功");
    }**/



}
