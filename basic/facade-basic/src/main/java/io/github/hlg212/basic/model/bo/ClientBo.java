package io.github.hlg212.basic.model.bo;

import io.github.hlg212.basic.model.po.Client;
import io.github.hlg212.fcf.model.basic.IClient;
import lombok.Data;

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

	@Override
	public String getSecret() {
		return getPasswd();
	}

	@Override
	public String getRegisteredRedirectUri() {
		return getAuthCallbacks();
	}
}
