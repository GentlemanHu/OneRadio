package pers.hu.oneradio.utils.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pers.hu.oneradio.net.model.DjDetail;

public class DjParser {
    private List<DjDetail> djDetails = new ArrayList<DjDetail>();

    //TODO:异常处理等，优化
    public void parseDjs(String djraws){
        JSONObject obj = JSON.parseObject(djraws);
        JSONArray array = obj.getJSONArray("djRadios");
        for (Object o:array
        ) {
            JSONObject oj = (JSONObject) o;
            DjDetail dj = JSON.parseObject(oj.toJSONString(),DjDetail.class);
            djDetails.add(dj);
        }
    }

    public List<DjDetail> getDjs(){
        return djDetails;
    }
}
