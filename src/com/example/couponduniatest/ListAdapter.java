package com.example.couponduniatest;

import java.util.ArrayList;
import java.util.HashMap;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter{

	Context context ;
	ArrayList<Restaurantinfo> mhashmap= new ArrayList<>();
	HashMap<Integer, String> mcategories= new HashMap<>();
	ArrayList<Integer> cate;
	double lat1,lon1;
	public ListAdapter(Context context2,
			ArrayList<Restaurantinfo> user_info, double lat, double longi) {
		context=context2;
		mhashmap= user_info;
		lat1=lat;
		lon1=longi;
	}

	@Override
	public int getCount() {
		return mhashmap.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mhashmap.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		Restaurantinfo info = mhashmap.get(position);
		ViewHolder holder ;
		if(convertView==null)
		{
			holder=new ViewHolder();
			convertView= LayoutInflater.from(context).inflate(R.layout.list_item, null);
			holder.name=(TextView)convertView.findViewById(R.id.textView1);
			holder.offers=(TextView)convertView.findViewById(R.id.textView2);
			holder.category=(TextView)convertView.findViewById(R.id.categories);
			holder.place=(TextView)convertView.findViewById(R.id.textView3);
			holder.distance=(TextView)convertView.findViewById(R.id.textView4);
			holder.logo=(ImageView)convertView.findViewById(R.id.image_logo);
			convertView.setTag(holder);
			convertView.setFocusable(false);
			convertView.setClickable(false);
		}
		else
		{
			holder=(ViewHolder)convertView.getTag();
		}
	
		if(info!=null)
		{
			holder.name.setText(info.getName());
		if(lat1==0.0 && lon1==0.0)
		{
			holder.distance.setVisibility(View.GONE);
		}
		else
		{
			holder.distance.setVisibility(View.VISIBLE);
			float dist = (info.getDistance());
			if(dist>1000)
			{
				dist = dist/1000;
				float dist2= Math.round(dist*100)/100.f;
				holder.distance.setText(Float.toString(dist2)+" km");
			}
			else
			{					
				holder.distance.setText(Integer.toString((int)dist)+" m");
			}
		
		}
		
		
		if(Integer.parseInt(info.getOffer())==1)
		{
			holder.offers.setText(info.getOffer()+" Offer");
		}
		else
		{
		holder.offers.setText(info.getOffer()+" Offers");
		}
		holder.place.setText(info.getPlace());
		
				
			if(info.getText().length()>45)
			{
				 String v = info.getText().substring(0, 40);
				holder.category.setText(v+"...");
					if(holder.category.getText().length()>info.getText().length())
					{
						holder.category.setText(info.getText());
					}
				
			}
			else
			{
				holder.category.setText(info.getText());		
			}
			
			Picasso.with(context).load(info.getImage()).into(holder.logo);
		}
	   
		/*cate =info.getCategories();
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<cate.size();i++)
		{
		sb.append("•"+mcategories.get(cate.get(i)));
		
		}
		String text = sb.toString();*/
		
		return convertView;
		
	}

	private class ViewHolder
	{
		TextView name,place,category,distance,offers;
		ImageView logo;
	}
}
