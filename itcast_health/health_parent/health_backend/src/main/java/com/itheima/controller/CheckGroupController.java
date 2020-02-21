package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import com.itheima.service.CheckItemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.BinaryClient;

import java.util.List;

@RestController
@RequestMapping("/checkgroup")
public class CheckGroupController {
    //新增检查组
    @Reference
    private CheckGroupService checkGroupService;
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try {
            checkGroupService.add(checkGroup,checkitemIds);

        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return  new Result(true,MessageConstant.ADD_CHECKGROUP_SUCCESS);
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return checkGroupService.pageQuery(queryPageBean);
    }
//根据id查询检查组
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
           CheckGroup checkGroup= checkGroupService.findById(id);
           return  new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }
    //根据检查组id 查询关联表中检查项id
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id){
        try {
            List<Integer> CheckItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(id);
            return  new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,CheckItemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }
    //编辑检查组
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup,Integer[] checkitemIds){
        try {
            checkGroupService.edit(checkGroup,checkitemIds);

        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL );
        }
        return  new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
    //查询所有检查组
    @RequestMapping("/findAll")
    public Result findAll(){
        try {
          List<CheckGroup> list= checkGroupService.findAll();
        return  new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS, list );
        } catch (Exception e) {
            e.printStackTrace();
            return  new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL  );
        }

    }
}
