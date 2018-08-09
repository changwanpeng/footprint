package com.footprint.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.footprint.core.BhSession;
import com.footprint.core.Constant;
import com.footprint.core.ISessionRepository;

@Controller
public class ConfigController {
	@Autowired
	private ISessionRepository sessionRepository;

	@RequestMapping(value = "/config", method = RequestMethod.GET)
	public ModelAndView show(Map<String, Object> model) {
		BhSession bhSession = sessionRepository.findById(Constant.ID).get();
		model.put("bhSession", bhSession);
		return new ModelAndView("config");
	}

	@RequestMapping(value = "/config", method = RequestMethod.POST)
	public ModelAndView save(Map<String, Object> model, @ModelAttribute("formData") BhSession formData) {
		BhSession bhSession = sessionRepository.findById(Constant.ID).get();
		bhSession.setUsername(formData.getUsername());
		bhSession.setPassword(formData.getPassword());
		bhSession.setClientId(formData.getClientId());
		bhSession.setClientSecret(formData.getClientSecret());
		sessionRepository.save(bhSession);
		model.put("bhSession", bhSession);
		return new ModelAndView("config");
	}
}
