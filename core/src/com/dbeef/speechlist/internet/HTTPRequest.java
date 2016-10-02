package com.dbeef.speechlist.internet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.net.HttpStatus;
import com.dbeef.speechlist.utils.Variables;

public class HTTPRequest {

	private boolean FAILED;
	private boolean CURRENTLY_RETRIEVING;
	private String RETRIEVED_CONTENT;
	private String url;
	
	public void sendRequest(final String url, String header, String method, String content) {

		// http://www.mets-blog.com/libgdx-http-request-json/

		Net.HttpRequest request = new Net.HttpRequest(method);

		this.url = url;
		request.setUrl(url);
		
		System.out.println("Starting retrieving from url - " + url);
		
		if(content != null)
		request.setContent(content);
		request.setTimeOut(12000);
		request.setHeader("Content-Type", header);
		request.setHeader("Accept", header);

		CURRENTLY_RETRIEVING = true;

		Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {

			public void handleHttpResponse(Net.HttpResponse httpResponse) {

				int statusCode = httpResponse.getStatus().getStatusCode();
				if (statusCode != HttpStatus.SC_OK && statusCode != HttpStatus.SC_CREATED) {
				
					  FAILED = true;
					  CURRENTLY_RETRIEVING = false;
					
					if (Variables.DEBUG_MODE == true){
						System.out.println("Request Failed");
						System.out.println("Status code:" + statusCode);
					}
						return;
				}

				String responseJson = httpResponse.getResultAsString();

				try {
					CURRENTLY_RETRIEVING = false;
					RETRIEVED_CONTENT = responseJson;
					System.out.println("Retrieved: " + RETRIEVED_CONTENT);
					System.out.println(url);
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}

			public void failed(Throwable t) {
	
				if (Variables.DEBUG_MODE == true)
				{	
					System.out.println("Request Failed Completely");
					System.out.println(t.getMessage());
				}
				FAILED = true;
				CURRENTLY_RETRIEVING = false;
			}

			@Override
			public void cancelled() {
				if (Variables.DEBUG_MODE == true)
					System.out.println("request cancelled");
				FAILED = true;
				CURRENTLY_RETRIEVING = false;
			}
		});
	}

	public String getRETRIEVED_CONTENT() {
		return RETRIEVED_CONTENT;
	}
	
	public String getURL(){
		return url;
	}

	public boolean isCURRENTLY_RETRIEVING() {
		return CURRENTLY_RETRIEVING;
	}

	public boolean isFAILED() {
		return FAILED;
	}
}