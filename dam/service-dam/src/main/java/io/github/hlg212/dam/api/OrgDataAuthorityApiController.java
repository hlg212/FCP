package io.github.hlg212.dam.api;

import io.github.hlg212.basic.model.bo.OrgTreeBo;
import io.github.hlg212.basic.model.bo.OrgTreeParam;
import io.github.hlg212.dam.api.client.OrgTreeApi;
import io.github.hlg212.fcf.api.UserApi;
import io.github.hlg212.fcf.api.dam.OrgDataAuthorityApi;
import io.github.hlg212.fcf.model.basic.IUser;
import io.github.hlg212.fcf.util.CollectionHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(value = "机构数据权限api控制器", tags = {"机构数据权限api控制器"})
//@CacheConfig(cacheNames = io.github.hlg212.frame.cache.Constants.App)
public class OrgDataAuthorityApiController implements OrgDataAuthorityApi {

    @Autowired
    private UserApi userApi;
    @Autowired
    private OrgTreeApi orgTreeApi;

    @ResponseBody
    @ApiOperation("用户某业务下的机构权限范围")
    @ApiImplicitParam(name = "uid", value = "用户id", paramType = "query")
    @Override
    // @Cacheable(key = "#p0")
    @RequestMapping(value="/getAuthoritys", method = {RequestMethod.GET,RequestMethod.POST})
    public List<String> getAuthoritys(String uid, String param) {
        List<OrgTreeBo> orgTreeBos = null;
        String pid = null;
        if (StringUtils.isNotEmpty(uid)) {
            IUser iuser = userApi.getById(uid);
             if (iuser != null & StringUtils.isNotEmpty(iuser.getOrgId())) {
                pid = iuser.getOrgId();
            }
        }
        else
        {
            return null;
        }
        String params[] = param.split("::");
        String orgTreeTypeCode = params[0];
        String proName = "orgId";
        if (params.length > 1) {
            proName = params[1];
        }
        List<String> result = new ArrayList<>();
        OrgTreeParam treeParam = new OrgTreeParam();
        treeParam.setOrgTreeTypeCode(orgTreeTypeCode);
        treeParam.setOrgId(pid);
        orgTreeBos = orgTreeApi.getChildList(treeParam);
        CollectionHelper.getPropertyValues(orgTreeBos, proName, result);
        result.add(pid);
        return result;
    }
}
