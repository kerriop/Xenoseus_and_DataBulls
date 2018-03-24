package com.mishin870.xenoseus;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mishin870 on 24.03.2018.
 */

public class JsonGETAsyncTask extends Thread {
	private ICommand successful, error;
	private String url;
	
	public JsonGETAsyncTask(String url, ICommand successful, ICommand error) {
		this.url = url;
		this.successful = successful;
		this.error = error;
	}
	
	public JsonGETAsyncTask(String url, ICommand successful) {
		this(url, successful, new ICommand() {
			@Override
			public void execute(Object object) {
				Toast.makeText(MainActivity.instance.getApplicationContext(), "Ошибка: " + object, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	@Override
	public void run() {
		String response = null;
		try {
			response = GET(url);
		} catch (IOException e) {
			e.printStackTrace();
			error.execute(e.getMessage());
		}
		try {
			JSONObject object = new JSONObject(response);
			successful.execute(object);
		} catch (JSONException jsone) {
			try {
				JSONArray array = new JSONArray(response);
				successful.execute(array);
			} catch (JSONException jsoneInner) {
				error.execute(jsoneInner.getMessage());
			}
		}
	}
	
	/**
	 * Получить результат GET-запроса
	 * @param url адрес
	 * @return
	 * @throws IOException
	 */
	public static String GET(String getUrl) throws IOException {
		URL url = new URL(getUrl);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod("GET");
		urlConnection.setReadTimeout(10000);
		urlConnection.setConnectTimeout(15000);
		//urlConnection.setDoOutput(true);
		urlConnection.connect();
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
		StringBuilder stringBuilder = new StringBuilder();
		
		String line;
		while ((line = bufferedReader.readLine()) != null)
			stringBuilder.append(line + "\n");
		bufferedReader.close();
		
		return stringBuilder.toString();
	}
	
	/**
	 * Своеобразный callback асинхронного get-запроса
	 */
	public interface ICommand {
		void execute(Object object);
	}
	
}
