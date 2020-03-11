package com.changgou.service.goods.service;

import com.changou.goods.pojo.Brand;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface BrandService {
    //品牌列表查询
    List<Brand> findAll();
    //根据id查询品牌数据
    Brand findById(Integer id);
    //新增品牌
    void add(Brand brand);
    //修改品牌
    void update(Brand brand);
    //删除品牌
    void delete(Integer id);
    //根据条件查询
     List<Brand> findList(Map<String, Object> searchMap);
     //分页查询
    Page<Brand> findPage(int page,int size);
    //品牌列表分页+条件查询
    Page<Brand> findPage(Map<String, Object> searchMap, int page, int size);

}
