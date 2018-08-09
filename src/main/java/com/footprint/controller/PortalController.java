package com.footprint.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.footprint.candidate.ICandidateRestService;

@Controller
public class PortalController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Value("${application.message}")
	private String message;

	@Autowired
	private ICandidateRestService candidateRestClient;

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public ModelAndView welcome(Map<String, Object> model) {
		logger.info("This is a logging sample");
		model.put("message", message);
		return new ModelAndView("welcome");
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView test(Map<String, Object> model) throws Exception {
		candidateRestClient.getCandidate();
		return new ModelAndView("welcome");
	}
}
