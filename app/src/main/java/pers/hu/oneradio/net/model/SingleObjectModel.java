package pers.hu.oneradio.net.model;

public class SingleObjectModel {


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public SongData[] getData(){
        return data;
    }

    public void setData(SongData[] data) {
        this.data = data;
    }

    private SongData[] data;
    private int code;

}
