
package com.Master5.main.utils.constant;

public interface SysKey {

	/**
	 * 负状态的角色有且只能有一个 权限的数量不做限制
	 */
	public static final int STATE_DEFAULT_DARK = -20;

	public static final int STATE_DEFAULT_USER = -10;

	public static final int STATE_DEFAULT_ADMIN = -1;

	public static final String ROLE_DEFAULT_DARK = "黑名单";

	public static final String ROLE_DEFAULT_USER = "普通用户";

	public static final String ROLE_DEFAULT_ADMIN = "超级管理员";

	public static final int ROLE_DEFAULT_DARK_ID = 2;

	public static final int ROLE_DEFAULT_USER_ID = 1;

	public static final int ROLE_DEFAULT_ADMIN_ID = 0;

	public static final String ADMIN_DEFAULT_NAME = "admin";

	public static final String ADMIN_DEFAULT_PASS = "admin";

	public static final String ADMIN_DEFAULT_EMAIL = "admin@yuanayuan.com";

	public static final int STATE_NROMAL = 0;

	public static final int STATE_LOCK = 1;

	public static final int STATE_UNACTIVE = 2;
}
