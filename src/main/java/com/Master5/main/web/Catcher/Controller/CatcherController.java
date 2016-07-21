package com.Master5.main.web.Catcher.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Master5.main.utils.Tools;
import com.Master5.main.web.Catcher.entry.Catcher;
import com.Master5.main.web.Catcher.entry.UrlsInfo;
import com.Master5.main.web.Catcher.service.CatcherService;

@Controller
@RequestMapping(value = "catcher")
public class CatcherController {
	@Autowired
	CatcherService catcherService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// true:允许输入空值，false:不能为空值
	}

	@RequestMapping(value = { "listUrlsInfo" })
	public String listUrlsInfo(Model model) {
		model.addAttribute("list", catcherService.queryUrlsInfo());
		return "catcher/listUrlsInfo";
	}

	@RequestMapping(value = "addUrlsInfo", method = RequestMethod.GET)
	public String addUrlsInfo() {
		return "catcher/addUrlsInfo";
	}

	@RequestMapping(value = "addUrlsInfo", method = RequestMethod.POST)
	public String addUrlsInfo(UrlsInfo urlsInfo) {
		catcherService.saveUrlsInfo(urlsInfo);
		return "redirect:listUrlsInfo";
	}

	@RequestMapping(value = "updateUrlsInfo/{id}", method = RequestMethod.GET)
	public String updateUrlsInfo(@PathVariable int id, Model model) {
		model.addAttribute("urlsInfo", catcherService.queryOne(id));
		return "catcher/updateUrlsInfo";
	}

	@RequestMapping(value = "updateUrlsInfo", method = RequestMethod.POST)
	public String updateUrlsInfo(UrlsInfo urlsInfo) {
		catcherService.saveUrlsInfo(urlsInfo);
		return "catcher/listUrlsInfo";
	}

	@RequestMapping(value = "work")
	public String work(String[] urls, Date startDate, Date endDate) {
		catcherService.catcherWork(urls, startDate);
		return "catcher/listUrlsInfo";
	}

	@RequestMapping(value = "testCatcher")
	@ResponseBody
	public Catcher testCatcher(int id) throws ParseException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return catcherService.testCatcher(id,simpleDateFormat.parse("2016-07-18"));
	}

	@RequestMapping(value = { "listCatcher" })
	public String listCatcher(Model model) {
		model.addAttribute("list", catcherService.queryCatcher());
		return "catcher/listCatcher";
	}

}
