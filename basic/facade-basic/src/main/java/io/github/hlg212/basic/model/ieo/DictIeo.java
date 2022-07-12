package io.github.hlg212.basic.model.ieo;

import io.github.hlg212.fcf.model.ImpExpModel;

/**
 * @author: Administrator
 * @date: 2022/5/24 23:18
 */
public class DictIeo extends ImpExpModel {

    public DictIeo()
    {
        setProps(new String[]{"id","appId","parentDictId","name","code","val","memo","createTime","createUserName"});
        setRequiredProps(new String[]{"name","code","val"});
        setTitle("字典数据");
        setFileName("字典导出数据.xlsx");
//        Map dicProps = new HashMap<>();
//        dicProps.put("sybz", Constants.Dict.SYBZ);
//        dicProps.put("sfcjgly",Constants.Dict.SF);
//        setDictProps(dicProps);
    }
}
