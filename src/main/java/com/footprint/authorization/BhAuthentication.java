package com.footprint.authorization;

import java.net.URI;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.footprint.core.BhSession;

@Service
public class BhAuthentication implements IBhAuthentication {
	// Bullhorn Login Form Data
	private static final String ACTION = "Login";

	// Base URL components
	private static final String AUTH_BASE_URL = "https://auth.bullhornstaffing.com/oauth/";
	private static final String REDIRECT_URI = "&redirect_uri=http://bullhorn.com/";

	// Authorization Code
	private static final String AUTH_URL = AUTH_BASE_URL + "authorize?response_type=code&client_id={0}" + REDIRECT_URI;

	// Access Tokens
	private static final String ACCESS_URL = AUTH_BASE_URL
			+ "token?grant_type=authorization_code&client_id={0}&client_secret={1}&code={2}" + REDIRECT_URI;
	private static final String REFRESH_URL = AUTH_BASE_URL
			+ "token?grant_type=refresh_token&client_id={0}&client_secret={1}&refresh_token={2}";

	// Login Bullhorn REST API
	private static final String LOGIN_URL = "https://rest.bullhornstaffing.com/rest-services/login?version=*&access_token={0}";

	private String requestAuthCode(BhSession bhSession) throws Exception {
		List<NameValuePair> formData = new ArrayList<>();
		formData.add(new BasicNameValuePair("username", bhSession.getUsername()));
		formData.add(new BasicNameValuePair("password", bhSession.getPassword()));
		formData.add(new BasicNameValuePair("action", ACTION));
		String authorizationUrl = MessageFormat.format(AUTH_URL, bhSession.getClientId());
		HttpResponse httpResponse = post(authorizationUrl, formData);
		String redirectURL = httpResponse.getFirstHeader("Location").getValue();
		List<NameValuePair> params = URLEncodedUtils.parse(new URI(redirectURL), Charset.forName("UTF-8"));
		Optional<NameValuePair> target = params.stream().filter(param -> "code".equals(param.getName())).findAny();
		return target.get().getValue();
	}

	private BhAccessTokenRO requestAccess(BhSession bhSession) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String accessTokenUrl = MessageFormat.format(ACCESS_URL, bhSession.getClientId(), bhSession.getClientSecret(),
				requestAuthCode(bhSession));
		BhAccessTokenRO access = restTemplate.postForObject(accessTokenUrl, null, BhAccessTokenRO.class);
		return access;
	}

	synchronized public BhSessionTokenRO requestBhRestToken(BhSession bhSession) throws Exception {
		BhAccessTokenRO accessTokenRO = bhSession.getRefreshToken() == null ? requestAccess(bhSession)
				: refreshAccess(bhSession);
		String loginTokenUrl = MessageFormat.format(LOGIN_URL, accessTokenRO.access_token);
		RestTemplate restTemplate = new RestTemplate();
		BhSessionTokenRO sessionTokenRO = restTemplate.postForObject(loginTokenUrl, null, BhSessionTokenRO.class);
		sessionTokenRO.accessToken = accessTokenRO.access_token;
		sessionTokenRO.refreshToken = accessTokenRO.refresh_token;
		sessionTokenRO.expiresIn = accessTokenRO.expires_in;
		return sessionTokenRO;
	}

	private BhAccessTokenRO refreshAccess(BhSession bhSession) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		String refreshTokenUrl = MessageFormat.format(REFRESH_URL, bhSession.getClientId(), bhSession.getClientSecret(),
				bhSession.getRefreshToken());
		BhAccessTokenRO access = restTemplate.postForObject(refreshTokenUrl, null, BhAccessTokenRO.class);
		return access;
	}

	private static HttpResponse post(String url, List<NameValuePair> params) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		CloseableHttpResponse httpResponse;

		try {
			if (params != null) {
				UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(params, Consts.UTF_8);
				httpPost.setEntity(uefEntity);
			}
			httpResponse = httpClient.execute(httpPost);

		} finally {
			httpPost.releaseConnection();
			httpClient.close();
		}
		return httpResponse;
	}

	// used for DEBUG only
	@SuppressWarnings("unused")
	private static String printResponseBody(HttpResponse httpResponse) throws Exception {
		String responseBody = null;
		HttpEntity entity = httpResponse.getEntity();
		if (entity != null) {
			responseBody = EntityUtils.toString(entity, "UTF-8");
		}
		return responseBody;
	}
}
