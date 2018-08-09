package com.footprint.candidate;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.footprint.authorization.BhSessionTokenRO;
import com.footprint.core.IBhSessionManager;

@Service
public class CandidateRestService implements ICandidateRestService {
	@Autowired
	private IBhSessionManager sessionManager;
	private static final String TEMPLATE_URL = "{0}search/Candidate?query=id:{1}&fields=firstName,lastName&BhRestToken={2}";

	@Override
	public CandidateRO getCandidate() throws Exception {
		BhSessionTokenRO token = sessionManager.findOrCreateBhSession();
		String restUrl = MessageFormat.format(TEMPLATE_URL, token.restUrl, "233808", token.BhRestToken);
		RestTemplate restTemplate = new RestTemplate();
		CandidateRO candidate = restTemplate.getForObject(restUrl, CandidateRO.class);
		return candidate;
	}
}
