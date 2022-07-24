package io.github.hlg212.basic.util;

import io.github.hlg212.basic.model.enums.ResCategoryEnum;
import io.github.hlg212.basic.model.enums.ResTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * @author: Administrator
 * @date: 2022/7/16 18:14
 */
public class ResCategoryHelper {

    static  Map<String, List<String>> RESCATEGORY_RESTYPE_MAPPING = new HashMap<>();
    static {
        RESCATEGORY_RESTYPE_MAPPING.put(ResCategoryEnum.MENU.getValue(),priConvertResTypes(ResCategoryEnum.MENU.getValue()));
        RESCATEGORY_RESTYPE_MAPPING.put(ResCategoryEnum.IFACE.getValue(),priConvertResTypes(ResCategoryEnum.IFACE.getValue()));
        RESCATEGORY_RESTYPE_MAPPING.put(ResCategoryEnum.DATA.getValue(),priConvertResTypes(ResCategoryEnum.DATA.getValue()));
    }


    public static List<String> convertResTypes(String resCategory){
        return RESCATEGORY_RESTYPE_MAPPING.get(resCategory.toUpperCase());
    }

    private static List<String> priConvertResTypes(String resCategory) {
        List<String> types = new ArrayList<>();
        if (StringUtils.equalsIgnoreCase(ResCategoryEnum.MENU.getValue(),resCategory)) {
            types.add(ResTypeEnum.MENU.getValue());
            types.add(ResTypeEnum.COMPONENT.getValue());
        } else if ( StringUtils.equalsIgnoreCase(ResCategoryEnum.IFACE.getValue(),resCategory)) {
            types.add(ResTypeEnum.INTERFACE.getValue());
        } else if ( StringUtils.equalsIgnoreCase( ResCategoryEnum.DATA.getValue(),resCategory) ) {
            types.add(ResTypeEnum.DATA.getValue());
        }
        return types;
    }

}
