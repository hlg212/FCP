package io.github.hlg212.basic.model.ieo;

import io.github.hlg212.fcf.Constants;
import io.github.hlg212.fcf.model.ImpExpModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Administrator
 * @date: 2022/5/24 23:18
 */
public class RoleIeo extends ImpExpModel {

    public RoleIeo()
    {
        setProps(new String[]{"id","name","code","type","sortNum","memo","createTime","createUserName"});
        setRequiredProps(new String[]{"name","code","type"});
        setTitle("角色数据");
        setFileName("角色导出数据.xlsx");
//        Map dicProps = new HashMap<>();
//        dicProps.put("sybz", Constants.Dict.SYBZ);
//        dicProps.put("sfcjgly",Constants.Dict.SF);
//        setDictProps(dicProps);
    }
}
