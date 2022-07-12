package io.github.hlg212.basic.model.bo;

import io.github.hlg212.basic.model.po.Client;
import io.github.hlg212.fcf.model.basic.IClient;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/** 
 * 客户端Bo
 *
 * @author huanglg
 * @date 2022-03-28
 */
@Data
public class ClientBo extends Client implements IClient {
	
	private static final long serialVersionUID = 1L;
	private String scopes;
	private String autoApproveScopes;
	private String[] grantTypes;
	@Override
	public String getSecret() {
		return getPasswd();
	}

	@Override
	public String[] getGrantTypes() {
		String gt = getGrantType();
		if(StringUtils.isNotEmpty(gt))
		{
			return gt.split(",");
		}
		return new String[0];
	}

	public void setGrantTypes(String[] grantTypes) {
		this.grantTypes = grantTypes;
		if( grantTypes != null )
		{
			setGrantType(String.join(",",grantTypes));
		}
	}

	@Override
	public String getRegisteredRedirectUri() {
		return getAuthCallbacks();
	}
}
