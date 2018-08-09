package com.footprint.core;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.footprint.authorization.BhSessionTokenRO;
import com.footprint.authorization.IBhAuthentication;

@Service
public class BhSessionManager implements IBhSessionManager {
	@Autowired
	private ISessionRepository sessionRepository;
	@Autowired
	private IBhAuthentication bhAuthentication;

	private static Integer MILLI = 1000;
	private static Integer DEDUCTION = 120; // expires the token 2 mins earlier

	public BhSessionManager() {
	}

	@Override
	public BhSessionTokenRO findOrCreateBhSession() throws Exception {
		BhSessionTokenRO bhSessionTokenRO;
		BhSession bhSession = sessionRepository.findById(Constant.ID).get();
		try {
			if (isSessionAbsentOrExpired(bhSession)) {
				// Create
				bhSessionTokenRO = bhAuthentication.requestBhRestToken(bhSession);
				saveBhSession(bhSession, bhSessionTokenRO);

			} else {
				// Found
				bhSessionTokenRO = new BhSessionTokenRO();
				bhSessionTokenRO.BhRestToken = bhSession.getBhRestToken();
				bhSessionTokenRO.restUrl = bhSession.getRestUrl();
			}
		} catch (Throwable t) {
			resetSession(bhSession); // refresh_token is expired
			bhSessionTokenRO = findOrCreateBhSession(); // infinite loop?
		}
		return bhSessionTokenRO;
	}

	private void resetSession(BhSession bhSession) {
		// bhSession.setId(Constant.ID);
		bhSession.setAccessToken(null);
		bhSession.setBhRestToken(null);
		bhSession.setRefreshToken(null);
		bhSession.setExpiresIn(null);
		bhSession.setRestUrl(null);
		bhSession.setCreatedTime(null);
		sessionRepository.save(bhSession);
	}

	private boolean isSessionAbsentOrExpired(BhSession bhSession) {
		Integer expiresIn = bhSession.getExpiresIn();
		Date createdTime = bhSession.getCreatedTime();
		// Absent
		if (bhSession.getBhRestToken() == null || bhSession.getRefreshToken() == null || bhSession.getRestUrl() == null
				|| expiresIn == null || createdTime == null)
			return true;
		// EXPRIED?
		Date now = Calendar.getInstance().getTime();
		long lapse = now.getTime() - createdTime.getTime();
		return lapse / MILLI >= expiresIn - DEDUCTION;
	}

	private void saveBhSession(BhSession bhSession, BhSessionTokenRO bhSessionTokenRO) {
		// bhSession.setId(ID);
		bhSession.setBhRestToken(bhSessionTokenRO.BhRestToken);
		bhSession.setAccessToken(bhSessionTokenRO.accessToken);
		bhSession.setRefreshToken(bhSessionTokenRO.refreshToken);
		bhSession.setExpiresIn(Integer.parseInt(bhSessionTokenRO.expiresIn));
		bhSession.setRestUrl(bhSessionTokenRO.restUrl);
		bhSession.setCreatedTime(Calendar.getInstance().getTime());
		sessionRepository.save(bhSession);
	}
}
