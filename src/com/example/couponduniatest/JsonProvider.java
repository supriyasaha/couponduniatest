package com.example.couponduniatest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

	import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

	import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
public class JsonProvider {



	
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet;
		public Global variable = Global.getInstance();
		ServerResponse response_Server = new ServerResponse();

		DataOutputStream outputStreamWriter;
		//variable.outputStreamWriter = outputStreamWriter;
		Context ctx;
		// constructor
		public JsonProvider() {
		}
		public ServerResponse getJSONFromUrl(String url,Context c)
		{
			ctx=c;
			httpGet = new HttpGet(url);
			try {
				HttpResponse response = client.execute(httpGet);
				StatusLine statusLine = response.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					InputStream content = entity.getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content));
					String line;
					while ((line = reader.readLine()) != null) {
						builder.append(line);
					}
					response_Server.response = builder.toString();
					//writeToFile(response_Server.response);
					variable.outputStreamWriter = new DataOutputStream(ctx.openFileOutput(variable.FILENAME, Context.MODE_PRIVATE));
				    
					variable.outputStreamWriter.write( response_Server.response.getBytes());
				     variable.outputStreamWriter.close();
				    
					Log.v("Getter", "Your data: " + response_Server.response); //response data
				} else {
					response_Server.error = "Not found "+ statusCode;
					Log.e("Getter", "Failed to download file");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				response_Server.error = "Error connecting to server";
				e.printStackTrace();
				
			}
			if(response_Server!=null)
			{
				
				return response_Server;
			}
			else
			{
				response_Server.error = "No content found";
				return response_Server;
			}
		}
		

		

	}


