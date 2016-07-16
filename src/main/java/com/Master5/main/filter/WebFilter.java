package com.Master5.main.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.CharacterEncodingFilter;

public class WebFilter extends CharacterEncodingFilter {

	// String[] notFilter = new String[] { "/login/", "/login/regist",
	// "/login/login",
	// "/login/checkRegAjax" };

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// request.setCharacterEncoding("utf-8");
		// response.setCharacterEncoding("utf-8");
		//
		// String uri = request.getServletPath();
		// Object obj = request.getSession().getAttribute(Key.LOGINED);

		// boolean flag = true;
		//
		// for (String str : notFilter) {
		// if (uri.equals(str)) {
		// if(obj!=null) {
		// redirectLogin(request, response, obj);
		// return;
		// }
		// flag = false;
		// break;
		// }
		// }
		//
		// if (flag && null == obj && (uri.indexOf("resources") == -1)) {
		// redirectLogin(request, response, obj);
		// return;
		// }

		// System.out.println (uri) ;
		//
		// if (!uri.startsWith("/login/") && null == obj &&
		// (uri.indexOf("resources") == -1)) {
		//
		// response.sendRedirect ( request.getContextPath ( )+"/login/list/" );
		// //redirectLogin(request, response, obj);
		// return;
		//
		// }else if(uri.startsWith("/login/") && null != obj){
		//
		// response.sendRedirect ( request.getContextPath ( )+"/menu/list/" );
		// }

		filterChain.doFilter(request, response);
	}

	// private void redirectLogin(HttpServletRequest request,
	// HttpServletResponse response, Object obj)
	// throws IOException {
	//
	// PrintWriter out = response.getWriter();
	// StringBuilder builder = new StringBuilder();
	// String url = request.getRequestURL().toString();
	// if (null == obj)
	// url = url.substring(0, url.indexOf(request.getContextPath())
	// + request.getContextPath().length())
	// + "/login/";
	// else
	// url = url.substring(0, url.indexOf(request.getContextPath())
	// + request.getContextPath().length())
	// + "/user/menu";
	// builder.append("<script type=\"text/javascript\">");
	// builder.append("window.top.location.href='");
	// builder.append(url);
	// builder.append("';");
	// builder.append("</script>");
	//
	// out.print(builder.toString());
	// }
}
