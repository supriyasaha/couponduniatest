package com.example.couponduniatest;
import java.io.DataOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;



public class Global {

	private static
		Global GLOBAL_VARIABLES 
	;
	
	private static synchronized 
		Global setInstance() 
	{
		GLOBAL_VARIABLES = new Global();
		return GLOBAL_VARIABLES;
	}

	public static synchronized 
	Global getInstance() 
	{
		if (GLOBAL_VARIABLES == null) {
			setInstance();
		}
		return GLOBAL_VARIABLES;
	}

	public static synchronized void 
		clearInstance() 
	{
		GLOBAL_VARIABLES = null;
	}

public String FILENAME = "coupondunia";

public DataOutputStream outputStreamWriter;

}
