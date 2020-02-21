package com.itheima.pojo;

import java.io.Serializable;

public class HotSetmeal implements Serializable {
    // {name:'阳光爸妈升级肿瘤12项筛查（男女单人）体检套餐',setmeal_count:200,proportion:0.222},
    private String name;
    private Integer setmeal_count;
    private float proportion;

    public HotSetmeal() {
    }

    public HotSetmeal(String name, Integer setmeal_count, float proportion) {
        this.name = name;
        this.setmeal_count = setmeal_count;
        this.proportion = proportion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSetmeal_count() {
        return setmeal_count;
    }

    public void setSetmeal_count(Integer setmeal_count) {
        this.setmeal_count = setmeal_count;
    }

    public float getProportion() {
        return proportion;
    }

    public void setProportion(float proportion) {
        this.proportion = proportion;
    }
}
