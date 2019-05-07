package com.ding.running.vo;

/**
 * @ClassName HotelVo
 * @Author Leoren
 * @Date 2019/5/6 16:28
 * Description :
 * @Version v1.0
 */
public class HotelVo {

    private Integer id;

    private String type;

    private String text;

    private String picture;

    private Long price;

    private String name;

    public HotelVo() {
    }

    public HotelVo(Integer id, String type, String text, String picture, Long price, String name) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.picture = picture;
        this.price = price;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
