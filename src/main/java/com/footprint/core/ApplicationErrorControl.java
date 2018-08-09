package com.footprint.core;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@Controller
public class ApplicationErrorControl implements ErrorController {

	@Autowired
	private ErrorAttributes _errorAttributes;

	@RequestMapping("/error")
	@ResponseBody
	ErrorJson error(HttpServletRequest request, HttpServletResponse response) {
		return new ErrorJson(response.getStatus(), getErrorAttributes(request, true));
	}

	private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
		WebRequest requestAttributes = new ServletWebRequest(request);
		return _errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
	}

	@Override
	public String getErrorPath() {
		return "/error";
	}

	class ErrorJson {
		public Integer _status;
		public String _error;
		public String _message;
		public String _timeStamp;
		public String _trace;

		public ErrorJson(int status, Map<String, Object> errorAttributes) {
			this._status = status;
			this._error = (String) errorAttributes.get("error");
			this._message = (String) errorAttributes.get("message");
			this._timeStamp = errorAttributes.get("timestamp").toString();
			this._trace = (String) errorAttributes.get("trace");
		}
	}
}