package com.Master5.main.web.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Master5.main.annotation.CheckPermission;
import com.Master5.main.utils.constant.SysKey;
import com.Master5.main.utils.encrypt.encryptTools;
import com.Master5.main.utils.qrcode.Qrcode;
import com.Master5.main.web.user.dao.UserDao;

@Controller
@RequestMapping(value = "/play/qrcode")
public class QrcodeController {

	private static final Logger logger = LoggerFactory.getLogger(QrcodeController.class);

	@Autowired
	UserDao userDao;

	@RequiresPermissions(value = "play:qrcode")
	@CheckPermission(name = "二维码菜单", method = "play:qrcode", state = SysKey.STATE_DEFAULT_USER)
	@RequestMapping(value = "menu", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "/play/qrcode";
	}

	@RequiresPermissions(value = "play:qrcodeRun")
	@CheckPermission(name = "二维码执行", method = "play:qrcodeRun", state = SysKey.STATE_DEFAULT_USER)
	@RequestMapping(value = "creat", method = RequestMethod.GET)
	@ResponseBody
	public List<String> qrcode(HttpSession session, HttpServletResponse response, String code, Model model) {

		List<String> list = new ArrayList<String>();

		response.setHeader("Pragma", "no-cache");// 禁止图像缓存。
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		ServletOutputStream sos = null;

		try {

			code = encryptTools.getUTF8Code(code);

			sos = response.getOutputStream();
			ImageIO.write(Qrcode.enCode(code), "png", sos);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				sos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return list;
	}

}
