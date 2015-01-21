package com.example.couponduniatest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class Restaurantinfo implements Comparable<Restaurantinfo>{
	
	String neighourhoodplace ,image_logo,rest_name,offers,text_cat;
	Float longitude;
	Float lattitude;
	ArrayList<String> categories;
	Float distance;
	 public Restaurantinfo(String neighourhood ,Float rest_lati,Float rest_longi,String image, String name, String coupons, ArrayList<String> category_name, String text, float dist )
	 {
		 neighourhoodplace=neighourhood;
		 lattitude=rest_lati;
		 longitude=rest_longi;
		 image_logo=image;
		 rest_name=name;
		 offers=coupons;
		 categories=category_name;
		 text_cat=text;
		 distance=dist;
	 }
	
	 public String getPlace()
	 {
		 return neighourhoodplace;
	 }
	 public String getImage()
	 {
		 return image_logo;
	 }
	 public Float getLat()
	 {
		 return lattitude;
	 }
	 public Float getlong()
	 {
		 return longitude;
	 }
	 public String getName()
	 {
		 return rest_name;
	 }
	 public String getOffer()
	 {
		 return offers;
	 }
	 public ArrayList<String> getCategories()
	 {
		 return categories;
	 }

	public String getText()
	 {
		 return text_cat;
	 }
	public float getDistance()
	{
		return distance;
	}

	//inner class to sort by distance
	public static class SortByDistance implements Comparator <Restaurantinfo>
	{
	public int compare(Restaurantinfo track1, Restaurantinfo track2)
	{
	return track1.distance.compareTo(track2.distance);
	}

	}
	@Override
	public int compareTo(Restaurantinfo another) {
		
		return 0;
	}
}
