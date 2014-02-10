package external.services;

import play.libs.F.Promise;
import play.libs.F.Tuple;
import play.libs.OAuth.RequestToken;

import com.fasterxml.jackson.databind.JsonNode;

public class DropBoxOAuthService implements OAuthService {

	@Override
	public Tuple<String, RequestToken> retrieveRequestToken(String callbackUrl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Promise<JsonNode> registeredUserProfile(RequestToken token,
			String authVerifier) {
		// TODO Auto-generated method stub
		return null;
	}

}
