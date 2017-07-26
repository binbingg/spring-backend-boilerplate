package in.clouthink.daas.sbb.dashboard.rest.support.impl;

import in.clouthink.daas.sbb.dashboard.rest.dto.SmsHistorySummary;
import in.clouthink.daas.sbb.dashboard.rest.support.SmsHistorySupport;
import in.clouthink.daas.sbb.event.sms.history.domain.model.SmsHistory;
import in.clouthink.daas.sbb.event.sms.history.domain.request.SmsHistoryQueryRequest;
import in.clouthink.daas.sbb.event.sms.history.service.SmsHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SmsHistorySupportImpl implements SmsHistorySupport {

	@Autowired
	private SmsHistoryService smsHistoryService;

	@Override
	public Page<SmsHistorySummary> findPage(SmsHistoryQueryRequest request) {
		Page<SmsHistory> smsHistories = smsHistoryService.findPage(request);
		return new PageImpl<>(smsHistories.getContent()
										  .stream()
										  .map(SmsHistorySummary::from)
										  .collect(Collectors.toList()),
							  new PageRequest(request.getStart(), request.getLimit()),
							  smsHistories.getTotalElements());
	}
}