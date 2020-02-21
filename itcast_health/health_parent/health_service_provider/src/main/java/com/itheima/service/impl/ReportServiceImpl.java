package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.entity.Result;
import com.itheima.pojo.HotSetmeal;
import com.itheima.pojo.Report;
import com.itheima.service.ReportService;
import com.itheima.utils.DateUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
/*
*
* 运营数据统计服务*/
@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl  implements  ReportService{
   @Autowired
    private MemberDao memberDao;
   @Autowired
    private OrderDao orderDao;
   //查询运营数据
    public Report getBusinessReportData() throws Exception {
        //报表日期
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        //获得本周一日期
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        //获取本月第一天日期
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        //本日新增会员数
        Integer todayNewMember = memberDao.findMemberCountAfterDate(today);
       // 总会员数
        Integer totalMember = memberDao.findMemberTotalCount();
        //本周新增会员数
        Integer thisWeekNewMember = memberDao.findMemberCountAfterDate(thisWeekMonday);
        //本月新增会员数
        Integer thisMonthNewMember = memberDao.findMemberCountAfterDate(firstDay4ThisMonth);
        //今日预约数
        Integer todayOrderNumber = orderDao.findOrderCountByDate(today);
        //本周预约数
        Integer thisWeekOrderNumber = orderDao.findOrderCountAfterDate(thisWeekMonday);
        //本月预约数
        Integer thisMonthOrderNumber = orderDao.findOrderCountAfterDate(firstDay4ThisMonth);
        //今日到诊数
        Integer todayVisitsNumber = orderDao.findVisitsCountByDate(today);
        //本周到诊数
        Integer thisWeekVisitsNumber =orderDao.findVisitsCountAfterDate(thisWeekMonday);
        //本月到诊数
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(firstDay4ThisMonth);

        List<HotSetmeal> hotSetmeal = orderDao.findHotSetmeal();


        Report report=new Report();
        report.setHotSetmeal(hotSetmeal);
        report.setReportDate(today);
        report.setTodayNewMember(todayNewMember);
        report.setTotalMember(totalMember);
        report.setThisWeekNewMember(thisWeekNewMember);
        report.setThisMonthNewMember(thisMonthNewMember);
        report.setTodayOrderNumber(todayOrderNumber);


        report.setTodayVisitsNumber(todayVisitsNumber);
        report.setThisWeekOrderNumber(thisWeekOrderNumber);
        report.setThisWeekVisitsNumber(thisWeekVisitsNumber);
        report.setThisMonthVisitsNumber(thisMonthVisitsNumber);
        report.setThisMonthOrderNumber(thisMonthOrderNumber);
        return report;
    }




}
