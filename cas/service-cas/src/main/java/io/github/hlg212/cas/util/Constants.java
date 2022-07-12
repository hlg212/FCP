package io.github.hlg212.cas.util;

/**
 * @description: 常量声明
 * @author: huangligui
 * @create: 2018-11-16 15:04
 **/
public class Constants {

	public static final String appName = "cas";

	public static final String AUTHENTICATION_LOGINPASSWORDERRORINFO = "AUTHENTICATION_LOGINPASSWORDERRORINFO";

	public static class ErrorMessage{

		public static final String loginPasswordErrorMessage = "cas.loginPasswordErrorMessage";
		public static final String userLoginLock = "cas.userLoginLock";
	}

	public enum GrantType{
		AUTHORIZATION_CODE("authorization_code"),PASSWORD("password"),CLIENT_CREDENTIALS("client_credentials"),IMPLICIT("implicit"),ACCOUNT("account"),REFRESH_TOKEN("refresh_token");
		private String value;
		GrantType(String value)
		{
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public static final String[] DefGrantTypes = new String[]{GrantType.AUTHORIZATION_CODE.value,GrantType.PASSWORD.value,GrantType.CLIENT_CREDENTIALS.value,GrantType.IMPLICIT.value,GrantType.ACCOUNT.value,GrantType.REFRESH_TOKEN.value};
    
}
