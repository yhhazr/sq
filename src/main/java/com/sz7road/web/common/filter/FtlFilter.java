package com.sz7road.web.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.mapper.ActionMapping;
import org.apache.struts2.dispatcher.ng.ExecuteOperations;
import org.apache.struts2.dispatcher.ng.InitOperations;
import org.apache.struts2.dispatcher.ng.filter.FilterHostConfig;

public class FtlFilter implements Filter {

	protected ExecuteOperations execute;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		InitOperations init = new InitOperations();
		try {
			FilterHostConfig config = new FilterHostConfig(filterConfig);
			init.initLogging(config);
			Dispatcher dispatcher = init.initDispatcher(config);
			init.initStaticContentLoader(config, dispatcher);
			execute = new ExecuteOperations(filterConfig.getServletContext(), dispatcher);
		} catch (Exception e) {
			 init.cleanup();
		}

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
		try {
			ActionMapping mapping = new ActionMapping("executeFtlAction","/","execute",null);
			execute.executeAction(request, response, mapping);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
