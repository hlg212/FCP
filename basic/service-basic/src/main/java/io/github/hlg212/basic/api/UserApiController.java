package io.github.hlg212.basic.api;

import io.github.hlg212.basic.model.bo.UserBo;
import io.github.hlg212.basic.model.qco.UserQco;
import io.github.hlg212.basic.service.OrgService;
import io.github.hlg212.fcf.api.UserApi;
import io.github.hlg212.fcf.web.controller.QueryController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/** 
 * 用户Controller
 *
 * @author wuwei
 * @date 2018年11月05日
 */
@RestController
@Api(value="用户api控制器",tags={"用户api控制器"})
@CacheConfig(cacheNames = io.github.hlg212.fcf.cache.Constants.User)
public class UserApiController implements QueryController<UserBo, UserQco> , UserApi<UserBo> {


	@Autowired
	private OrgService orgService;


	@Override
    @RequestMapping(value="/getUserByAccount",method=RequestMethod.GET)
	@Cacheable(key = io.github.hlg212.fcf.cache.Constants.UserKey.getUserByAccount_spel)
    public UserBo getUserByAccount(String account) {
		UserQco qco = new UserQco();
		qco.setAccount(account);
		//q.setSybz(io.github.hlg212.fcf.model.Constants.BOOLEAN_Y);

		UserBo userBo = this.get(qco);
		if( userBo != null ) {
			return getById(userBo.getId());
		}
		return null;
    }

	/**
	  * 修改当前登录用户密码
	 * @param yhid
	 * @param oldPassword
	 * @param newPassword
	 * @see io.github.hlg212.fcf.api.UserApi#changePassword(java.lang.String, java.lang.String, java.lang.String) 
	 * @author ccfhn-xiequn 
	 */
	@Override
	public void changePassword(String yhid, String oldPassword, String newPassword) {
		//this.getService(YhService.class).checkChangePassword(yhid, oldPassword, newPassword);
	}


	@ResponseBody
	@ApiOperation("根据主键获取实体信息")
	@ApiImplicitParam(name="id", value="主键ID", required=true, paramType="query")
	@Override
	@Cacheable(key = "#p0")
	public UserBo getById(String id) {
		UserBo userBo = QueryController.super.getById(id);

		if( userBo != null ) {
			if (StringUtils.isNotBlank(userBo.getOrgId()) && userBo.getOrg() == null) {
				// 强制加载机构
				userBo.setOrg(orgService.getById(userBo.getOrgId()));
			}
		}
		return userBo;
	}
}
