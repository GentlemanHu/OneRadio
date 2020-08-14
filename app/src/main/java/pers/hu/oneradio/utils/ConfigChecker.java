package pers.hu.oneradio.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import pers.hu.oneradio.feel.home.perfectview.OAlertDialog;

public class ConfigChecker {
    private  String config;
    public boolean checkConfig(Context context) {
        try {
            File file = new File(context.getFilesDir(), "config");
            System.out.println(context.getFilesDir());
            if (!file.exists()) {
                OAlertDialog dialog = new OAlertDialog(context);
                setConfig(dialog.getText(), file);
            } else if (file.exists()) {
                FileInputStream stream = new FileInputStream(file);
                int length = (int) file.length();
                byte[] bytes = new byte[length];
                try {
                    stream.read(bytes);
                } catch (Exception e) {
                    System.out.println("error");
                } finally {
                    stream.close();
                }
                String contents = new String(bytes);
                this.config=contents;
                System.out.println(contents+"   <---read from config");
            }
        } catch (Exception e) {
            Toast.makeText(context, "读取错误，请重启软件重试或联系管理员", Toast.LENGTH_LONG);
            e.printStackTrace();
        }
        return false;
    }

    public  String getConfig(){
        return config;
    }
    private void setConfig(String config, File file) {
        this.config = config;
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            stream.write(config.getBytes());
            stream.close();
        } catch (Exception e) {
            System.out.println("写入错误---请重试");
        }
        System.out.println("set config--->>");
    }

}
