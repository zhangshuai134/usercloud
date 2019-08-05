package com.zs.user01.controller;

import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.zs.user01.entity.User;
import com.zs.user01.util.EasyExcelUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @GetMapping("/export")
    public void  exportExcel(HttpServletResponse response) {
        try(ServletOutputStream outputStream = response.getOutputStream()){

            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename="+System.currentTimeMillis()+".xlsx");

            List<User> userList = getUsers();

            List<List<String>> data = getData(userList);

            Table table = getTable();

            EasyExcelUtil.writeExcelWithStringList(outputStream, data, table, ExcelTypeEnum.XLSX);

        }catch (Exception e){

        }
    }
    @PostMapping("/upload")
    public void  uploadExcel(HttpServletRequest req) {
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) req;
        MultipartFile file = multiRequest.getFile("file");
        assert file != null;
        try (InputStream inputStream = file.getInputStream()){
            List<Object> objects = EasyExcelUtil.readExcelWithModel(inputStream, User.class, ExcelTypeEnum.XLSX);
            System.out.println(JSON.toJSONString(objects));
            for (Object object : objects) {
                if(object instanceof User){
                    User user = (User)object;
                    System.out.println(JSON.toJSONString(user));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private List<List<String>> getData(List<User> userList) {
        List<List<String>> data = new ArrayList<List<String>>();
        if(userList != null && userList.size() > 0){
            for (User user : userList) {
                List<String> item = new ArrayList<String>();
                item.add(user.getId()+"");
                item.add(user.getName());
                item.add(user.getAge()+"");
                data.add(item);
            }
        }
        return data;
    }

    private List<User> getUsers() {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setId(1);
        user1.setName("aa");
        user1.setAge(2);
        userList.add(user1);
        return userList;
    }

    private Table getTable() {
        Table table=new Table(3);
        List<List<String>> head = new ArrayList<List<String>>();
        List<String> headCoulumn1 = new ArrayList<String>();
        List<String> headCoulumn2 = new ArrayList<String>();
        List<String> headCoulumn3 = new ArrayList<String>();
        headCoulumn1.add("id");
        headCoulumn2.add("name");
        headCoulumn3.add("age");
        head.add(headCoulumn1);
        head.add(headCoulumn2);
        head.add(headCoulumn3);
        table.setHead(head);
        return table;
    }
}
