package io.github.hlg212.gateway.util;

import io.github.hlg212.fcf.model.basic.BlackWhiteList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.List;

@Slf4j
public class CheckBlackWhiteListUtil {

    public static boolean check(String ip, List<BlackWhiteList> hbmds){
        StringBuilder blackIps = new StringBuilder();
        StringBuilder whiteIps = new StringBuilder();
        if(hbmds != null && hbmds.size() > 0) {
            for (BlackWhiteList blackWhiteList : hbmds) {
                if ("0".equals(blackWhiteList.getType())) {
                    blackIps.append(blackWhiteList.getStart() + "-" + blackWhiteList.getEnd() + ";");
                }
                if ("1".equals(blackWhiteList.getType())) {
                    whiteIps.append(blackWhiteList.getStart() + "-" + blackWhiteList.getEnd() + ";");
                }
            }
            try {
                String blackIp = blackIps.toString();
                if( StringUtils.isNotEmpty(blackIp) ) {
                    if (BlackWhiteListUtil.checkIp(ip, blackIp)) {
                        return false;
                    }
                    log.debug("ip[{}] not in blackIps [{}]",ip,blackIp);
                    return true;
                }
                if (BlackWhiteListUtil.checkIp(ip, whiteIps.toString())) {
                    log.debug("ip[{}]  in whiteIps [{}]",whiteIps.toString());
                    return true;
                }
            } catch (Exception e) {
                log.warn("check ip Exception ",e);
                return true;
            }
        }
        return false;
    }
}
