
package com.Master5.main.web.record.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Master5.main.web.record.service.RecordService;

@Controller
@RequestMapping("record")
public class RecordController {

	@Autowired
	RecordService recordService;

	@RequestMapping("list")
	public String list(Model model) {

		model.addAttribute("list", recordService.findAll());
		return "record/list";
	}

}
