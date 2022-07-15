package io.github.hlg212.gatewayAdmin.api;

import io.github.hlg212.fcf.api.AnonymousResApi;
import io.github.hlg212.fcf.cache.Constants;
import io.github.hlg212.gatewayAdmin.model.bo.GaAnonymousResBo;
import io.github.hlg212.gatewayAdmin.model.qco.GaAnonymousResQco;
import io.github.hlg212.gatewayAdmin.service.GaAnonymousResService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(value="匿名资源api控制器",tags={"匿名资源api"})
@CacheConfig(cacheNames = Constants.AnonymousRes)
public class AnonymousResController implements AnonymousResApi {

    @Autowired
    private GaAnonymousResService gaAnonymousResService;

    @Cacheable(key =  Constants.AnonymousResKey.getAllUrls_spel)
    @Override
    public List<String> getAllUrls() {
        GaAnonymousResQco qco = new GaAnonymousResQco();

        List <GaAnonymousResBo> list = gaAnonymousResService.find(qco);
        List<String> result = null;
        if( list != null )
        {
            result= new ArrayList(list.size());
            for( GaAnonymousResBo n : list )
            {
                result.add(n.getMatchExpression());
            }
        }

        return  result;
    }
}
