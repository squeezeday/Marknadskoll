package com.squeezeday.marknadskoll;
/*
* Saldo - http://github.com/kria/saldo
*
* Copyright (C) 2010 Kristian Adrup
*
* This file is part of Saldo.
*
* Saldo is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* Saldo is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*/


import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import android.util.Log;

import java.io.IOException;
import java.util.List;

public class HttpHelper {
	private static final String TAG = "HttpHelper";
	
	private static HttpClient getClient()
	{
		return new DefaultHttpClient();
		/*
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("https", 
		            SSLSocketFactory.getSocketFactory(), 443));

		HttpParams params = new BasicHttpParams();

		SingleClientConnManager mgr = new SingleClientConnManager(params, schemeRegistry);

		return new DefaultHttpClient(mgr, params);*/
	}
	
	public static String get(String url) throws IOException, HttpException {
		HttpGet request = new HttpGet(url);
		return doRequest(request, url, new BasicHttpContext(), true);
	}
	public static String post(String url, List<? extends NameValuePair> parameters) throws IOException, HttpException {
		HttpPost request = new HttpPost(url);
		request.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
		return doRequest(request, url, new BasicHttpContext(), true);
	}
	
	private static String doRequest( HttpUriRequest request, String url,
		HttpContext context, boolean readResponse) throws IOException, HttpException {
		HttpClient httpClient = getClient();
		
		Log.w("http", url);
			
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String response = httpClient.execute(request, responseHandler, context);
		
		return response;
	}
}

