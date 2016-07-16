
package com.Master5.main.interceptor;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.Master5.main.annotation.CheckPermission;
import com.Master5.main.utils.IPTools;
import com.Master5.main.utils.constant.Key;
import com.Master5.main.web.record.entry.Record;
import com.Master5.main.web.record.service.RecordService;

public class Interceptor implements HandlerInterceptor {

	@Autowired
	RecordService recordService;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse reponse, Object handler, Exception e)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model)
			throws Exception {

		if (null != model) {
			HttpSession session = request.getSession();
			request.setAttribute(Key.msg, session.getAttribute(Key.msg));
			session.removeAttribute(Key.msg);
		}

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		String permission = "";

		if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {

			Record record = new Record();
			CheckPermission annotation = ((HandlerMethod) handler).getMethodAnnotation(CheckPermission.class);
			if (null == annotation)
				return true;
			permission = annotation.method();
			record.setUserName(request.getRemoteUser());
			record.setMethod(annotation.name());
			record.setIp(IPTools.getClientIp(request));
			record.setRecordTime(Calendar.getInstance().getTime());
			record.setData(String.valueOf(request.getParameterMap()));

			recordService.save(record);

			// System.err.println ( ( (HandlerMethod) handler ).getMethod (
			// ).getName ( ) ) ;
			// System.err.println ( request.getParameterMap ( ) ) ;
			// System.err.println ( request.getQueryString ( ) ) ;
			// System.out.println ( request.getProtocol ( ) ) ;
			// System.out.println ( request.getRemoteUser ( ) ) ;
			Subject currentUser = SecurityUtils.getSubject();
			// 没有获得注解 及不需要权限-- 则直接运行
			if (currentUser.isPermitted(permission)) {

				return true;
			} else {
				// 抛出无权限异常
				return false;
			}
		}

		// return true;
		// String uri = request.getServletPath ( ) ;
		// User user = (User) request.getSession ( ).getAttribute ( Key.LOGINED
		// ) ;
		//
		// if ( ! uri.startsWith ( "/login/" ) && null == user && ( uri.indexOf
		// ( "resources" ) == - 1 ) ) {
		// response.sendRedirect ( request.getContextPath ( ) + "/login/list/" )
		// ;
		// return false ;
		// } else if ( uri.startsWith ( "/login/" ) && null != user ) {
		// response.sendRedirect ( request.getContextPath ( ) + "/menu/list/" )
		// ;
		// return false ;
		// }
		//
		// if ( handler.getClass ( ).isAssignableFrom ( HandlerMethod.class ) )
		// {
		// CheckPermission perAnnotation = ( (HandlerMethod) handler
		// ).getMethodAnnotation ( CheckPermission.class ) ;
		// //没有声明需要权限,或者声明不验证权限
		// if ( null == perAnnotation || perAnnotation.method ( ).equals ( "" )
		// ) {
		// return true ;
		// } else {
		// if ( null != user ) {
		// Set<Permission> pers = user.getPermissions ( ) ;
		// for ( Permission per : pers ) {
		// if ( per.getMethod ( ).equals ( perAnnotation.method ( ) ) ) {
		// return true ;
		// }
		// }
		// List<String> msgList = new ArrayList<String> ( ) ;
		// msgList.add ( Key.NO_PERMISSION ) ;
		// request.getSession ( ).setAttribute ( Key.msg , msgList ) ;
		// response.sendRedirect ( request.getContextPath ( ) + "/menu/list" ) ;
		// return false ;
		// }
		// }
		// }

		return true;
	}

}
