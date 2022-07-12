package io.github.hlg212.cas.api;

import io.github.hlg212.cas.service.OnlineUserService;
import io.github.hlg212.cas.util.Constants;
import io.github.hlg212.fcf.api.OnlineUserApi;
import io.github.hlg212.fcf.model.PageInfo;
import io.github.hlg212.fcf.model.mc.IOnlineUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController(Constants.appName + ".OnlineUserApiController")
@Api(value="在线用户管理控制器",tags={"在线用户管理"})
public class OnlineUserApiController implements OnlineUserApi {

    @Autowired
    private OnlineUserService queryService;

    @Override
    @ApiOperation("获得所有的在线用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name="username", value="用户名(账号)", paramType="query"),
            @ApiImplicitParam(name="pageNum", value="当前页数", required=true, dataType="int",example = "1",paramType="query"),
            @ApiImplicitParam(name="pageSize", value="每页显示数量", required=true, dataType="int",example = "10",paramType="query")
    })
    public PageInfo findPage(String username, int pageNum, int pageSize) {
        return queryService.findPage(username,pageNum,pageSize);
    }

    @ApiOperation("移除在线用户，清理认证")
    @Override
    public void kill(String id) {
        queryService.kill(id);
    }

    @ResponseBody
    @ApiOperation("获取在线用户信息")
    @Override
    public IOnlineUser getById(String id) {
        return queryService.getById(id);
    }

    @Override
    public boolean isInvalid(String id) {
        return queryService.isInvalid(id);
    }

}
