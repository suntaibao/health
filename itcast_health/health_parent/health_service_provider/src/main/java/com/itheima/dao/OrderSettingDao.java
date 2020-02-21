package com.itheima.dao;

import com.itheima.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    void add(OrderSetting orderSetting);

    void editNumberByOrderDate(OrderSetting orderSetting);//根据日期起改预约人数

    long findCountByOrderDate(Date orderDate);//根据日期查询下有没有数据
    //根据月份查询设置的预约设置数据
    List<OrderSetting> getOrderSettingByMonth(Map<String, String> map);

    OrderSetting findByOrderDate(Date parseString2Date);
    //更新已预约人数
    public void editReservationsByOrderDate(OrderSetting orderSetting);
}
