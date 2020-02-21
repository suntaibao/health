package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass =SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${out_put_path}")
    private String outPutPath; //从属性文件中读取要生成的html对应的目录

    //新增套餐信息,同时关联检查组
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        Integer setmealId = setmeal.getId();

        this.setSetmealAndCheckGroup(setmealId, checkgroupIds);
        //将图片名称保存到redis集合中
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
        //当添加套餐后需要重新生成静态页面,套餐列表页面,套餐详情页面
        generateMobileStaticHtml();

    }
    //生成当前方法所需的静态页面
    public void generateMobileStaticHtml(){
        //生成静态页面前,需查询数据
        List<Setmeal> all = setmealDao.findAll();
        //需要生成套餐列表静态页面
        generateMobileSetmealListHtml(all);

        //需要生成套餐详情静态页面
        generateMobileSetmealDetailHtml(all);

    }
    //生成套餐列表静态页面
    public void generateMobileSetmealListHtml(List<Setmeal> list){
        Map map = new HashMap();
        //为模板提供数据，用于生成静态页面
        map.put("setmealList",list);
        generateHtml("mobile_setmeal.ftl","m_setmeal.html",map);
    }
    //生成套餐详情静态页面（可能有多个）
    public void generateMobileSetmealDetailHtml(List<Setmeal> list){
        for (Setmeal setmeal : list) {
            Map map = new HashMap();
            map.put("setmeal",setmealDao.findById(setmeal.getId()));
            generateHtml("mobile_setmeal_detail.ftl","setmeal_detail_" + setmeal.getId() + ".html",map);
        }
    }

    //用于生成新的静态页面
    public void generateHtml(String templateName,String htmlPageName,Map map){
        Configuration configuration = freeMarkerConfigurer.getConfiguration();//获得配置对象

        Writer out=null;
        try {
            Template template = configuration.getTemplate(templateName);
            //构造输出流

            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                    new File(outPutPath + "/" + htmlPageName)),"UTF-8"));
            //输出文件
            template.process(map,out);
            out.close();
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
       Page<Setmeal> page =setmealDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public Setmeal findById(int id) {
        return  setmealDao.findById(id);
    }

    @Override
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }

    //设置套餐跟检查组多对多关系,操作t_setmeal_checkgroup
    public void setSetmealAndCheckGroup(Integer setmealId, Integer[] checkgroupIds) {
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("setmealId", setmealId);
                map.put("checkgroupId", checkgroupId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }
    }
}