package com.example.couponduniatest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
		HashMap<Integer, Restaurantinfo> hashmap= new HashMap();

		ProgressDialog pdia;
		int flag=0;
		Dialog l_dialogue ;
		JSONObject data;
		ListView list_items;
		LinearLayout view;
		TextView error;
		private Location location;
		private double lat = 0.0;
		private double longi = 0.0;
		String lat1,lon2;
		String FILENAME = "coupondunia";
		ConnectivityManager conMgr;
		DataOutputStream outputStreamWriter ;
		InputStream inputstream1;
		ArrayList<Restaurantinfo>Rest_info=new ArrayList<Restaurantinfo>();
		Context context = MainActivity.this;
		private String jResponse="";
		Button refresh_btn;
		 View vi;
		 


			@Override
			protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_main);
				view = (LinearLayout)findViewById(R.id.list_items_layout);
				GPSTracker gps = new GPSTracker(this);
				if(gps.canGetLocation()){
					lat =  gps.getLatitude(); // returns latitude
					longi = gps.getLongitude(); // returns longitude
				}
				else
				{
					gps.showSettingsAlert();
				}
				gps.stopUsingGPS();
		        networkconnectivitycheck();
		        refresh_btn = (Button)findViewById(R.id.button1);
		        refresh_btn.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(jResponse != ""){
							GPSTracker gps = new GPSTracker(context);
							/*while(lat == 0.0 && longi==0.0){
							if(gps.canGetLocation()){
								lat =  gps.getLatitude(); // returns latitude
								longi = gps.getLongitude(); // returns longitude
							}
							}*/
							if(gps.canGetLocation()){
								lat =  gps.getLatitude(); // returns latitude
								longi = gps.getLongitude();}
							System.out.println(jResponse);
							
							try {
								loaddata(jResponse);
								ListAdapter madapter = new ListAdapter(MainActivity.this,Rest_info, lat, longi);
								list_items.setAdapter(madapter);
								list_items.requestLayout();
								gps.stopUsingGPS();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							}
						
					}
				});
			}
			public void check_for_Cache()
			{
				try {
					inputstream1 = openFileInput(FILENAME);
				} catch (FileNotFoundException e1) {
					flag=1;
					e1.printStackTrace();
				}
		    	
		    	if(flag!=1)
		    	{
		    		String json2= readFromFile(inputstream1);
		          	if(json2!=null)
		          	{
		          		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		             	 
		      		     vi = inflater.inflate(R.layout.list_view, null);
		      			list_items=(ListView)vi.findViewById(R.id.listView1);
		      		
		      			
		      			view.addView(vi);
		      			try {
							loaddata(json2);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ListAdapter madapter = new ListAdapter(MainActivity.this,Rest_info,lat,longi);
						list_items.setAdapter(madapter);
		          	}     	   
		    	}
		    	
		    	else
		    	{
		    		TextView text = new TextView(this);		
					text.setText("Problem in loading data due to inappropriate network settings ");
					view.addView(text);
					text.setGravity(Gravity.CENTER);
		    	}
		    	
			}
			 public void networkconnectivitycheck()
		     {
				conMgr =  (ConnectivityManager) getSystemService(MainActivity.this.CONNECTIVITY_SERVICE);
				final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
				if(activeNetwork!=null && activeNetwork.isConnected())
				{
					LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				    vi = inflater.inflate(R.layout.list_view, null);
					list_items=(ListView)vi.findViewById(R.id.listView1);
					
					view.addView(vi);
					
					new LoadData().execute();
				}
				else
				{
					/*TextView text = new TextView(this);		
					text.setText("Sorry an error occurred while connecting....Please check your network settings.");
					view.addView(text);
					text.setGravity(Gravity.CENTER);*/
					check_for_Cache();
					
				}
			}
			
			public class LoadData extends AsyncTask<String,String, String>
			{ 
				String url = "http://staging.couponapitest.com/task_data.txt";
				ServerResponse json;
				JSONArray array;
				boolean status=false;
				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					 pdia = new ProgressDialog(MainActivity.this);
				        pdia.setMessage("Loading...");
				        pdia.show();   
				}
				@Override
				protected String doInBackground(String... params) {
					JsonProvider jParser = new JsonProvider(); 
					json = jParser.getJSONFromUrl(url,MainActivity.this);
					
					return null;
				}


				@Override
				protected void onPostExecute(String result) {
						
						pdia.dismiss();
				
						if(json.error == null){
							
							try {
								status=true;
								loaddata(json.response);
								//writeToFile(json.response);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								status = false;
							}
							
							if(status==true)
							{
								
								ListAdapter madapter = new ListAdapter(MainActivity.this,Rest_info, lat, longi);
								list_items.setAdapter(madapter);
							
							}
							else
							{
								CharSequence text = "Invalide data received";
								int duration = Toast.LENGTH_SHORT;
								Toast toast = Toast.makeText(context, text, duration);
								toast.show();
							}
						}
						else
						{
							CharSequence text = json.error;
							int duration = Toast.LENGTH_SHORT;
							Toast toast = Toast.makeText(context, text, duration);
							toast.show();
						}
				}
			}
			private void loaddata(String json2) throws JSONException {
				this.jResponse = json2;
				Rest_info=new ArrayList<>();
				hashmap=new HashMap<>();

				JSONObject jobject = new JSONObject(json2);
				 data = jobject.getJSONObject(Tags.Tag_Data);
				 int length = data.length();
				 
				 for(int i=0;i<length;i++)
				 { 			 
					 if(data.has(Integer.toString(i)))
					 {
					 
					 JSONObject info = data.getJSONObject(Integer.toString(i));
					 
					ArrayList<String>category_name=new ArrayList<>();
					 JSONArray cuisines= info.getJSONArray(Tags.Tag_Categories);
					 StringBuilder sb = new StringBuilder();
					 for(int j=0;j<cuisines.length();j++)
					 {
						 JSONObject cat = cuisines.getJSONObject(j);
						 //String id = cat.getString("OfflineCategoryID");
						 
						 if(!category_name.contains(cat.getString(Tags.Tag_Category_Name)))
						 {
						 category_name.add(cat.getString(Tags.Tag_Category_Name));
						 
						 sb.append("● "+cat.getString(Tags.Tag_Category_Name)+"  ");
						 }
						 
					 }
					 String text = sb.toString();
					
					 Location loc1 = new Location("");
			    		loc1.setLatitude(lat);
			    		loc1.setLongitude(longi);
			    		Location loc2 = new Location("");
			    		Float rest_lati=(float)info.getDouble(Tags.Tag_Lattitude);
			    		Float rest_longi=(float)info.getDouble(Tags.Tag_Longitude);
			    		loc2.setLatitude(rest_lati);
			    		loc2.setLongitude(rest_longi);
			    		float dist = loc1.distanceTo(loc2); 
						
					 Restaurantinfo restinfo = new Restaurantinfo(
							 info.getString(Tags.Tag_location),rest_lati,rest_longi,
							 info.getString(Tags.Tag_logoUrl),info.getString(Tags.Tag_OutletName),info.getString(Tags.Tag_Numcoupons),category_name,text,dist);
					 if(!hashmap.containsKey(i))
					 {
						 hashmap.put(i, restinfo);
						 Rest_info.add(restinfo);
					 }
					 
					}
					 else
					 {
						 length++;
					 }
					 
				 }
				 Collections.sort(Rest_info,new Restaurantinfo.SortByDistance());
				
				
			}
			/*private void writeToFile(String data) {
			    try {
			    	outputStreamWriter = new DataOutputStream(openFileOutput(FILENAME, Context.MODE_PRIVATE));
			        outputStreamWriter.write(data.getBytes());
			        outputStreamWriter.close();
			    }
			    catch (IOException e) {
			        Log.e("Exception", "File write failed: " + e.toString());
			    } 
			}*/
			private String readFromFile(InputStream inputStream) {

			    String ret = "";

			    try {
			        

			        if ( inputStream != null ) {
			            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			            String receiveString = "";
			            StringBuilder stringBuilder = new StringBuilder();

			            while ( (receiveString = bufferedReader.readLine()) != null ) {
			                stringBuilder.append(receiveString);
			            }

			            inputStream.close();
			            ret = stringBuilder.toString();
			        }
			    }
			    catch (IOException e) {
			        Log.e("Reading file", "Can not read file: " + e.toString());
			    }

			    return ret;
			}
			
			@Override
				public void onBackPressed() {
					// TODO Auto-generated method stub
					super.onBackPressed();
					//gps.stopUsingGPS();
					finish();
				}
			@Override
			public void onResume(){
				super.onResume();
				if(jResponse != ""){
				GPSTracker gps = new GPSTracker(this);
				/*while(lat == 0.0 && longi==0.0){
				if(gps.canGetLocation()){
					lat =  gps.getLatitude(); // returns latitude
					longi = gps.getLongitude(); // returns longitude
				}
				}*/
				
				System.out.println(jResponse);
				
				try {
					loaddata(jResponse);
					ListAdapter madapter = new ListAdapter(MainActivity.this,Rest_info, lat, longi);
					list_items.setAdapter(madapter);
					list_items.requestLayout();
					gps.stopUsingGPS();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				
			}
			

	}

