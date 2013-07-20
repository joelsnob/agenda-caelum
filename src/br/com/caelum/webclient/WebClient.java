package br.com.caelum.webclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class WebClient 
{
	private final String url;
	
	public WebClient(String url)
	{
		this.url = url;
	}
	
	public String post(String json)
	{
		try
		{
			DefaultHttpClient http = new DefaultHttpClient();
			
			HttpPost post = new HttpPost(url);
			
			post.setEntity(new StringEntity(json));
			
			post.setHeader("Accept", "application/json");
			post.setHeader("Content-type", "application/json");
			
			HttpResponse response = http.execute(post);
			String resposta = EntityUtils.toString(response.getEntity());
			
			return resposta;			
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
