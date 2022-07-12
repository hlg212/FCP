package io.github.hlg212.basic.model.ieo;

import io.github.hlg212.fcf.Constants;
import io.github.hlg212.fcf.model.ImpExpModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Administrator
 * @date: 2022/5/24 23:18
 */
public class AppIeo extends ImpExpModel {

    public AppIeo()
    {
        setProps(new String[]{"id","name","code","type","indexUrl","loginUrl","memo","createTime","createUserName"});
        setRequiredProps(new String[]{"name","code","type"});
        setTitle("应用数据");
        setFileName("应用导出数据.xlsx");
//        Map dicProps = new HashMap<>();
//        dicProps.put("sybz", Constants.Dict.SYBZ);
//        dicProps.put("sfcjgly",Constants.Dict.SF);
//        setDictProps(dicProps);
    }
}
