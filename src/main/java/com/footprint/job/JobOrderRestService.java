package com.footprint.job;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.footprint.authorization.BhSessionTokenRO;
import com.footprint.core.IBhSessionManager;

@Service
public class JobOrderRestService implements IJobOrderRestService {
	@Autowired
	private IBhSessionManager sessionManager;
	private static final String TEMPLATE_URL = "{0}entity/JobOrder/{1}?fields=id,title,isOpen,status,type&BhRestToken={2}";

	@Override
	public JobOrderRO getJobOrder(Integer id) throws Exception {
		BhSessionTokenRO token = sessionManager.findOrCreateBhSession();
		String restUrl = MessageFormat.format(TEMPLATE_URL, token.restUrl, Integer.toString(id), token.BhRestToken);
		RestTemplate restTemplate = new RestTemplate();
		JobOrderEntityRO jobOrderEntity = restTemplate.getForObject(restUrl, JobOrderEntityRO.class);
		return jobOrderEntity != null ? jobOrderEntity.data : null;
	}
}
