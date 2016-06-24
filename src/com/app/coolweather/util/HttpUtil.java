package com.app.coolweather.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

/**
 * 网络请求类
 */
public class HttpUtil {

	public static void sendHttpRequest(final String address, final HttpCallbackListener listener) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				HttpURLConnection conneciton = null;
				try {
					URL url = new URL(address);
					conneciton = (HttpURLConnection) url.openConnection();
					conneciton.setRequestMethod("GET");
					conneciton.setConnectTimeout(8000);
					conneciton.setReadTimeout(8000);
					InputStream in = conneciton.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					if (listener != null) {
						// 回调onFinish()方法
						Log.e("data", response.toString());
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					e.printStackTrace();
					if (listener != null) {
						listener.onError(e);
					}
				} finally {
					if (conneciton != null) {
						conneciton.disconnect();
					}
				}
			}
		}).start();

	}
}
