package com.example.easyweather.util;

public interface HttpcallbackListener {
	
	void onFinish(String reponse);
	
	void onError(Exception e);
	
}
