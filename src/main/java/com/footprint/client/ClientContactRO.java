package com.footprint.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.footprint.job.JobOrderRO;
import com.footprint.job.JobOrdersRO;

public class ClientContactRO {
	public List<ClientContactDataRO> data = new ArrayList<ClientContactDataRO>();
	public List<JobOrderRO> jobOrderList;

	public int getOpenedJobOrderSize() {
		return getOpenedJobOrders().size();
	}

	public int getClosedJobOrderSize() {
		return getClosedJobOrders().size();
	}

	public List<JobOrderRO> getOpenedJobOrders() {
		return getJobOrders(true);
	}

	public List<JobOrderRO> getClosedJobOrders() {
		return getJobOrders(false);
	}

	private List<JobOrderRO> getJobOrders(boolean isOpened) {
		return jobOrderList.stream().filter(jobOrder -> isOpened == jobOrder.isOpen).collect(Collectors.toList());
	}

	public Map<String, Long> getJobOrdersByStatus() {
		return jobOrderList.stream().collect(Collectors.groupingBy(jobOrder -> jobOrder.status, Collectors.counting()));
	}

	public Map<Integer, Long> getJobOrdersByType() {
		return jobOrderList.stream().collect(Collectors.groupingBy(jobOrder -> jobOrder.type, Collectors.counting()));
	}

	public void populateJobOrdersOverview() {
		if (data == null || data.isEmpty()) {
			return;
		}
		JobOrdersRO jobOrders = data.get(0).jobOrders;
		if (jobOrders == null || jobOrders.data.isEmpty()) {
			return;
		}
		jobOrderList = jobOrders.data != null ? jobOrders.data : Collections.emptyList();
	}
}
