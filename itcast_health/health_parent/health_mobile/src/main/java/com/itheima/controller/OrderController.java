package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import com.itheima.utils.SMSUtils;
import org.springframework.beans.factory.SmartFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map){

        //从redis中获取保存的验证码
        String telephone = (String) map.get("telephone");
        String validateCodeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        String validateCode= (String) map.get("validateCode");
        //比对验证码
        if (validateCode != null && validateCodeInRedis != null && validateCode.equals(validateCodeInRedis)) {
            //比对成功,调用服务完成预约
            map.put("orderType", Order.ORDERTYPE_WEIXIN);//设置预约类型,分为微信预约,电话预约
            Result result = null;
            try {
                result = orderService.order(map);
            } catch (Exception e) {
                e.printStackTrace();
                return  result;
            }
            if(result.isFlag()){
                //预约成功.可以为用户发送短信
                try {
                    SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone, (String) map.get("orderDate"));
                } catch (ClientException e) {
                    e.printStackTrace();
                }
            }
            return  result;
        }else{
            //比对失败
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }




    }
    //根据预约ID查询预约相关信息
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            Map map = orderService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
