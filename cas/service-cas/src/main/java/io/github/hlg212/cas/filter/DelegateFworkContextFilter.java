
package io.github.hlg212.cas.filter;

import io.github.hlg212.fcf.api.UserApi;
import io.github.hlg212.fcf.context.FworkContext;
import io.github.hlg212.fcf.model.basic.IUser;
import io.github.hlg212.fcf.util.FworkHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import java.io.IOException;

/** 
 *
 *  扩展框架获取用户的方式，兼容在spring security框架中，获取的方式
 * date: 2017年8月23日 上午9:10:42
 * 
 * @author huangligui 
 */

@Slf4j
public class DelegateFworkContextFilter implements Filter {

	@Autowired
	private UserApi userApi;


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		// TODO Auto-generated method stub
		FworkHelper.setContext(new DelegateHttpFworkContext(FworkHelper.getContext()));
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	private class DelegateHttpFworkContext implements FworkContext
	{
		private FworkContext context = null;

		public DelegateHttpFworkContext(FworkContext context) {
			this.context = context;
		}


		@Override
		public IUser getUser() {
			// TODO Auto-generated method stub
			IUser user = null;
			if(  context != null )
			{
				user = context.getUser();
			}
			if( user == null )
			{
				SecurityContext securityContext = SecurityContextHolder.getContext();

				if( securityContext != null && securityContext.getAuthentication() != null )
				{
					user = userApi.getUserByAccount(securityContext.getAuthentication().getName());
				}
			}

			return user;
		}

		@Override
		public String getUid() {
			// TODO Auto-generated method stub
			IUser s = getUser();
			if( s != null )
			{
				return s.getId();
			}
			return null;
		}

		@Override
		public Object get(String key) {
			// TODO Auto-generated method stub
			return context.get(key);
		}
		@Override
		public void put(String key, Object o) {
			// TODO Auto-generated method stub
			context.put(key, o);
		}
		

	}

}
