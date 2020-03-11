package com.changgou.service.goods.service.impl;

import com.changgou.common.util.PinYinUtils;
import com.changgou.service.goods.dao.BrandMapper;
import com.changgou.service.goods.service.BrandService;
import com.changou.goods.pojo.Brand;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;
    //查询品牌列表
    public List<Brand> findAll() {
        List<Brand> brandList = brandMapper.selectAll();
        return brandList;
    }

   //根据id查询品牌
    public Brand findById(Integer id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    //新增
    @Transactional
    public void add(Brand brand) {
        String name = brand.getName();
        if (StringUtils.isBlank(name)) {
            throw new RuntimeException("参数非法");
        }

        // 1 判断 填写 品牌首字母
        String letter = brand.getLetter();
        if (StringUtils.isBlank(letter)) {  // 没传参数
            letter = PinYinUtils.getFirstLetter(name);  //  name ? null
        } else {
            // 转成大小存储到数据库
            letter = letter.toUpperCase();
        }
        brand.setLetter(letter);

        brandMapper.insertSelective(brand);
    }

    //修改
    @Transactional
    public void update(Brand brand) {
        String name = brand.getName();
        if (StringUtils.isBlank(name)) {
            throw new RuntimeException("参数非法");
        }

        // 1 判断 填写 品牌首字母
        String letter = brand.getLetter();
        if (StringUtils.isBlank(letter)) {  // 没传参数
            letter = PinYinUtils.getFirstLetter(name);  //  name ? null
        } else {
            // 转成大小存储到数据库
            letter = letter.toUpperCase();
        }
        brand.setLetter(letter);
        brandMapper.updateByPrimaryKeySelective(brand);
    }

    //删除
    @Transactional
    public void delete(Integer id) {
        brandMapper.deleteByPrimaryKey(id);
    }

   //条件查询
    public List<Brand> findList(Map<String, Object> searchMap) {
        Example example=new Example(Brand.class);
        //封装查询条件
        Example.Criteria criteria = example.createCriteria();
        if(searchMap!=null){
            //品牌名称模糊查询
            if(searchMap.get("name")!=null&&!"".equals(searchMap.get("name"))){
                criteria.andLike("name","%"+searchMap.get("name")+"%");
            }
            //品牌首字母查询
            if(searchMap.get("letter")!=null&&!"".equals(searchMap.get("letter"))){
              criteria.andEqualTo("letter",searchMap.get("letter"));
            }

        }
      return   brandMapper.selectByExample(example);

    }

    //分页查询
    public Page<Brand> findPage(int page, int size) {
        PageHelper.startPage(page,size);
        //这两行代码中间不能有其他查询,默认会选择最近的查询分页
        Page<Brand>  page1 = (Page<Brand>) brandMapper.selectAll();
        return page1;
    }

    @Override
    public Page<Brand> findPage(Map<String, Object> searchMap, int page, int size) {
        //设置分页
        PageHelper.startPage(page,size);
        //条件查询
        Example example=new Example(Brand.class);
        //封装查询条件
        Example.Criteria criteria = example.createCriteria();
        if(searchMap!=null){
            //品牌名称模糊查询
            if(searchMap.get("name")!=null&&!"".equals(searchMap.get("name"))){
                criteria.andLike("name","%"+searchMap.get("name")+"%");
            }
            //品牌首字母查询
            if(searchMap.get("letter")!=null&&!"".equals(searchMap.get("letter"))){
                criteria.andEqualTo("letter",searchMap.get("letter"));
            }

        }
        Page<Brand> pageInfo = (Page<Brand>) brandMapper.selectByExample(example);

        return pageInfo;
    }

}
