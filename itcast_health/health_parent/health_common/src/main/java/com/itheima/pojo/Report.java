package com.itheima.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Report implements Serializable {
/*
*                reportDate:null,
                    todayNewMember :0,
                    totalMember :0,
                    thisWeekNewMember :0,
                    thisMonthNewMember :0,
                    todayOrderNumber :0,
                    todayVisitsNumber :0,
                    thisWeekOrderNumber :0,
                    thisWeekVisitsNumber :0,
                    thisMonthOrderNumber :0,
                    thisMonthVisitsNumber :0,
                    hotSetmeal :[
                        {name:'阳光爸妈升级肿瘤12项筛查（男女单人）体检套餐',setmeal_count:200,proportion:0.222},
                        {name:'阳光爸妈升级肿瘤12项筛查体检套餐',setmeal_count:200,proportion:0.222}
                    ]
                }*/
private String reportDate;
private Integer todayNewMember;
private Integer totalMember;
private Integer thisWeekNewMember;
private Integer thisMonthNewMember;
private Integer todayOrderNumber;
private Integer todayVisitsNumber;
private Integer thisWeekOrderNumber;
private Integer thisWeekVisitsNumber;
private Integer thisMonthOrderNumber;
private Integer thisMonthVisitsNumber;
private List<HotSetmeal> hotSetmeal;


    public Report() {
    }

    public Report(String reportDate, Integer todayNewMember, Integer totalMember, Integer thisWeekNewMember, Integer thisMonthNewMember, Integer todayOrderNumber, Integer todayVisitsNumber, Integer thisWeekOrderNumber, Integer thisWeekVisitsNumber, Integer thisMonthOrderNumber, Integer thisMonthVisitsNumber, List<HotSetmeal> hotSetmeal) {
        this.reportDate = reportDate;
        this.todayNewMember = todayNewMember;
        this.totalMember = totalMember;
        this.thisWeekNewMember = thisWeekNewMember;
        this.thisMonthNewMember = thisMonthNewMember;
        this.todayOrderNumber = todayOrderNumber;
        this.todayVisitsNumber = todayVisitsNumber;
        this.thisWeekOrderNumber = thisWeekOrderNumber;
        this.thisWeekVisitsNumber = thisWeekVisitsNumber;
        this.thisMonthOrderNumber = thisMonthOrderNumber;
        this.thisMonthVisitsNumber = thisMonthVisitsNumber;
        this.hotSetmeal = hotSetmeal;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public Integer getTodayNewMember() {
        return todayNewMember;
    }

    public void setTodayNewMember(Integer todayNewMember) {
        this.todayNewMember = todayNewMember;
    }

    public Integer getTotalMember() {
        return totalMember;
    }

    public void setTotalMember(Integer totalMember) {
        this.totalMember = totalMember;
    }

    public Integer getThisWeekNewMember() {
        return thisWeekNewMember;
    }

    public void setThisWeekNewMember(Integer thisWeekNewMember) {
        this.thisWeekNewMember = thisWeekNewMember;
    }

    public Integer getThisMonthNewMember() {
        return thisMonthNewMember;
    }

    public void setThisMonthNewMember(Integer thisMonthNewMember) {
        this.thisMonthNewMember = thisMonthNewMember;
    }

    public Integer getTodayOrderNumber() {
        return todayOrderNumber;
    }

    public void setTodayOrderNumber(Integer todayOrderNumber) {
        this.todayOrderNumber = todayOrderNumber;
    }

    public Integer getTodayVisitsNumber() {
        return todayVisitsNumber;
    }

    public void setTodayVisitsNumber(Integer todayVisitsNumber) {
        this.todayVisitsNumber = todayVisitsNumber;
    }

    public Integer getThisWeekOrderNumber() {
        return thisWeekOrderNumber;
    }

    public void setThisWeekOrderNumber(Integer thisWeekOrderNumber) {
        this.thisWeekOrderNumber = thisWeekOrderNumber;
    }

    public Integer getThisWeekVisitsNumber() {
        return thisWeekVisitsNumber;
    }

    public void setThisWeekVisitsNumber(Integer thisWeekVisitsNumber) {
        this.thisWeekVisitsNumber = thisWeekVisitsNumber;
    }

    public Integer getThisMonthOrderNumber() {
        return thisMonthOrderNumber;
    }

    public void setThisMonthOrderNumber(Integer thisMonthOrderNumber) {
        this.thisMonthOrderNumber = thisMonthOrderNumber;
    }

    public Integer getThisMonthVisitsNumber() {
        return thisMonthVisitsNumber;
    }

    public void setThisMonthVisitsNumber(Integer thisMonthVisitsNumber) {
        this.thisMonthVisitsNumber = thisMonthVisitsNumber;
    }

    public List<HotSetmeal> getHotSetmeal() {
        return hotSetmeal;
    }

    public void setHotSetmeal(List<HotSetmeal> hotSetmeal) {
        this.hotSetmeal = hotSetmeal;
    }
}
