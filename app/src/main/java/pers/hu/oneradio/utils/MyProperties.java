package pers.hu.oneradio.utils;

public class MyProperties {
    private static MyProperties mInstance= null;

    public String config;

    protected MyProperties(){}

    public static synchronized MyProperties getInstance() {
        if(null == mInstance){
            mInstance = new MyProperties();
        }
        return mInstance;
    }
}