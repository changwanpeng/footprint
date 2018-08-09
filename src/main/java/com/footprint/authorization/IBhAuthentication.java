package com.footprint.authorization;

import com.footprint.core.BhSession;

public interface IBhAuthentication {
	BhSessionTokenRO requestBhRestToken(BhSession bhSession) throws Exception;
}
