package com.ding.running.vo;

/**
 * @ClassName RestaurantVo
 * @Author Leoren
 * @Date 2019/5/6 16:51
 * Description :
 * @Version v1.0
 */
public class RestaurantVo {
    private Integer id;

    private String name;

    private String foodname;

    private String picture;

    private Long price;

    private String text;

    public RestaurantVo() {
    }

    public RestaurantVo(Integer id, String name, String foodname, String picture, Long price, String text) {
        this.id = id;
        this.name = name;
        this.foodname = foodname;
        this.picture = picture;
        this.price = price;
        this.text = text;
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

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
