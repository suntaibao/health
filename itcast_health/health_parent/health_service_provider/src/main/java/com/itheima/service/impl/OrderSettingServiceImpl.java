package com.itheima.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
* 预约设置服务
* */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    //批量导入预约设置数据
    public void add(List<OrderSetting> data) {
        if(data!=null &&data.size()>0){
            for (OrderSetting orderSetting : data) {
                //判断当前日期是否预约设置
                long countByOrderDate = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if(countByOrderDate>0){
                    //已设置.修改更新
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else{
                    //没有设置,添加
                    orderSettingDao.add(orderSetting);

                }
            }
        }
    }

   //根据月份查询设置的预约设置数据
    public List<Map> getOrderSettingByMonth(String date) {//格式:yyyy-MM
        String begin=date+"-1"; //2020-3-1
        String end=date+"-31";//2020-3-31
        Map<String,String> map=new HashMap<>();
        map.put("begin",begin);
        map.put("end",end);
        //调用dao,根据日期范围查询预约设置数据
       List<OrderSetting> list=orderSettingDao.getOrderSettingByMonth(map);
       List<Map> result=new ArrayList<>();
       if(list!=null && list.size()>0){
        for (OrderSetting orderSetting : list) {
            Map<String,Object> m=new HashMap<>();
            m.put("date",orderSetting.getOrderDate().getDate());//获取日期
            m.put("number",orderSetting.getNumber());
            m.put("reservations",orderSetting.getReservations());
            result.add(m);
        }
       }
        return result;

    }

    //根据日期修改可预约人数
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if(count>0){
            //已设置,更新
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else{
            //未设置,插入
            orderSettingDao.add(orderSetting);
        }
    }


}
