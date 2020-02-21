package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import com.itheima.utils.DateUtils;
import org.apache.xmlbeans.impl.jam.mutable.MElement;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/*
* 体检预约服务*/
import java.util.Date;
import java.util.List;
import java.util.Map;
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    @Override
    public Result order(Map map) throws Exception {
       // 1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行 预约
        String orderDate = (String) map.get("orderDate");
       OrderSetting orderSetting= orderSettingDao.findByOrderDate(DateUtils.parseString2Date(orderDate));
       if(orderSetting==null){
           //没有预约设置,无法预约
           return  new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
       }
        // 2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        int number = orderSetting.getNumber();
        int reservations = orderSetting.getReservations();
        if(reservations>=number){
            //预约已满
            return new Result(false,MessageConstant.ORDER_FULL);
        }
        // 3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约 则无法完成再次预约
        String telephone = (String) map.get("telephone");
        Member member = memberDao.findByTelephone(telephone);
        if(member!=null){
            //判断是否在重复预约
            Integer memberId = member.getId();//会员的id
            Date order_date = DateUtils.parseString2Date(orderDate);//预约日期
            String setmealId = (String) map.get("setmealId");//套餐id
            Order order = new Order(memberId, order_date, Integer.parseInt(setmealId));
            //根据条件查询
            List<Order> list = orderDao.findByCondition(order);
            if(list!=null && list.size()>0){
                //说明用户已经预约,不能重复预约
                return  new Result(false,MessageConstant.HAS_ORDERED);
            }

        }else {
            // 4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注 册并进行预约
             member=new Member();
            //当前用户不是会员，需要添加到会员表
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            memberDao.add(member);//自动完成会员注册


        }
        // 5、预约成功，更新当日的已预约人数
        Order order=new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(DateUtils.parseString2Date(orderDate));//预约日期
        order.setOrderType((String) map.get("orderType"));//预约类型
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setSetmealId(Integer.parseInt((String)map.get("setmealId")));//套餐id
        orderDao.add(order);
        orderSetting.setReservations(orderSetting.getReservations()+1);//设置已预约人数
        orderSettingDao.editReservationsByOrderDate(orderSetting);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }

    @Override
    public Map findById(Integer id) throws Exception {
        Map map = orderDao.findById4Detail(id);
        if(map != null){
            //处理日期格式
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate",DateUtils.parseDate2String(orderDate));
        }
        return map;
    }
    }

