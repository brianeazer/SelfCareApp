package co.grandcircus.selfcareapp.model;

public class Response {
	private String token_type;
	private String scope;
	private String expires_in;
	private String access_token;
	public Response(String token_type, String scope, String expires_in, String access_token) {
		super();
		this.token_type = token_type;
		this.scope = scope;
		this.expires_in = expires_in;
		this.access_token = access_token;
	}
	
	public Response() {
		
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	@Override
	public String toString() {
		return "Response [token_type=" + token_type + ", scope=" + scope + ", expires_in=" + expires_in
				+ ", access_token=" + access_token + "]";
	}
	
	

}
