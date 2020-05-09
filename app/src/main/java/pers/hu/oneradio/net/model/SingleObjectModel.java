package pers.hu.oneradio.net.model;

@Deprecated
public class SingleObjectModel {

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Song[] getData(){
        return data;
    }

    public void setData(Song[] data) {
        this.data = data;
    }

    private Song[] data;
    private int code;

}
