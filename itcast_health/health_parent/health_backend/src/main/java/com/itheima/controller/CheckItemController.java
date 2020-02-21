package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;
    //新增检查项
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        //RequestBody解析提交的json数据 ,封装成 CheckItem对象

        try {
            checkItemService.add(checkItem);
        } catch (Exception e) {
            //服务调用失败
            e.printStackTrace();
            return  new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result( true, MessageConstant.ADD_CHECKITEM_SUCCESS );
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkItemService.pageQuery(queryPageBean);
        return  pageResult;

    }
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")//权限校验
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            checkItemService.deleteById(id);
        } catch (Exception e) {
            //服务调用失败
            e.printStackTrace();
            return  new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result( true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        //RequestBody解析提交的json数据 ,封装成 CheckItem对象
        try {
            checkItemService.edit(checkItem);
        } catch (Exception e) {
            //服务调用失败
            e.printStackTrace();
            return  new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return new Result( true, MessageConstant.EDIT_CHECKITEM_SUCCESS );
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        //RequestBody解析提交的json数据 ,封装成 CheckItem对象
        try {
          CheckItem checkItem=  checkItemService.findById(id);
          return new Result( true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem );
        } catch (Exception e) {
            //服务调用失败
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("/findAll")
    public Result findAll(){
        //RequestBody解析提交的json数据 ,封装成 CheckItem对象
        try {
            List<CheckItem> list=  checkItemService.findAll();
            return new Result( true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list );
        } catch (Exception e) {
            //服务调用失败
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }


}
