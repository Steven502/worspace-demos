package org.demo.utils.http;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

public class httpClientTest {

	@Test
	public void testHttpClient001() throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient();
		// 创建Get方法实例
		HttpGet httpgets = new HttpGet("http://localhost:8080/roleplay/form/object/getObjectBomDataJson?parentIds=Test_000002");
		HttpResponse response = httpclient.execute(httpgets);

		HttpEntity entity = response.getEntity();

		if (entity != null) {
			InputStream instreams = entity.getContent();

			String str = convertStreamToString(instreams);
			System.out.println("Do something");
			System.out.println(str);
			// Do not need the rest
			httpgets.abort();
		}
	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
