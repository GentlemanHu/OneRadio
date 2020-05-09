package pers.hu.oneradio.net.model;

public class DjDetail {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRcmdtext() {
        return rcmdtext;
    }

    public void setRcmdtext(String rcmdtext) {
        this.rcmdtext = rcmdtext;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    private String rcmdtext;
    private String category;
    private String picUrl;


}
