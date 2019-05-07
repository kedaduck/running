package com.ding.running.vo;

/**
 * @ClassName GoodVo
 * @Author Leoren
 * @Date 2019/5/6 16:06
 * Description :
 * @Version v1.0
 */
public class GoodVo {

    private Integer id;

    private String name;

    private String text;

    private String picture;

    private Long price;

    private String storename;

    public GoodVo() {
    }

    public GoodVo(Integer id, String name, String text, String picture, Long price, String storename) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.picture = picture;
        this.price = price;
        this.storename = storename;
    }

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }
}
