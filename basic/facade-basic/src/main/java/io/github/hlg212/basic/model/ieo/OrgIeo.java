package io.github.hlg212.basic.model.ieo;

import io.github.hlg212.fcf.Constants;
import io.github.hlg212.fcf.model.ImpExpModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Administrator
 * @date: 2022/5/24 23:18
 */
public class OrgIeo extends ImpExpModel {

    public OrgIeo()
    {
        setProps(new String[]{"id","name","code","shortName","ext1","ext2","ext3","memo","createTime","createUserName"});
        setRequiredProps(new String[]{"name","code"});
        setTitle("机构数据");
        setFileName("机构导出数据.xlsx");
//        Map dicProps = new HashMap<>();
//        dicProps.put("sybz", Constants.Dict.SYBZ);
//        dicProps.put("sfcjgly",Constants.Dict.SF);
//        setDictProps(dicProps);
    }
}
