package com.changou.goods.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
@Table(name = "tb_brand")
public class Brand  implements Serializable {
    @Id
    private Integer id;
    private String name; //品牌名
    private String image;//品牌图片地址
    private String letter;//品牌首字母
    private Integer seq; //排序

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
