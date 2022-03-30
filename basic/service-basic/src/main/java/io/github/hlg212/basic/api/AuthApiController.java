package io.github.hlg212.basic.api;

import io.github.hlg212.basic.model.bo.AppBo;
import io.github.hlg212.basic.model.bo.ResBo;
import io.github.hlg212.fcf.api.AuthApi;
import io.swagger.annotations.Api;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** 
 * 资源Controller
 *
 * @author wuwei
 * @date 2018年11月05日
 */
@RestController
@Api(value="权限api控制器",tags={"权限api控制器"})
@CacheConfig(cacheNames = io.github.hlg212.fcf.cache.Constants.Auth)
public class AuthApiController implements AuthApi {
//    @Autowired
//    private ZyService zyService;
//
//    @Autowired
//    private YyService yyService;
//
//    @Autowired
//    private YhService yhService;

    @Override
    @Cacheable(key = io.github.hlg212.fcf.cache.Constants.AuthKey.getAuthoritysByUserId_spel)
    public List<String> getPermissionsByUserId(String appCode, String userId) {
//        List<ZyBo> zys = null;
//        if(  StringUtils.isEmpty( appCode ) )
//        {
//            //zys = zyService.getZyByYhid( userId);
//            zys = zyService.getSyZyByYhid( userId);
//        }
//        else {
//            zys = getZyList(appCode, userId);
//        }
//        List<String> lists = getQxdmList(zys);
//        return lists;
        return null;
    }

    @Override
    @Cacheable(key = io.github.hlg212.fcf.cache.Constants.AuthKey.getAllAuthoritysByUserId_spel)
    public List<String> getAllPermissionsByUserId(String userId) {

        //List<ZyBo> zys = zyService.getZyByYhid( userId);
//        List<ZyBo> zys = zyService.getSyZyByYhid( userId);
//
//        List<String> lists = getQxdmList(zys);
//        return lists;
        return null;
    }

//    private List<String> getQxdmList( List<ZyBo> zys )
//    {
//        List<String> lists = new ArrayList<String>();
//        Set<String> sets = new LinkedHashSet<>();
//        if(zys != null && zys.size() > 0){
//            for(ZyBo zy : zys){
//                if(StringUtils.isNotBlank(zy.getQxdm())){
//                    sets.add(zy.getQxdm());
//                }
//            }
//        }
//        lists.addAll(sets);
//        return lists;
//    }

    @Override
    @Cacheable(key = io.github.hlg212.fcf.cache.Constants.AuthKey.getMenuByUserId_spel)
    public List<ResBo> getMenuByUserId(String appCode, String userId) {
//        List<ZyBo> zys = getZyList(appCode, userId);
//        List<ZyBo> lists = new ArrayList<ZyBo>();
//        if(zys != null && zys.size() > 0){
//            for(ZyBo zy : zys){
//                if(Constants.Dic.ZY_ZYLX_CD.equals(zy.getZylx())){
//                    lists.add(zy);
//                }
//            }
//        }
//        return TreeHelper.buildTree(lists);
        return null;
    }

//    private List<ZyBo> getZyList(String code, String yhid){
//        List<ZyBo> zys = null;
//        YyBo yyBo = yyService.getYyByCode(code);
//        if( yyBo != null ) {
//            if (yhService.isCjgly(yhid)) {
//                zys = zyService.getZyByYyid(yyBo.getId());
//            } else {
//                zys = zyService.getZyByYyidAndYhid(yyBo.getId(), yhid);
//            }
//        }
//        return zys;
//    }

    @Override
    @Cacheable(key = io.github.hlg212.fcf.cache.Constants.AuthKey.getAllAuthRes_spel)
    public List<ResBo> getAllAuthRes()
    {
//        List<ZyBo> zys = zyService.getAllAuthRes();
//        return zys;
        return null;
    }

    @Override
    @Cacheable(key = io.github.hlg212.fcf.cache.Constants.AuthKey.getAppsByUserId_spel)
    public  List<AppBo> getAppsByUserId(String userId, String type) {
        //return yyService.getYyByDlrAndYylx(userId,type);
        return null;
    }
}
