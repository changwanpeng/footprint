package com.footprint.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.footprint.client.ClientContactRO;
import com.footprint.client.IClientContactRestService;

@Controller
public class ClientContactController {
	@Autowired
	private IClientContactRestService clientService;
	
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard(Map<String, Object> model) throws Exception {
		ClientContactRO client = clientService.getClientContact();
		model.put("client", client);
		return new ModelAndView("dashboard");
	}
}
