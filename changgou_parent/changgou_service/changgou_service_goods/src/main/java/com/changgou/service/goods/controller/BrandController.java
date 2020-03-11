package com.changgou.service.goods.controller;

import com.changgou.common.pojo.PageResult;
import com.changgou.common.pojo.Result;
import com.changgou.common.pojo.StatusCode;
import com.changgou.service.goods.service.BrandService;
import com.changou.goods.pojo.Brand;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;
import java.util.Map;

@RequestMapping("/brand")
@RestController
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping
    public Result findList(){
        List<Brand> brandList = brandService.findAll();
        return  new Result(true, StatusCode.OK,"查询成功",brandList);
    }
    @GetMapping("/{id}")
    public Result findById(@PathVariable(value="id") Integer id){
        Brand brand = brandService.findById(id);
        return new Result(true,StatusCode.OK,"查询成功",brand);
    }
    @PostMapping
    public Result add(@RequestBody Brand brand){
        brandService.add(brand);
        return new Result(true,StatusCode.OK,"添加成功");
    }
    @PutMapping("/{id}")
    public Result update(@PathVariable("id") Integer id,@RequestBody Brand brand){
        brand.setId(id);
        brandService.update(brand);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    @DeleteMapping(value="/{id}")
    public Result deleteById(@PathVariable("id") Integer id){
        brandService.delete(id);
        return  new Result(true,StatusCode.OK,"删除成功");
    }

    @GetMapping(value="/search")
    public Result  findList(@RequestParam Map searchMap){
        List list = brandService.findList(searchMap);
        return  new Result(true,StatusCode.OK,"查询成功",list);
    }
    @GetMapping(value="/search/{page}/{size}")
    public Result  findPage(@PathVariable int page,@PathVariable int size){
        Page<Brand> pageList = brandService.findPage(page, size);
        PageResult pageResult=new PageResult(pageList.getTotal(),pageList.getResult());
        return  new Result(true,StatusCode.OK,"查询成功",pageResult);
    }
    @GetMapping(value="/searchPage/{page}/{size}")
    public Result  findPage(@RequestParam Map searchMap,@PathVariable int page,@PathVariable int size){
        Page<Brand> pageList = brandService.findPage(searchMap,page, size);
        PageResult pageResult=new PageResult(pageList.getTotal(),pageList.getResult());
        return  new Result(true,StatusCode.OK,"查询成功",pageResult);
    }

}
