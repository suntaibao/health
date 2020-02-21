package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;
    public void clearImg(){
        //根据redis中保存的两个set集合计算差值.获得垃圾图片名称
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if(set!=null){
            for (String picName : set) {
                //删除七牛云服务器的垃圾图片
                QiniuUtils.deleteFileFromQiniu(picName);
                //删除set集合中的图片名称
                jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,picName);
                System.out.println("清理垃圾图片"+picName);
            }
        }
    }
}
