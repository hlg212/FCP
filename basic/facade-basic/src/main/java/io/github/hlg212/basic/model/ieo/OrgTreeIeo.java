package io.github.hlg212.basic.model.ieo;

import io.github.hlg212.fcf.model.ImpExpModel;

/**
 * @author: Administrator
 * @date: 2022/5/24 23:18
 */
public class OrgTreeIeo extends ImpExpModel {

    public OrgTreeIeo()
    {
        setProps(new String[]{"orgTreeTypeId","org.id","org.name","org.code","org.shortName","parentOrgId","org.ext1","org.ext2","org.ext3"});
        setRequiredProps(new String[]{"orgTreeTypeId","org.id","org.name","org.code"});
        setTitle("机构树数据");
        setFileName("机构树导出数据.xlsx");
//        Map dicProps = new HashMap<>();
//        dicProps.put("sybz", Constants.Dict.SYBZ);
//        dicProps.put("sfcjgly",Constants.Dict.SF);
//        setDictProps(dicProps);
    }
}
