
package io.github.hlg212.basic.api;

import io.github.hlg212.basic.model.bo.ClientAuthorityBo;
import io.github.hlg212.basic.model.bo.ClientBo;
import io.github.hlg212.basic.model.qco.ClientQco;
import io.github.hlg212.basic.service.ClientService;
import io.github.hlg212.fcf.api.ClientApi;
import io.github.hlg212.fcf.cache.Constants;
import io.github.hlg212.fcf.web.controller.QueryController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@Api(value="客户端api控制器",tags={"客户端api控制器"})
@CacheConfig(cacheNames = io.github.hlg212.fcf.cache.Constants.Client)
@AllArgsConstructor
public class ClientApiController implements QueryController<ClientBo, ClientQco> , ClientApi<ClientBo> {

    private final ClientService clientService;

    @ResponseBody
    @ApiOperation("根据主键获取实体信息")
    @ApiImplicitParam(name="id", value="主键ID", required=true, paramType="query")
    @Override
    @Cacheable(key = "#p0")
    public ClientBo getById(String id) {
        return clientService.getClientAndAuth(id);
    }

    @Override
    @Cacheable(key =  Constants.ClientKey.getAuthoritysByClientId_spel)
    public Collection<ClientAuthorityBo> getAuthoritys(String clientId) {
        return clientService.getRoleAuthority(clientId);
    }

}
