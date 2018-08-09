package com.footprint.core;

import com.footprint.authorization.BhSessionTokenRO;

public interface IBhSessionManager {
	BhSessionTokenRO findOrCreateBhSession() throws Exception;
}
