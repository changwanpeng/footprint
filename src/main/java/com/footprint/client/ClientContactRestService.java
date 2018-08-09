package com.footprint.client;

import java.text.MessageFormat;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.footprint.authorization.BhSessionTokenRO;
import com.footprint.core.IBhSessionManager;
import com.footprint.job.IJobOrderRestService;
import com.footprint.job.JobOrderRO;

@Service
public class ClientContactRestService implements IClientContactRestService {
	@Autowired
	private IBhSessionManager sessionManager;
	@Autowired
	private IJobOrderRestService jobService;

	private static final String TEMPLATE_URL = "{0}search/ClientContact?query=id:{1}&fields=id,jobOrders,username,password&BhRestToken={2}";

	@Override
	public ClientContactRO getClientContact() throws Exception {
		BhSessionTokenRO token = sessionManager.findOrCreateBhSession();
		String restUrl = MessageFormat.format(TEMPLATE_URL, token.restUrl, "235077", token.BhRestToken);
		RestTemplate restTemplate = new RestTemplate();
		ClientContactRO client = restTemplate.getForObject(restUrl, ClientContactRO.class);
		populate(client);
		return client;
	}

	private void populate(ClientContactRO client) throws Exception {
		populateJobOrder(client);
	}

	private void populateJobOrder(ClientContactRO client) throws Exception {
		client.populateJobOrdersOverview();
		ListIterator<JobOrderRO> iterator = client.jobOrderList.listIterator();
		while (iterator.hasNext()) {
			JobOrderRO jobOrder = iterator.next();
			iterator.set(jobService.getJobOrder(jobOrder.id));
		}
	}
}
