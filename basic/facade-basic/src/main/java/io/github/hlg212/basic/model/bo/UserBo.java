package io.github.hlg212.basic.model.bo;

import io.github.hlg212.basic.model.po.User;
import io.github.hlg212.fcf.model.basic.IOrg;
import io.github.hlg212.fcf.model.basic.IUser;
import lombok.Data;

/** 
 * 用户Bo
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class UserBo extends User implements IUser {
	
	private static final long serialVersionUID = 1L;

	private IOrg org;

	@Override
	public String getPassword() {
		return getPasswd();
	}

	@Override
	public IOrg getOrg() {
		return org;
	}

	public void setOrg(OrgBo org) {
		this.org = org;
	}
}
