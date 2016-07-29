package com.example.easyweather.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class autoUpdateReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent(context,AutoUpdateService.class);
		context.startService(i);
	}
	
}
