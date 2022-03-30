
package io.github.hlg212.basic.api;

import io.github.hlg212.basic.model.bo.ClientBo;
import io.github.hlg212.basic.model.qco.ClientQco;
import io.github.hlg212.fcf.api.ClientApi;
import io.github.hlg212.fcf.web.controller.QueryController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api(value="客户端api控制器",tags={"客户端api控制器"})
@CacheConfig(cacheNames = io.github.hlg212.fcf.cache.Constants.Client)
public class ClientApiController implements QueryController<ClientBo, ClientQco> , ClientApi<ClientBo> {
//    @Autowired
//    private KhdqxService khdqxService;
//
//    @Autowired
//    private ZyService zyService;
//
//    @Autowired
//    private YyService yyService;

    @ResponseBody
    @ApiOperation("根据主键获取实体信息")
    @ApiImplicitParam(name="id", value="主键ID", required=true, paramType="query")
    @Override
    @Cacheable(key = "#p0")
    public ClientBo getById(String id) {
        ClientBo clientBo = QueryController.super.getById(id);

//        QueryProperty qp = new QueryProperty();
//        qp.addCondition("khdid", "=", id);
//        List<KhdqxBo> lists = khdqxService.find(qp);
//        String jsids = "";
//        String zdqrs = "";
//        if(lists != null && lists.size() > 0){
//            for(KhdqxBo khdqxBo : lists){
//                jsids += khdqxBo.getJsid() + ",";
//                if("Y".equals(khdqxBo.getZdqr())){
//                    zdqrs += khdqxBo.getJsid() + ",";
//                }
//            }
//            khdBo.setJsids(jsids.substring(0,jsids.length()-1));
//            khdBo.setZdqrs(zdqrs);
//        }
//        if( StringUtils.isEmpty( khdBo.getJsids() ) )
//        {
//            khdBo.setJsids("def");
//            khdBo.setZdqrs("def");
//        }
        return clientBo;
    }

    @Override
    @Cacheable(key =  io.github.hlg212.fcf.cache.Constants.ClientKey.getAuthoritysByKhdid_spel)
    public Map getAuthoritysByKhdid(String khdid) {
        Map map = new HashMap();

//        QueryProperty qp = new QueryProperty();
//        qp.addCondition("khdid", "=", khdid);
//        List<KhdqxBo> qxs = khdqxService.find(qp);
//        if(qxs != null && qxs.size() > 0){
//            for(KhdqxBo khdqxBo : qxs){
//                List<ZyBo> zys = zyService.getZyByJsids(khdqxBo.getJsid());
//
//                List<String> lists = new ArrayList<String>();
//                if(zys != null && zys.size() > 0){
//                    for(ZyBo zy : zys){
//                        if(StringUtils.isNotBlank(zy.getQxdm())){
//                            lists.add(zy.getQxdm());
//                        }
//                    }
//                }
//
//                map.put(khdqxBo.getJsid(), lists);
//            }
//        }
        return map;
    }

}
