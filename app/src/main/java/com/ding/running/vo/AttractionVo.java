package com.ding.running.vo;

/**
 * @ClassName AttractionVo
 * @Author Leoren
 * @Date 2019/5/6 12:05
 * Description :
 * @Version v1.0
 */
public class AttractionVo {

    private Integer id;

    private String attractioname;

    private String text;

    private String picture;

    public AttractionVo() {
    }

    public AttractionVo(Integer id, String attractioname, String text, String picture) {
        this.id = id;
        this.attractioname = attractioname;
        this.text = text;
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "AttractionVo{" +
                "id=" + id +
                ", attractioname='" + attractioname + '\'' +
                ", text='" + text + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttractioname() {
        return attractioname;
    }

    public void setAttractioname(String attractioname) {
        this.attractioname = attractioname;
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
}
