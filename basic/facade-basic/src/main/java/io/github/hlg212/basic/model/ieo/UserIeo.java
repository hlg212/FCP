package io.github.hlg212.basic.model.ieo;

import io.github.hlg212.fcf.Constants;
import io.github.hlg212.fcf.model.ImpExpModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Administrator
 * @date: 2022/5/24 23:18
 */
public class UserIeo extends ImpExpModel {

    public UserIeo()
    {
        setProps(new String[]{"id","org.code","org.name","account","name","phone","memo","createTime","createUserName"});
        setRequiredProps(new String[]{"org.code","account","name"});
        setTitle("用户数据");
        setFileName("用户导出数据.xlsx");
//        Map dicProps = new HashMap<>();
//        dicProps.put("sybz", Constants.Dict.SYBZ);
//        dicProps.put("sfcjgly",Constants.Dict.SF);
//        setDictProps(dicProps);
    }
}
