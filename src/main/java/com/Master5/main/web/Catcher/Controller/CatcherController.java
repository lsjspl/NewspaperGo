package com.Master5.main.web.Catcher.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.Master5.main.web.Catcher.entry.Catcher;
import com.Master5.main.web.Catcher.entry.CatcherTask;
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
		model.addAttribute("urlsInfo", catcherService.queryUrlsInfoOne(id));
		return "catcher/updateUrlsInfo";
	}

	@RequestMapping(value = "updateUrlsInfo", method = RequestMethod.POST)
	public String updateUrlsInfo(UrlsInfo urlsInfo) {
		catcherService.saveUrlsInfo(urlsInfo);
		return "redirect:listUrlsInfo";
	}

	@RequestMapping(value = "testCatcher")
	@ResponseBody
	public Catcher testCatcher(UrlsInfo urlsInfo) throws ParseException {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return catcherService.testCatcher(urlsInfo, simpleDateFormat.parse("2016-07-18"));
	}

	@RequestMapping(value = { "listCatcher" })
	public String listCatcher(Model model) {
		model.addAttribute("list", catcherService.queryCatcher());
		return "catcher/listCatcher";
	}

	@RequestMapping(value = "total/{taskId}")
	public String total(Model model,@PathVariable int taskId) {
		model.addAttribute("list", catcherService.total(taskId));
		model.addAttribute("taksId", taskId);
		return "catcher/total";
	}
	

	@RequestMapping(value = "catcherDetail")
	@ResponseBody
	public Catcher catcherDetail(int id) throws ParseException {

		return catcherService.queryCatcherOne(id);
	}


	@RequestMapping(value = "updateCatcherState/{taskId}/{id}/{state}/{point}")
	public String deleteCatcher(@PathVariable int id,@PathVariable int taskId,@PathVariable int state,@PathVariable int point) {
		catcherService.updateCatcherState(id,state);
		return "redirect:/catcher/total/"+taskId+"#"+point;
	}
	
	@RequestMapping(value = "taskLog/{type}/{taskId}")
	public String taskLog(Model model, @PathVariable int type,@PathVariable int taskId) {
		model.addAttribute("list", catcherService.queryTaskLog(type, taskId));
		return "/catcher/taskLog";
	}

	/**
	 * =========================================
	 * 任务相关
	 * =============================================
	 */
	
	@RequestMapping(value = "addTask")
	public String addTask(CatcherTask task) {
		catcherService.saveCatcherTask(task);
		return "redirect:listTask";
	}
	
	@RequestMapping(value = "listTask")
	public String listTask(Model model) {
		model.addAttribute("list", catcherService.queryTask());
		model.addAttribute("urlsInfoList", catcherService.queryUrlsInfo());
		return "/catcher/listTask";
	}
	
	
	@RequestMapping(value = "deleteTask/{taskId}")
	public String deleteTask(@PathVariable int taskId) {
		catcherService.deleteTask(taskId);
		return "redirect:/catcher/listTask";
	}
	
	@RequestMapping(value = "redoTask/{taskId}")
	public String redoTask(@PathVariable int taskId) {
		catcherService.redoTask(taskId);
		return "redirect:/catcher/listTask";
	}
	
}
